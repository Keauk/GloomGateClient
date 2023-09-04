package org.example.core;

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
            case INITIAL:
                if (input.equals("1")) {
                    callback.clearResponse();
                    callback.addResponse("You chose to Register.");
                    callback.addResponse("Enter Username");
                    currentState = InputState.REGISTER_USERNAME;
                } else if (input.equals("2")) {
                    callback.clearResponse();
                    callback.addResponse("You chose to Login.");
                    callback.addResponse("Enter Username");
                    currentState = InputState.LOGIN_USERNAME;
                } else {
                    callback.addResponse("You entered: " + input);
                }
                break;

            case REGISTER_USERNAME:
                tempUsername = input;
                callback.addResponse("Enter Password for registration");
                currentState = InputState.REGISTER_PASSWORD;
                break;

            case REGISTER_PASSWORD:
                tempPassword = input;
                // At this point, both tempUsername and tempPassword are set.
                // You can make a POST request or any further processing.
                System.out.println(tempUsername);
                System.out.println(tempPassword);

                // Resetting the temporary variables after use.
                tempUsername = null;
                tempPassword = null;

                callback.addResponse("Registration complete!");
                currentState = InputState.INITIAL;
                break;
            case LOGIN_USERNAME:
                // Handle the username input for login
                currentState = InputState.LOGIN_PASSWORD;
                break;
            case LOGIN_PASSWORD:
                // Handle the password input for login
                currentState = InputState.INITIAL;
                break;
        }
    }
}