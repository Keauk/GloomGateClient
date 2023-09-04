package org.example.ui;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ResponseView {
    private VBox textContainer;
    private StackPane wrapperContainer;
    private final ScrollPane scrollPane;

    public ResponseView() {
        textContainer = new VBox();
        styleResponseContainer(textContainer);

        scrollPane = new ScrollPane(textContainer);
        styleScrollPane(scrollPane);
    }

    public ScrollPane getView() {
        return scrollPane;
    }

    public void addResponse(String response) {
        Text responseText = new Text(response);
        responseText.setFill(Color.WHITE);
        responseText.setFont(Font.font("Consolas", 18));
        textContainer.getChildren().add(responseText);
        autoScroll();
    }

    private void styleResponseContainer(VBox container) {
        container.setStyle("-fx-background-color: black; -fx-padding: 10px; -fx-border-color: white; -fx-border-width: 0 0 2 0;");
    }

    public VBox getContainer() {
        return this.textContainer;
    }

    public void clear() {
        this.textContainer.getChildren().clear();
    }

    public void add(String response) {
        Text responseText = new Text(response);
        responseText.setFill(Color.WHITE);
        responseText.setFont(Font.font("Consolas", 18));
        textContainer.getChildren().add(responseText);
        autoScroll();
    }

    private void autoScroll() {
        // Whenever the height of the container changes, adjust the scroll.
        textContainer.heightProperty().addListener((obs, oldVal, newVal) -> {
            scrollPane.setVvalue(1.0);
        });
    }

    private void styleScrollPane(ScrollPane scrollPane) {
        scrollPane.setPrefHeight(400);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: black;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }
}