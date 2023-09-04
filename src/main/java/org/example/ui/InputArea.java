package org.example.ui;

import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import org.example.core.StateManager;

public class InputArea {
    private TextArea textArea;
    private StateManager stateManager;
    private ResponseView responseView;

    public InputArea(StateManager stateManager) {
        System.out.println("Constructor: stateManager = " + stateManager); // Debug print

        this.stateManager = stateManager;
        System.out.println("After assignment: this.stateManager = " + this.stateManager); // Debug print

        textArea = new TextArea();
        configureInputArea();
        styleInputArea();
        // ... (Styling and other configurations)
    }

    public TextArea getView() {
        return textArea;
    }

    private void configureInputArea() {
        textArea.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER && !e.isShiftDown()) {
                e.consume();
                String input = textArea.getText().trim();

                System.out.println("Key Pressed: this.stateManager = " + this.stateManager); // Debug print

                stateManager.handleInput(input);
                textArea.clear();
            }
        });
        textArea.requestFocus();
    }

    public TextArea getArea() {
        return this.textArea;
    }

    public void styleInputArea() {
        textArea.setStyle("-fx-control-inner-background: black; " +
                "-fx-text-fill: white; " +
                "-fx-prompt-text-fill: grey; " +
                "-fx-cursor: text; " +
                "-fx-border-color: transparent; " +
                "-fx-focus-color: transparent; " +
                "-fx-border-width: 0; " +
                "-fx-faint-focus-color: transparent;");
        textArea.setFont(Font.font("Consolas", 18));
        textArea.setPrefHeight(70);
    }
}