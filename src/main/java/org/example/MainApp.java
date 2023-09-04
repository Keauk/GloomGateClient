package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.ui.MainLayout;

public class MainApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        MainLayout mainLayout = new MainLayout();
        Scene scene = new Scene(mainLayout.getRoot(), 800, 600);
        primaryStage.setTitle("MUD Terminal");
        primaryStage.setScene(scene);
        primaryStage.show();

        mainLayout.showInitialMessage();
    }
}