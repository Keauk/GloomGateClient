package org.example.ui;

import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.example.core.InputCallback;
import org.example.core.StateManager;

public class MainLayout implements InputCallback {
    private VBox root;
    private ResponseView responseView;
    private InputArea inputArea;
    private StateManager stateManager;

    public MainLayout() {
        this.root = new VBox(10);
        this.root.setStyle("-fx-background-color: black;");

        this.responseView = new ResponseView();
        this.stateManager = new StateManager(this);  // This should come first
        this.inputArea = new InputArea(this.stateManager);  // Then this

        VBox.setVgrow(responseView.getView(), Priority.ALWAYS);
        VBox.setVgrow(inputArea.getView(), Priority.NEVER);

        this.root.getChildren().addAll(responseView.getView(), inputArea.getView());
    }
    public VBox getRoot() {
        return this.root;
    }

    public void showInitialMessage() {
        addResponse("Welcome to GloomGate.");
        addResponse("Choose an option:");
        addResponse("1. Register");
        addResponse("2. Login");
    }

    @Override
    public void clearResponse() {
        responseView.clear();
    }

    @Override
    public void addResponse(String response) {
        responseView.add(response);
    }
}