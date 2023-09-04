package org.example.ui;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ResponseView {
    private VBox container;
    private ScrollPane scrollPane;

    public ResponseView() {
        container = new VBox();
        styleResponseContainer(container);

        scrollPane = new ScrollPane(container);
        styleScrollPane(scrollPane);
    }

    public ScrollPane getView() {
        return scrollPane;
    }

    public void addResponse(String response) {
        Text responseText = new Text(response);
        responseText.setFill(Color.WHITE);
        responseText.setFont(Font.font("Consolas", 18));
        container.getChildren().add(responseText);
        autoScroll();
    }

    private void styleResponseContainer(VBox container) {
        container.setStyle("-fx-background-color: black; -fx-padding: 10px;");
    }

    public VBox getContainer() {
        return this.container;
    }

    public void clear() {
        this.container.getChildren().clear();
    }

    public void add(String response) {
        Text responseText = new Text(response);
        responseText.setFill(Color.WHITE);
        responseText.setFont(Font.font("Consolas", 18));
        container.getChildren().add(responseText);
        autoScroll();
    }

    private void autoScroll() {
        container.heightProperty().addListener((obs, oldVal, newVal) ->
                container.setTranslateY(-newVal.doubleValue() + 510)
        );
    }

    private void styleScrollPane(ScrollPane scrollPane) {
        scrollPane.setPrefHeight(400);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: black;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }
}