package com.example.kidsapplication;



import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.util.logging.Logger;
import java.util.logging.Level;

public class AvatarChatController {
    private static final Logger LOGGER = Logger.getLogger(AvatarChatController.class.getName());
    private static final String API_KEY = System.getenv("OPENAI_API_KEY");

    private AnimationHandler animationHandler;
    private ChatGPTServices chatGPTService;
    private double dragOffsetX;
    private double dragOffsetY;
    private boolean isMinimized = false;

    @FXML
    private ImageView avatarImageView;

    @FXML
    private VBox avatarContainer;

    @FXML
    private VBox speechBubble;

    @FXML
    private Label speechText;

    @FXML
    private VBox chatInputContainer;

    @FXML
    private TextArea chatHistory;

    @FXML
    private TextField userInput;

    @FXML
    public void initialize() {
        try {
            if (API_KEY == null || API_KEY.isEmpty()) {
                throw new IllegalStateException("OpenAI API key not found in environment variables");
            }

            // Initialize services
           animationHandler = new AnimationHandler(avatarImageView);
            chatGPTService = new ChatGPTServices(API_KEY);

            // Setup drag functionality
            setupDragHandlers();

            // Setup speech bubble animations
            setupSpeechBubbleAnimations();

            // Setup chat input handlers
            setupChatInputHandlers();

            // Start with idle animation
            animationHandler.playAnimation("idle");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize controller", e);
            showError("Initialization Error", "Failed to initialize the application: " + e.getMessage());
        }
    }

    private void setupDragHandlers() {
        avatarContainer.setOnMousePressed(event -> {
            dragOffsetX = event.getSceneX();
            dragOffsetY = event.getSceneY();
        });

        avatarContainer.setOnMouseDragged(event -> {
            Stage stage = (Stage) avatarContainer.getScene().getWindow();
            stage.setX(event.getScreenX() - dragOffsetX);
            stage.setY(event.getScreenY() - dragOffsetY);
        });
    }

    private void setupSpeechBubbleAnimations() {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), speechBubble);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), speechBubble);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        speechBubble.setOnMouseEntered(event -> fadeIn.play());
        speechBubble.setOnMouseExited(event -> fadeOut.play());
    }

    private void setupChatInputHandlers() {
        userInput.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                handleSendMessage();
            }
        });
    }

    @FXML
    private void handleAvatarClick(MouseEvent event) {
        if (isMinimized) {
            maximize();
        } else {
            toggleChatInput();
        }
    }

    @FXML
    private void handleSendMessage() {
        String message = userInput.getText().trim();
        if (message.isEmpty()) {
            return;
        }

        // Add user message to chat history
        chatHistory.appendText("You: " + message + "\n");
        userInput.clear();

        // Show thinking animation
        animationHandler.playAnimation("thinking");

        // Get response from ChatGPT
        new Thread(() -> {
            try {
                String response = chatGPTService.getResponse(message);

                // Update UI on JavaFX thread
                javafx.application.Platform.runLater(() -> {
                    // Show talking animation
                    animationHandler.playAnimation("talking");

                    // Update speech bubble
                    speechText.setText(response);
                    speechBubble.setVisible(true);

                    // Add response to chat history
                    chatHistory.appendText("Avatar: " + response + "\n");

                    // Scroll to bottom
                    chatHistory.setScrollTop(Double.MAX_VALUE);

                    // Return to idle animation after delay
                    new Thread(() -> {
                        try {
                            Thread.sleep(3000);
                            javafx.application.Platform.runLater(() -> {
                                animationHandler.playAnimation("idle");
                                speechBubble.setVisible(false);
                            });
                        } catch (InterruptedException e) {
                            LOGGER.log(Level.WARNING, "Animation delay interrupted", e);
                        }
                    }).start();
                });
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error getting response from ChatGPT", e);
                javafx.application.Platform.runLater(() -> {
                    showError("Error", "Failed to get response from ChatGPT");
                    animationHandler.playAnimation("idle");
                });
            }
        }).start();
    }

    @FXML
    private void handleMinimize() {
        minimize();
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) avatarContainer.getScene().getWindow();
        stage.close();
    }

    private void minimize() {
        isMinimized = true;
        chatInputContainer.setVisible(false);
        speechBubble.setVisible(false);
        Stage stage = (Stage) avatarContainer.getScene().getWindow();
        stage.setHeight(100);
        stage.setWidth(100);
    }

    private void maximize() {
        isMinimized = false;
        Stage stage = (Stage) avatarContainer.getScene().getWindow();
        stage.setHeight(400);
        stage.setWidth(300);
    }

    private void toggleChatInput() {
        chatInputContainer.setVisible(!chatInputContainer.isVisible());
        if (chatInputContainer.isVisible()) {
            userInput.requestFocus();
        }
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void dispose() {
        if (animationHandler != null) {
            animationHandler.dispose();
        }
        if (chatGPTService != null) {
            chatGPTService.dispose();
        }
    }
}
