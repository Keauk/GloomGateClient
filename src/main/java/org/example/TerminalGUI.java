package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.Priority;

public class TerminalGUI extends Application {

    private VBox responseContainer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = createMainLayout();

        ScrollPane responseView = createResponseView();
        TextArea inputArea = createInputArea();

        root.getChildren().addAll(responseView, inputArea);

        VBox.setVgrow(responseView, Priority.ALWAYS);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("MUD Terminal");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createMainLayout() {
        VBox root = new VBox(10);
        root.setStyle("-fx-background-color: black;");
        return root;
    }

    private ScrollPane createResponseView() {
        responseContainer = new VBox();
        styleResponseContainer(responseContainer);

        ScrollPane scrollPane = new ScrollPane(responseContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: black;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        return scrollPane;
    }

    private TextArea createInputArea() {
        TextArea inputArea = new TextArea();
        inputArea.setPrefHeight(60);
        styleInputArea(inputArea);
        configureInputArea(inputArea);

        return inputArea;
    }

    private void configureInputArea(TextArea inputArea) {
        inputArea.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER && !e.isShiftDown()) {
                e.consume();
                String input = inputArea.getText().trim();
                addResponse("You entered: " + input);
                inputArea.clear();
            }
        });
        inputArea.requestFocus();
    }

    private void addResponse(String response) {
        Text responseText = new Text(response);
        responseText.setFill(Color.WHITE);
        responseText.setFont(Font.font("Consolas", 18));
        responseContainer.getChildren().add(responseText);
        autoScrollResponse();
    }

    private void autoScrollResponse() {
        responseContainer.heightProperty().addListener((obs, oldVal, newVal) ->
                responseContainer.setTranslateY(-newVal.doubleValue() + 540)
        );
    }

    private void styleResponseContainer(VBox container) {
        container.setStyle("-fx-background-color: black; -fx-padding: 10px;");  // Added 10px padding
    }

    private void styleInputArea(TextArea textArea) {
        textArea.setStyle("-fx-control-inner-background: black; " +
                "-fx-text-fill: white; " +
                "-fx-prompt-text-fill: grey; " +
                "-fx-cursor: text; " +
                "-fx-border-color: transparent; " +
                "-fx-focus-color: transparent; " +
                "-fx-border-width: 0; " +
                "-fx-faint-focus-color: transparent;");
        textArea.setFont(Font.font("Consolas", 18));
    }
}