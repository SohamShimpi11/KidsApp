package com.example.kidsapplication;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {
    @FXML
    public Button loginButton;
    @FXML
    private StackPane rootPane;

    @FXML
    public VBox loginBox, createAccountBox;

    @FXML
    private TextField usernameField, newUsernameField;

    @FXML
    private PasswordField passwordField, newPasswordField, confirmPasswordField;

    @FXML
    private Label statusLabel, accountStatusLabel;

    @FXML
    public Button createAccountButton, backlogin;

    @FXML
    private void initialize() {
        addButtonAnimation1(loginButton);
//        addButtonAnimation(backlogin);
        addButtonAnimation2(createAccountButton);
    }
    // Switch to Create Account view
    @FXML
    private void showCreateAccount() {
        loginBox.setVisible(false);
        loginBox.setManaged(false);
        createAccountBox.setVisible(true);
        createAccountBox.setManaged(true);
    }

    // Switch back to Login view
    @FXML
    private void showLogin() {
        createAccountBox.setVisible(false);
        createAccountBox.setManaged(false);
        loginBox.setVisible(true);
        loginBox.setManaged(true);
    }

    // Handle login
    @FXML
    public void handleLoginAction() throws IOException {
        try {
            // Close the current login window
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            currentStage.close();

            // Load the new StudentDashboard.fxml file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("KidsApplication.fxml"));
            Parent root = fxmlLoader.load();

            // Create a new stage and show the student dashboard
            Stage newStage = new Stage();
            newStage.setTitle("KIDS APP");
            newStage.setScene(new Scene(root, 450, 785));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle or log the exception (FXML loading or other I/O issues)
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any other exception, e.g. related to the UI or other methods
        }
    }


    // Handle account creation
    @FXML
    private void handleCreateAccountAction() {
        String newUsername = newUsernameField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (newUsername.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            accountStatusLabel.setText("All fields are required.");
            accountStatusLabel.setStyle("-fx-text-fill: red;");
        } else if (!newPassword.equals(confirmPassword)) {
            accountStatusLabel.setText("Passwords do not match.");
            accountStatusLabel.setStyle("-fx-text-fill: red;");
        } else {
            accountStatusLabel.setText("Account created successfully!");
            accountStatusLabel.setStyle("-fx-text-fill: green;");
            showLogin(); // Switch back to login
        }
    }

    // Navigate to KidsApplication.fxml after successful login
    private void navigateToKidsApplication() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("KidsApplication.fxml"));
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void addButtonAnimation1(Button button) {
        // Initial button style
        button.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: #40916c; " +
                        "-fx-border-width: 1; " +
                        "-fx-text-fill: #40916c; " +
                        "-fx-font-size: 14px; " +
                        "-fx-padding: 10 20; " +
                        "-fx-border-radius: 20; " +
                        "-fx-background-radius: 20;"
        );

        // Hover effect
        button.setOnMouseEntered(event -> {
            button.setStyle(
                    "-fx-background-color: rgba(255, 255, 255, 0.1); " +
                            "-fx-border-color: #40916c; " +
                            "-fx-border-width: 1; " +
                            "-fx-text-fill: #40916c; " +
                            "-fx-font-size: 14px; " +
                            "-fx-padding: 10 20; " +
                            "-fx-border-radius: 20; " +
                            "-fx-background-radius: 20;"
            );
        });

        // Reset style on mouse exit
        button.setOnMouseExited(event -> {
            button.setStyle(
                    "-fx-background-color: transparent; " +
                            "-fx-border-color: #40916c; " +
                            "-fx-border-width: 1; " +
                            "-fx-text-fill: #40916c; " +
                            "-fx-font-size: 14px; " +
                            "-fx-padding: 10 20; " +
                            "-fx-border-radius: 20; " +
                            "-fx-background-radius: 20;"
            );
        });

        // Click effect
        button.setOnMousePressed(event -> {
            button.setStyle(
                    "-fx-background-color: rgba(255, 255, 255, 0.2); " +
                            "-fx-border-color: #40916c; " +
                            "-fx-border-width: 1; " +
                            "-fx-text-fill: #40916c; " +
                            "-fx-font-size: 14px; " +
                            "-fx-padding: 10 20; " +
                            "-fx-border-radius: 20; " +
                            "-fx-background-radius: 20;"
            );
        });

        // Reset style after click
        button.setOnMouseReleased(event -> {
            button.setStyle(
                    "-fx-background-color: rgba(255, 255, 255, 0.1); " +
                            "-fx-border-color: #40916c; " +
                            "-fx-border-width: 1; " +
                            "-fx-text-fill: #40916c; " +
                            "-fx-font-size: 14px; " +
                            "-fx-padding: 10 20; " +
                            "-fx-border-radius: 20; " +
                            "-fx-background-radius: 20;"
            );
        });
    }

    private void addButtonAnimation(Button button) {
        // Initial button style
        button.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: #40916c; " +
                        "-fx-border-width: 1; " +
                        "-fx-text-fill: #40916c; " +
                        "-fx-font-size: 14px; " +
                        "-fx-padding: 10 20; " +
                        "-fx-border-radius: 20; " +
                        "-fx-background-radius: 20;"
        );

        // Hover effect
        button.setOnMouseEntered(event -> {
            button.setStyle(
                    "-fx-background-color: rgba(255, 255, 255, 0.1); " +
                            "-fx-border-color: #40916c; " +
                            "-fx-border-width: 1; " +
                            "-fx-text-fill: #40916c; " +
                            "-fx-font-size: 14px; " +
                            "-fx-padding: 10 20; " +
                            "-fx-border-radius: 20; " +
                            "-fx-background-radius: 20;"
            );
        });

        // Reset style on mouse exit
        button.setOnMouseExited(event -> {
            button.setStyle(
                    "-fx-background-color: transparent; " +
                            "-fx-border-color: #40916c; " +
                            "-fx-border-width: 1; " +
                            "-fx-text-fill: #40916c; " +
                            "-fx-font-size: 14px; " +
                            "-fx-padding: 10 20; " +
                            "-fx-border-radius: 20; " +
                            "-fx-background-radius: 20;"
            );
        });

        // Click effect
        button.setOnMousePressed(event -> {
            button.setStyle(
                    "-fx-background-color: rgba(255, 255, 255, 0.2); " +
                            "-fx-border-color: #40916c; " +
                            "-fx-border-width: 1; " +
                            "-fx-text-fill: #40916c; " +
                            "-fx-font-size: 14px; " +
                            "-fx-padding: 10 20; " +
                            "-fx-border-radius: 20; " +
                            "-fx-background-radius: 20;"
            );
        });

        // Reset style after click
        button.setOnMouseReleased(event -> {
            button.setStyle(
                    "-fx-background-color: rgba(255, 255, 255, 0.1); " +
                            "-fx-border-color: #40916c; " +
                            "-fx-border-width: 1; " +
                            "-fx-text-fill: #40916c; " +
                            "-fx-font-size: 14px; " +
                            "-fx-padding: 10 20; " +
                            "-fx-border-radius: 20; " +
                            "-fx-background-radius: 20;"
            );
        });
    }

    private void addButtonAnimation2(Button button) {
        // Initial button style
        button.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: #40916c; " +
                        "-fx-border-width: 1; " +
                        "-fx-text-fill: #40916c; " +
                        "-fx-font-size: 14px; " +
                        "-fx-padding: 10 20; " +
                        "-fx-border-radius: 20; " +
                        "-fx-background-radius: 20;"
        );

        // Hover effect
        button.setOnMouseEntered(event -> {
            button.setStyle(
                    "-fx-background-color: rgba(255, 255, 255, 0.1); " +
                            "-fx-border-color: #40916c; " +
                            "-fx-border-width: 1; " +
                            "-fx-text-fill: #40916c; " +
                            "-fx-font-size: 14px; " +
                            "-fx-padding: 10 20; " +
                            "-fx-border-radius: 20; " +
                            "-fx-background-radius: 20;"
            );
        });

        // Reset style on mouse exit
        button.setOnMouseExited(event -> {
            button.setStyle(
                    "-fx-background-color: transparent; " +
                            "-fx-border-color: #40916c; " +
                            "-fx-border-width: 1; " +
                            "-fx-text-fill: #40916c; " +
                            "-fx-font-size: 14px; " +
                            "-fx-padding: 10 20; " +
                            "-fx-border-radius: 20; " +
                            "-fx-background-radius: 20;"
            );
        });

        // Click effect
        button.setOnMousePressed(event -> {
            button.setStyle(
                    "-fx-background-color: rgba(255, 255, 255, 0.2); " +
                            "-fx-border-color: #40916c; " +
                            "-fx-border-width: 1; " +
                            "-fx-text-fill: #40916c; " +
                            "-fx-font-size: 14px; " +
                            "-fx-padding: 10 20; " +
                            "-fx-border-radius: 20; " +
                            "-fx-background-radius: 20;"
            );
        });

        // Reset style after click
        button.setOnMouseReleased(event -> {
            button.setStyle(
                    "-fx-background-color: rgba(255, 255, 255, 0.1); " +
                            "-fx-border-color: #40916c; " +
                            "-fx-border-width: 1; " +
                            "-fx-text-fill: #40916c; " +
                            "-fx-font-size: 14px; " +
                            "-fx-padding: 10 20; " +
                            "-fx-border-radius: 20; " +
                            "-fx-background-radius: 20;"
            );
        });
    }
}


