package com.example.kidsapplication;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.logging.Logger;
import java.util.logging.Level;

public class AvatarChatApp extends Application {
    private static final Logger LOGGER = Logger.getLogger(AvatarChatApp.class.getName());

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/avatar_chat.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.setFill(null); // Make scene background transparent

            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.setTitle("AI Avatar Chat");
            primaryStage.setScene(scene);
            primaryStage.setAlwaysOnTop(true);
            primaryStage.setWidth(300);
            primaryStage.setHeight(400);
            primaryStage.show();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to start application", e);
            showError("Application Error", "Failed to start the application: " + e.getMessage());
        }
    }

    private void showError(String title, String content) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}