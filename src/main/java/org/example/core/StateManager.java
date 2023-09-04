package org.example.core;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import org.example.ui.ResponseView;
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
        LOGIN_PASSWORD
    }

    public void handleInput(String input) {
        switch (currentState) {
            case INITIAL -> {
                if (input.equalsIgnoreCase("register")) {
                    callback.clearResponse();
                    callback.addResponse("You chose to Register.");
                    callback.addResponse("Enter Username");
                    currentState = InputState.REGISTER_USERNAME;
                } else if (input.equalsIgnoreCase("login")) {
                    callback.clearResponse();
                    callback.addResponse("You chose to Login.");
                    callback.addResponse("Enter Username");
                    currentState = InputState.LOGIN_USERNAME;
                } else {
                    callback.addResponse("You entered: " + input);
                }
            }
            case REGISTER_USERNAME -> {
                tempUsername = input;
                callback.addResponse("Enter Password for registration");
                currentState = InputState.REGISTER_PASSWORD;
            }
            case REGISTER_PASSWORD -> {
                tempPassword = input;
                callback.addResponse("User " + tempUsername + " created.");
                JSONObject response = HttpUtil.register(tempUsername, tempPassword);
                System.out.println(response);
                tempUsername = null;
                tempPassword = null;
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(event -> {
                    callback.addResponse("Logging you in...");
                    currentState = InputState.INITIAL;
                });
                pause.play();
                currentState = InputState.INITIAL;
            }
            case LOGIN_USERNAME ->
                // Handle the username input for login
                    currentState = InputState.LOGIN_PASSWORD;
            case LOGIN_PASSWORD ->
                // Handle the password input for login
                    currentState = InputState.INITIAL;
        }
    }
}