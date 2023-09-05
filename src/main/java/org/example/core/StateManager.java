package org.example.core;

import javafx.application.Platform;
import javafx.concurrent.Task;
import org.example.util.HttpUtil;
import org.json.JSONObject;

public class StateManager {
    private InputState currentState = InputState.INITIAL;
    private InputCallback callback;
    private String tempUsername;
    private String tempPassword;

    public StateManager(InputCallback callback) {
        this.callback = callback;
    }

    public enum InputState {
        INITIAL,
        REGISTER_USERNAME,
        REGISTER_PASSWORD,
        LOGIN_USERNAME,
        LOGIN_PASSWORD,
        AWAITING_TOKEN,
        LOGGED_IN,
        PROCESSING,
    }

    public void handleInput(String input) {
        if (currentState == InputState.PROCESSING) {
            return;
        }
        System.out.println("handleInput called with input: " + input);
        switch (currentState) {
            case INITIAL -> handleInitialState(input);
            case REGISTER_USERNAME -> handleRegisterUsernameState(input);
            case REGISTER_PASSWORD -> handleRegisterPasswordState(input);
            case LOGIN_USERNAME -> handleLoginUsernameState(input);
            case LOGIN_PASSWORD -> handleLoginPasswordState(input);
            case AWAITING_TOKEN -> processAwaitingTokenState();
        }
    }

    private void handleInitialState(String input) {
        if (input.equalsIgnoreCase("register")) {
            callback.clearResponse();
            callback.addResponse("You chose to Register.");
            callback.addResponse("Enter Username");
            currentState = InputState.REGISTER_USERNAME;
        } else if (input.equalsIgnoreCase("login")) {
            callback.clearResponse();
            callback.addResponse("You chose to Login.");
            callback.addResponse("Enter username");
            currentState = InputState.LOGIN_USERNAME;
        }
    }

    private void handleRegisterUsernameState(String input) {
        tempUsername = input;
        callback.addResponse("Enter Password for registration");

        currentState = InputState.REGISTER_PASSWORD;
    }

    private void handleRegisterPasswordState(String input) {
        tempPassword = input;
        JSONObject response = HttpUtil.register(tempUsername, tempPassword);
        System.out.println(response);
        currentState = InputState.PROCESSING;

        Task<Void> authenticationTask = new Task<>() {
            @Override
            protected Void call() {
                processAwaitingTokenState();
                return null;
            }
        };

        new Thread(authenticationTask).start();
    }

    private void handleLoginUsernameState(String input) {
        tempUsername = input;
        callback.addResponse("Enter password");
        currentState = InputState.LOGIN_PASSWORD;
    }

    private void handleLoginPasswordState(String input) {
        tempPassword = input;
        callback.addResponse("Trying to authenticate user...");

        currentState = InputState.PROCESSING;

        Task<Void> authenticationTask = new Task<>() {
            @Override
            protected Void call() {
                processAwaitingTokenState();
                return null;
            }
        };

        new Thread(authenticationTask).start();
    }

    private void authenticateAndFetchToken(String username, String password) {
        JSONObject loginResponse = HttpUtil.authenticateUser(username, password);
        if (loginResponse != null && loginResponse.has("token")) {
            String token = loginResponse.getString("token");

            Platform.runLater(() -> {
                callback.addResponse("Authentication successful! Welcome, " + username + ".");
                currentState = InputState.LOGGED_IN;
            });
        } else {
            String errorMessage = loginResponse != null ? loginResponse.getString("message") : "Authentication failed!";
            Platform.runLater(() -> {
                callback.addResponse(errorMessage);
                callback.addResponse("Would you like to register or login?");
                ;
                currentState = InputState.INITIAL;
            });
        }
    }

    private void processAwaitingTokenState() {
        authenticateAndFetchToken(tempUsername, tempPassword);

        // Ensure that the main thread is aware of the state changes made here
        Platform.runLater(() -> {
            tempUsername = null;
            tempPassword = null;
        });
    }
}