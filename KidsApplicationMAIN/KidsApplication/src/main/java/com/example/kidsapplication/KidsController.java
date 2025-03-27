package com.example.kidsapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import javafx.scene.shape.Circle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyEvent;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import java.io.*;


public class KidsController {
    public TextArea readingTextArea;
    @FXML
    public VBox readingTextAreaContainerUpdated;
    @FXML
    public VBox fillInTheBlanksSectionUpdated;
    @FXML
    public TextArea readingTextAreaUpdated;

    @FXML
    private Label welcomeText;

    @FXML
    private Button homeButton, leagueButton, helpButton, chatButton, profileButton;

    @FXML
    private ImageView imageView;

    @FXML
    private ImageView imageMaths1;

    @FXML
    private ImageView image2048;

    @FXML
    private ImageView imageReading;

    @FXML
    private Button PLAYR;

    @FXML
    private Button PLAYRIDDLE;

    @FXML
    private Button PLAY2048;

    @FXML
    private Button PLAYMATHS;

    @FXML
    private ImageView logoImageView;

    @FXML
    private VBox homePane;

    @FXML
    private VBox leaguePane;

    @FXML
    private VBox helpPane;

    @FXML
    private VBox chatPane;

    @FXML
    private VBox profilePane;
    @FXML
    private VBox easySection, hardSection;
    @FXML
    private Button backToHomeBtn, backToMathsFromEasy, backToMathsFromHard;
    @FXML
    private VBox riddleSection;
    @FXML
    private VBox game2048Section;
    @FXML
    private VBox mathsSection;
    @FXML
    private VBox readingSection;
    @FXML
    private Button easyBtn, hardBtn;
    @FXML
    private ImageView profileImage;
    @FXML
    private Label nameLabel, surnameLabel, usernameLabel, genderLabel;
    @FXML
    private Label favSubjectsField;

    @FXML
    public void initialize() {
        setButtonIcon(homeButton, "home.png");
        setButtonIcon(leagueButton, "league.png");
        setButtonIcon(helpButton, "help.png");
        setButtonIcon(chatButton, "chat.png");
        setButtonIcon(profileButton, "logo.jpg");
        nameLabel.setText("John");
        surnameLabel.setText("Doe");
        usernameLabel.setText("john_doe");
        genderLabel.setText("Male");
        addButtonAnimation(PLAYR);
        addButtonAnimation(PLAYRIDDLE);
        addButtonAnimation(PLAY2048);
        addButtonAnimation(PLAYMATHS);
        showHome();
        addHoverAnimation(easyBtn, "#66BB6A"); // Lighter green on hover
        addHoverAnimation(hardBtn, "#E53935");
        applyShadowAnimation(homeButton, true);
        updateUI();

        Image logoImage = new Image(getClass().getResource("logo.jpg").toExternalForm());
        logoImageView.setImage(logoImage);
        Image defaultImage = new Image(getClass().getResource("logo.jpg").toExternalForm());
        profileImage.setImage(defaultImage);
        // Set circular clip
        Circle clip3 = new Circle(logoImageView.getFitWidth() / 2, logoImageView.getFitHeight() / 2, 20);
        logoImageView.setClip(clip3);
        Circle clip4 = new Circle(profileImage.getFitWidth() / 2, profileImage.getFitHeight() / 2, 50);
        profileImage.setClip(clip4);
        imageView.setImage(new Image(getClass().getResource("riddle.jpg").toExternalForm()));

        // Apply circular clipping
        Circle clip = new Circle(40); // Radius = Half of fitHeight/fitWidth
        clip.setCenterX(40);
        clip.setCenterY(40);
        imageView.setClip(clip);

        image2048.setImage(new Image(getClass().getResource("2048.jpg").toExternalForm()));

        // Apply circular clipping
        Circle clip1 = new Circle(40); // Radius = Half of fitHeight/fitWidth
        clip1.setCenterX(40);
        clip1.setCenterY(40);
        image2048.setClip(clip1);

        imageMaths1.setImage(new Image(getClass().getResource("MATHS1.jpg").toExternalForm()));

        // Apply circular clipping
        Circle clip5 = new Circle(40); // Radius = Half of fitHeight/fitWidth
        clip5.setCenterX(40);
        clip5.setCenterY(40);
        imageMaths1.setClip(clip5);

        imageReading.setImage(new Image(getClass().getResource("reading.jpg").toExternalForm()));

        // Apply circular clipping
        Circle clip2 = new Circle(40); // Radius = Half of fitHeight/fitWidth
        clip2.setCenterX(40);
        clip2.setCenterY(40);
        imageReading.setClip(clip2);
    }

    //for progress bar
    private int currentPoints = 0;  // Points start from 0
    private int currentLevel = 1;   // Starting level
    private int nextLevelThreshold = 480;  // Level-up threshold

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label pointsLabel, levelLabel;

    // Method to add points (triggered when button is clicked)
    @FXML
    private void addPoints() {
        currentPoints += 50; // Adds 50 points when the button is clicked

        // Check if the user reaches the next level
        while (currentPoints >= nextLevelThreshold) {
            levelUp();
        }

        updateUI(); // Refresh UI
    }

    // Level-up logic
    private void levelUp() {
        currentLevel++; // Increase level
        currentPoints -= nextLevelThreshold; // Carry over extra points
        nextLevelThreshold += 480; // Increase the next level requirement
    }

    // Update the UI elements
    private void updateUI() {
        double progress = (double) currentPoints / nextLevelThreshold;
        progressBar.setProgress(progress);

        // Update labels
        pointsLabel.setText(currentPoints + " / " + nextLevelThreshold);
        levelLabel.setText("Level " + currentLevel);
    }

    private void setButtonIcon(Button button, String imagePath) {
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);

        // Apply circular clipping
        Circle clip = new Circle(20, 20, 20);
        imageView.setClip(clip);

        button.setGraphic(imageView);
    }

    @FXML
    private void showHome() {
        setSectionVisible(homePane);
        loadTopPlayers();
        loadAllPlayers();

        // Apply shadow effect to Home button
        applyShadowAnimation(homeButton, true);
        applyShadowAnimation(leagueButton, false);
        applyShadowAnimation(helpButton, false);
        applyShadowAnimation(chatButton, false);
        applyShadowAnimation(profileButton, false);
    }

    @FXML
    private void showLeague() {
        setSectionVisible(leaguePane);

        // Apply shadow effect to League button
        applyShadowAnimation(homeButton, false);
        applyShadowAnimation(leagueButton, true);
        applyShadowAnimation(helpButton, false);
        applyShadowAnimation(chatButton, false);
        applyShadowAnimation(profileButton, false);
    }

    @FXML
    private void showchat() {
        setSectionVisible(chatPane);
        connectToServer();

        // Apply shadow effect to League button
        applyShadowAnimation(homeButton, false);
        applyShadowAnimation(leagueButton, false);
        applyShadowAnimation(helpButton, false);
        applyShadowAnimation(chatButton, true);
        applyShadowAnimation(profileButton, false);
    }

    @FXML
    private void showHelp() {
        setSectionVisible(helpPane);

        // Apply shadow effect to Help button
        applyShadowAnimation(homeButton, false);
        applyShadowAnimation(leagueButton, false);
        applyShadowAnimation(helpButton, true);
        applyShadowAnimation(chatButton, false);
        applyShadowAnimation(profileButton, false);
    }

    @FXML
    private void showprofile() {
        setSectionVisible(profilePane);

        // Apply shadow effect to Help button
        applyShadowAnimation(homeButton, false);
        applyShadowAnimation(leagueButton, false);
        applyShadowAnimation(helpButton, false);
        applyShadowAnimation(chatButton, false);
        applyShadowAnimation(profileButton, true);
    }

    @FXML
    private void showHome1() {
        setSectionVisible(homePane);
        applyShadowAnimation(homeButton, true);
    }

    @FXML
    private HBox questionBox;

    @FXML
    private GridPane answerGrid;

    @FXML
    private void showRiddle() {
        setSectionVisible(riddleSection);
        loadNewRiddle();
        setBackground(questionBox);
        setBackground(answerGrid);
    }

    @FXML
    private void show2048() {
        setSectionVisible(game2048Section);
        game2048Section.setVisible(true);
        startGame();
        loadHighScore();
    }

    private final String HIGH_SCORE_FILE = "highscore.txt";

    //showing maths section
    @FXML
    private void showEasySection() {
        setSectionVisible(easySection);
        generateNewQuestion();
    }

    @FXML
    private void showHardSection() {
        setSectionVisible(hardSection);
        generateNewQuestion2();
    }

    @FXML
    private void showMaths() {
        setSectionVisible(mathsSection);
    }

    // Show Reading Section
    @FXML
    private void showReading() {
        setSectionVisible(readingSection);
    }

    @FXML
    private void showReading9to12() {
        setSectionVisible(readingSection9to12);
    }

    private void setSectionVisible(VBox visibleSection) {
        VBox[] sections = {homePane, mathsSection, readingSection9to12Updated, easySection, hardSection, riddleSection, game2048Section, readingSection, readingSection9to12, leaguePane, leaguePane1, achievementsPane, helpPane, chatPane, profilePane};

        for (VBox section : sections) {
            if (section == visibleSection) {
                fadeIn(section);
            } else {
                section.setVisible(false);
            }
        }
    }

    // Fade in effect when switching sections
    private void fadeIn(VBox section) {
        section.setVisible(true);
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), section);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    private final DropShadow shadowEffect = new DropShadow(15, Color.GRAY);

    private void applyShadowAnimation(Button button, boolean isActive) {
        DropShadow shadow = isActive ? shadowEffect : new DropShadow(0, Color.TRANSPARENT);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.001),
                        new KeyValue(button.effectProperty(), shadow)
                )
        );
        timeline.play();
    }


    //Animation
    private void addButtonAnimation(Button button) {
        // Initial button style
        button.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: white; " +
                        "-fx-border-width: 1; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 10 20; " +
                        "-fx-border-radius: 20; " +
                        "-fx-background-radius: 20;"
        );

        // Hover effect
        button.setOnMouseEntered(event -> {
            button.setStyle(
                    "-fx-background-color: rgba(255, 255, 255, 0.1); " +
                            "-fx-border-color: white; " +
                            "-fx-border-width: 1; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 14px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 10 20; " +
                            "-fx-border-radius: 20; " +
                            "-fx-background-radius: 20;"
            );
        });

        // Reset style on mouse exit
        button.setOnMouseExited(event -> {
            button.setStyle(
                    "-fx-background-color: transparent; " +
                            "-fx-border-color: white; " +
                            "-fx-border-width: 1; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 14px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 10 20; " +
                            "-fx-border-radius: 20; " +
                            "-fx-background-radius: 20;"
            );
        });

        // Click effect
        button.setOnMousePressed(event -> {
            button.setStyle(
                    "-fx-background-color: rgba(255, 255, 255, 0.2); " +
                            "-fx-border-color: white; " +
                            "-fx-border-width: 1; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 14px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 10 20; " +
                            "-fx-border-radius: 20; " +
                            "-fx-background-radius: 20;"
            );
        });

        // Reset style after click
        button.setOnMouseReleased(event -> {
            button.setStyle(
                    "-fx-background-color: rgba(255, 255, 255, 0.1); " +
                            "-fx-border-color: white; " +
                            "-fx-border-width: 1; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 14px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 10 20; " +
                            "-fx-border-radius: 20; " +
                            "-fx-background-radius: 20;"
            );
        });
    }

    //HOVER ANIMATION
    private void addHoverAnimation(Button button, String hoverColor) {
        button.setOnMouseEntered(e -> {
            button.setStyle(button.getStyle() + "-fx-background-color: " + hoverColor + ";");
            ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), button);
            scaleUp.setToX(1.3);
            scaleUp.setToY(1.3);
            scaleUp.play();
        });

        button.setOnMouseExited(e -> {
            button.setStyle(button.getStyle().replace(hoverColor, "")); // Reverts to original color
            ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), button);
            scaleDown.setToX(1);
            scaleDown.setToY(1);
            scaleDown.play();
        });
    }

    //RIDDLE SECTION CONTROLLER
    @FXML
    private Label riddleQuestion, scoreLabel;
    @FXML
    private Button optionA, optionB, optionC, optionD;

    private String correctAnswer;
    private int score = 0;

    private final String[][] riddles = {
            {"What has keys but can't open locks?", "Piano", "Lock", "Keyboard", "Treasure", "Piano"},
            {"The more you take, the more you leave behind. What am I?", "Steps", "Air", "Time", "Shadow", "Steps"},
            {"What comes once in a minute, twice in a moment, but never in a thousand years?", "Letter M", "Day", "Sun", "Moon", "Letter M"},
            {"I have hands but cannot clap. What am I?", "Clock", "Doll", "Robot", "Human", "Clock"},
            {"I’m tall when I’m young, and I’m short when I’m old. What am I?", "Candle", "Tree", "Shadow", "Person", "Candle"},
            {"What has to be broken before you can use it?", "Egg", "Glass", "Seal", "Lock", "Egg"}
    };


    private void loadNewRiddle() {
        Random rand = new Random();
        int index = rand.nextInt(riddles.length);

        String[] riddle = riddles[index];
        riddleQuestion.setText(riddle[0]);
        optionA.setText(riddle[1]);
        optionB.setText(riddle[2]);
        optionC.setText(riddle[3]);
        optionD.setText(riddle[4]);
        correctAnswer = riddle[5];

        correctAnswerLabel.setText(""); // Clear any previous message
        correctAnswerLabel.setVisible(false);
    }

    @FXML
    private Label correctAnswerLabel; // Add this label in your FXML

    @FXML
    private void checkAnswer(ActionEvent event) {
        Button selectedButton = (Button) event.getSource();

        if (selectedButton.getText().equals(correctAnswer)) {
            selectedButton.setText("Correct ✅");
            score += 10;
            correctAnswerLabel.setText(""); // Clear any previous message
        } else {
            selectedButton.setText("Wrong ❌");
            score = Math.max(score - 5, 0);

            // Display the correct answer in a separate label
            correctAnswerLabel.setVisible(true);
            correctAnswerLabel.setText("The correct answer was: " + correctAnswer);
        }

        scoreLabel.setText(String.valueOf(score));

        // Load next riddle after a short delay
        PauseTransition pause = new PauseTransition(Duration.seconds(3)); // Increased delay for visibility
        pause.setOnFinished(e -> loadNewRiddle());
        pause.play();
    }

    public void setBackground(HBox questionBox) {
        Image image = new Image(getClass().getResource("riddlebg.png").toExternalForm());
        BackgroundImage bgImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));

        questionBox.setBackground(new Background(bgImage));
    }

    public void setBackground(GridPane gridPane) {
        Image image = new Image(getClass().getResource("1.png").toExternalForm());
        BackgroundImage bgImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));

        gridPane.setBackground(new Background(bgImage));
    }
    //2048 GAME

    @FXML
    private Pane gamePane;
    private int scoree = 0;
    private int highScore = 0;
    @FXML
    private Label scoreL, highScoreLabel;
    private static final int TILE_SIZE = 100;
    private static final int GRID_SIZE = 4;
    private final int[][] board = new int[GRID_SIZE][GRID_SIZE];
    private GridPane gridPane;

    private void startGame() {
        gridPane = new GridPane();
        gamePane.getChildren().clear();  // Clear any previous game
        gamePane.getChildren().add(gridPane);  // Add the grid

        gamePane.setOnKeyPressed(this::handleKeyPress);
        gamePane.setFocusTraversable(true);  // Ensure key events work

        initializeBoard();
        drawBoard();
        updateScore(0);
    }

    private void updateScore(int points) {
        scoree += points;
        scoreL.setText("Score: " + scoree);

        if (scoree > highScore) {
            highScore = scoree;
            highScoreLabel.setText("High Score: " + highScore);
            saveHighScore();
        }
    }

    private void loadHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader(HIGH_SCORE_FILE))) {
            highScore = Integer.parseInt(reader.readLine());
            highScoreLabel.setText("High Score: " + highScore);
        } catch (IOException | NumberFormatException ignored) {
        }
    }

    private void saveHighScore() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HIGH_SCORE_FILE))) {
            writer.write(String.valueOf(highScore));
        } catch (IOException ignored) {
        }
    }

    private void initializeBoard() {
        addRandomTile();
        addRandomTile();
    }

    private void drawBoard() {
        gridPane.getChildren().clear();
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                int value = board[row][col];

                Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE);
                tile.setFill(getTileColor(value));
                tile.setStroke(Color.BLACK);

                Text text = new Text(value == 0 ? "" : String.valueOf(value));
                text.setFont(Font.font(30));

                gridPane.add(tile, col, row);
                gridPane.add(text, col, row);
            }
        }
    }

    private Color getTileColor(int value) {
        return switch (value) {
            case 2 -> Color.LINEN;
            case 4 -> Color.ANTIQUEWHITE;
            case 8 -> Color.SANDYBROWN;
            case 16 -> Color.LIGHTSALMON;
            case 32 -> Color.SALMON;
            case 64 -> Color.TOMATO;
            case 128 -> Color.KHAKI;
            case 256 -> Color.GOLDENROD;
            case 512 -> Color.CORAL;
            case 1024 -> Color.BLUE;
            case 2048 -> Color.PURPLE;
            default -> Color.LIGHTSLATEGRAY;
        };
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case UP -> moveUp();
            case DOWN -> moveDown();
            case LEFT -> moveLeft();
            case RIGHT -> moveRight();
        }
        addRandomTile();
        drawBoard();
    }

    private void moveUp() {
        for (int col = 0; col < GRID_SIZE; col++) {
            int[] merged = new int[GRID_SIZE];
            int index = 0;
            for (int row = 0; row < GRID_SIZE; row++) {
                if (board[row][col] != 0) {
                    if (index > 0 && merged[index - 1] == board[row][col]) {
                        merged[index - 1] *= 2;
                        updateScore(merged[index - 1]);  // Update score
                    } else {
                        merged[index++] = board[row][col];
                    }
                }
            }
            for (int row = 0; row < GRID_SIZE; row++) {
                board[row][col] = merged[row];
            }
        }
    }

    private void moveDown() {
        for (int col = 0; col < GRID_SIZE; col++) {
            int[] merged = new int[GRID_SIZE];
            int index = GRID_SIZE - 1;
            for (int row = GRID_SIZE - 1; row >= 0; row--) {
                if (board[row][col] != 0) {
                    if (index < GRID_SIZE - 1 && merged[index + 1] == board[row][col]) {
                        merged[index + 1] *= 2;
                        updateScore(merged[index + 1]);
                    } else {
                        merged[index--] = board[row][col];
                    }
                }
            }
            for (int row = 0; row < GRID_SIZE; row++) {
                board[row][col] = merged[row];
            }
        }
    }

    private void moveLeft() {
        for (int row = 0; row < GRID_SIZE; row++) {
            int[] merged = new int[GRID_SIZE];
            int index = 0;
            for (int col = 0; col < GRID_SIZE; col++) {
                if (board[row][col] != 0) {
                    if (index > 0 && merged[index - 1] == board[row][col]) {
                        merged[index - 1] *= 2;
                        updateScore(merged[index - 1]);
                    } else {
                        merged[index++] = board[row][col];
                    }
                }
            }
            for (int col = 0; col < GRID_SIZE; col++) {
                board[row][col] = merged[col];
            }
        }
    }

    private void moveRight() {
        for (int row = 0; row < GRID_SIZE; row++) {
            int[] merged = new int[GRID_SIZE];
            int index = GRID_SIZE - 1;
            for (int col = GRID_SIZE - 1; col >= 0; col--) {
                if (board[row][col] != 0) {
                    if (index < GRID_SIZE - 1 && merged[index + 1] == board[row][col]) {
                        merged[index + 1] *= 2;
                        updateScore(merged[index + 1]);
                    } else {
                        merged[index--] = board[row][col];
                    }
                }
            }
            for (int col = 0; col < GRID_SIZE; col++) {
                board[row][col] = merged[col];
            }
        }
    }

    private void addRandomTile() {
        Random rand = new Random();
        int row, col;
        do {
            row = rand.nextInt(GRID_SIZE);
            col = rand.nextInt(GRID_SIZE);
        } while (board[row][col] != 0);

        board[row][col] = rand.nextInt(10) == 0 ? 4 : 2;
    }

    // Maths games

    @FXML
    private Label firstNumberLabel, secondNumberLabel, resultLabel;
    @FXML
    private TextField answerInput;
    @FXML
    private Button nextQuestionButton;
    @FXML
    private ImageView operationImage;
    private int score1 = 0;
    private int streak = 0;

    @FXML
    private Label scoreLabel1;
    private boolean isAddition;
    private int correctAnswer1;
    // Function to generate a new question
    @FXML
    private void generateNewQuestion() {
        Random random = new Random();
        int num1 = random.nextInt(999) + 10;  // Larger range for better variation
        int num2 = random.nextInt(999) + 10;

        isAddition = random.nextBoolean();

        if (!isAddition && num1 < num2) { // Ensure no negative subtraction
            int temp = num1;
            num1 = num2;
            num2 = temp;
        }

        correctAnswer1 = isAddition ? num1 + num2 : num1 - num2;

        // Set the correct operation image
        String imagePath = isAddition ? "plus.png" : "minus.png";
        operationImage.setImage(new Image(getClass().getResourceAsStream(imagePath)));

        // Properly align numbers for column addition/subtraction
        int maxDigits = Math.max(String.valueOf(num1).length(), String.valueOf(num2).length());

        firstNumberLabel.setText(formatNumber(num1, maxDigits));
        secondNumberLabel.setText(formatNumber(num2, maxDigits));

        // Hide result and reset UI
        resultLabel.setVisible(false);
        resultLabel.setText("");
        answerInput.clear();
        nextQuestionButton.setVisible(false);
    }
    private String formatNumber(int num, int maxDigits) {
        String numStr = String.valueOf(num);

        // Ensure the number is right-aligned by adding spaces to the left
        while (numStr.length() < maxDigits) {
            numStr = " " + numStr;
        }

        return numStr;
    }
    // Function to check the answer
    @FXML
    private void checkAnswer1() {
        String userInput = answerInput.getText().trim();

        if (userInput.isEmpty()) {
            showResult("Enter a number!", "red");
            return;
        }

        try {
            int userAnswer = Integer.parseInt(userInput);

            String explanation = ""; // Variable to hold the detailed explanation

            if (userAnswer == correctAnswer1) {
                showResult("Correct! ✅", "green");
                streak++;

                // Bonus points if 5 correct answers in a row
                if (streak >= 5) {
                    score1 += 20; // Bonus points
                    streak = 0;  // Reset streak after bonus
                } else {
                    score1 += 10; // Normal points
                }

                explanation = generateCalculationExplanation(isAddition, firstNumberLabel.getText().trim(), secondNumberLabel.getText().trim());
            } else {
                showResult("Wrong ❌ Correct Answer: " + correctAnswer1, "red");
                streak = 0; // Reset streak on incorrect answer

                explanation = generateCalculationExplanation(isAddition, firstNumberLabel.getText().trim(), secondNumberLabel.getText().trim());
            }

            scoreLabel1.setText("Score: " + score1); // Update score on UI
            nextQuestionButton.setVisible(true); // Show "Next Question" button

            // Show detailed calculation explanation
            showCalculationExplanation(explanation);

        } catch (NumberFormatException e) {
            showResult("Enter a valid number!", "red");
        }
    }

    private String generateCalculationExplanation(boolean isAddition, String num1Str, String num2Str) {
        int num1 = Integer.parseInt(num1Str.trim());
        int num2 = Integer.parseInt(num2Str.trim());

        StringBuilder explanation = new StringBuilder();

        if (isAddition) {
            explanation.append("Step-by-step Addition:\n");

            int carry = 0;
            int placeValue = 1;

            while (num1 > 0 || num2 > 0 || carry > 0) {
                int digit1 = num1 % 10;
                int digit2 = num2 % 10;

                int sum = digit1 + digit2 + carry;
                carry = sum / 10; // Calculate carry

                explanation.append(String.format("Place %d: %d + %d + Carry(%d) = %d (Carry: %d)\n",
                        placeValue, digit1, digit2, carry - sum / 10, sum % 10, carry));

                num1 /= 10;
                num2 /= 10;
                placeValue++;
            }

        } else {
            explanation.append("Step-by-step Subtraction:\n");

            int borrow = 0;
            int placeValue = 1;

            while (num1 > 0 || num2 > 0) {
                int digit1 = num1 % 10 - borrow;
                int digit2 = num2 % 10;

                if (digit1 < digit2) {
                    borrow = 1;
                    digit1 += 10; // Borrowing logic
                    explanation.append(String.format("Place %d: %d - %d (Borrowing: +10) = %d\n",
                            placeValue, digit1 - borrow * 10, digit2, digit1 - digit2));
                } else {
                    borrow = 0;
                    explanation.append(String.format("Place %d: %d - %d = %d\n",
                            placeValue, digit1 + borrow * 10, digit2, digit1 - digit2));
                }

                num1 /= 10;
                num2 /= 10;
                placeValue++;
            }
        }

        return explanation.toString();
    }


    private void showCalculationExplanation(String explanation) {
        resultLabel.setText(resultLabel.getText() + "\n" + explanation); // Append explanation to result label
        resultLabel.setVisible(true); // Ensure it is visible
    }
    // Function to update result label
    private void showResult(String message, String color) {
        resultLabel.setText(message);
        resultLabel.setStyle("-fx-text-fill: " + color + ";");
        resultLabel.setVisible(true); // Make it visible
    }

    @FXML
    private Label firstNumberLabel2, secondNumberLabel2, resultLabel2;
    @FXML
    private TextField answerInput2;
    @FXML
    private Button nextQuestionButton2;
    @FXML
    private ImageView operationImage2;
    @FXML
    private Label scoreLabel2;

    private int score2 = 0;
    private int streak2 = 0;
    private boolean isMultiplication;
    private int correctAnswer2;

    // Function to generate a new question
    @FXML
    private void generateNewQuestion2() {
        Random random1 = new Random();
        int num1 = random1.nextInt(10) + 2;  // Ensures better division results
        int num2 = random1.nextInt(10) + 2;

        isMultiplication = random1.nextBoolean(); // Randomly choose between multiplication and division

        if (!isMultiplication) {
            // Ensure num1 is a multiple of num2 for clean division
            num1 = num1 * num2;
        }

        correctAnswer2 = isMultiplication ? num1 * num2 : num1 / num2;

        // Set the correct operation image
        String imagePath = isMultiplication ? "multiply.png" : "divide.png";
        operationImage2.setImage(new Image(getClass().getResourceAsStream(imagePath)));

        // Properly align numbers for column multiplication/division
        int maxDigits = Math.max(String.valueOf(num1).length(), String.valueOf(num2).length());

        firstNumberLabel2.setText(formatNumber1(num1, maxDigits));
        secondNumberLabel2.setText(formatNumber1(num2, maxDigits));

        // Hide result and reset UI
        resultLabel2.setVisible(false);
        resultLabel2.setText("");
        answerInput2.clear();
        nextQuestionButton2.setVisible(false);
    }

    private String formatNumber1(int num, int maxDigits) {
        String numStr = String.valueOf(num);

        // Ensure the number is right-aligned by adding spaces to the left
        while (numStr.length() < maxDigits) {
            numStr = " " + numStr;
        }

        return numStr;
    }

    // Function to check the answer
    @FXML
    private void checkAnswer2() {
        String userInput = answerInput2.getText().trim();

        if (userInput.isEmpty()) {
            showResult2("Enter a number!", "red");
            return;
        }

        try {
            int userAnswer = Integer.parseInt(userInput);
            String explanation = ""; // Variable to hold the detailed explanation

            if (userAnswer == correctAnswer2) {
                showResult2("Correct! ✅", "green");
                streak2++;

                // Bonus points if 5 correct answers in a row
                if (streak2 >= 5) {
                    score2 += 20; // Bonus points
                    streak2 = 0;  // Reset streak after bonus
                } else {
                    score2 += 10; // Normal points
                }

                explanation = generateCalculationExplanation1(isMultiplication, firstNumberLabel2.getText().trim(), secondNumberLabel2.getText().trim());
            } else {
                showResult2("Wrong ❌ Correct Answer: " + correctAnswer2, "red");
                streak2 = 0; // Reset streak on incorrect answer

                explanation = generateCalculationExplanation1(isMultiplication, firstNumberLabel2.getText().trim(), secondNumberLabel2.getText().trim());
            }

            scoreLabel2.setText("Score: " + score2); // Update score on UI
            nextQuestionButton2.setVisible(true); // Show "Next Question" button

            // Show detailed calculation explanation
            showCalculationExplanation1(explanation);

        } catch (NumberFormatException e) {
            showResult2("Enter a valid number!", "red");
        }
    }

    private String generateCalculationExplanation1(boolean isMultiplication, String num1Str, String num2Str) {
        int num1 = Integer.parseInt(num1Str.trim());
        int num2 = Integer.parseInt(num2Str.trim());

        StringBuilder explanation = new StringBuilder();

        if (isMultiplication) {
            explanation.append("Step-by-step Multiplication:\n");

            for (int i = 0; i < num2; i++) {
                explanation.append(num1).append(" + ");
            }

            explanation.setLength(explanation.length() - 3); // Remove last " + "
            explanation.append(" = ").append(num1 * num2).append("\n");

        } else {
            explanation.append("Step-by-step Division:\n");

            while (num1 >= num2) {
                num1 -= num2;
                explanation.append(num1 + num2).append(" - ").append(num2).append(" = ").append(num1).append("\n");
            }

            explanation.append("The remainder is: ").append(num1).append("\n");
        }

        return explanation.toString();
    }

    private void showCalculationExplanation1(String explanation) {
        resultLabel2.setText(resultLabel2.getText() + "\n" + explanation); // Append explanation to result label
        resultLabel2.setVisible(true); // Ensure it is visible
    }
    // Function to update result label
    private void showResult2(String message, String color) {
        resultLabel2.setText(message);
        resultLabel2.setStyle("-fx-text-fill: " + color + ";");
        resultLabel2.setVisible(true); // Make it visible
    }

    //Riddle section controller

    @FXML
    private void enlargeButton(MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setScaleX(1.1);
        btn.setScaleY(1.1);
    }

    @FXML
    private void resetButton(MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setScaleX(1.0);
        btn.setScaleY(1.0);
    }

    @FXML
    private void showagesection(){
        setSectionVisible(readingSection);
    }

    @FXML
    private Label timerLabel, readingScoreLabel, mcqQuestionLabel;
//    @FXML
//    private TextArea readingTextArea;
    @FXML
    private VBox ageSelectionScreen, readingSection9to12, mcqSection;
    @FXML
    private Button startTimerButton, stopTimerButton;

    private Timeline timeline;
    private int secondsElapsed = 0;

    @FXML
    private void startReadingTimer() {
        secondsElapsed = 0;
        timerLabel.setText("00:00");

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            secondsElapsed++;
            int minutes = secondsElapsed / 60;
            int seconds = secondsElapsed % 60;
            timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        startTimerButton.setDisable(true);
        stopTimerButton.setVisible(true);
    }

    private int questionCounter = 0;
    private int currentScore = 0;
    private final String[] demoPassages = {
            "The Arctic fox is a small fox native to the Arctic regions. These remarkable creatures can survive temperatures as low as -50°C. Their thick fur changes color with the seasons - white in winter and brown in summer. Arctic foxes primarily feed on lemmings, voles, and other small rodents.",
            "The giant panda is a bear species endemic to China. Despite being classified as carnivores, pandas' diet consists almost exclusively of bamboo. An adult panda can eat 12-38 kg of bamboo per day! Their distinctive black-and-white coloring helps them camouflage in snowy and rocky habitats."
    };

    private final String[][][] demoQuestions = {
            { // Questions for first passage
                    {"What is the main adaptation of Arctic foxes mentioned?", "Thick color-changing fur", "Hibernation ability", "Webbed feet", "Long tail", "A"},
                    {"What do Arctic foxes primarily eat?", "Fish", "Small rodents", "Berries", "Insects", "B"},
                    {"What temperature can Arctic foxes survive?", "-10°C", "-50°C", "-30°C", "-70°C", "B"},
                    {"What color is their fur in winter?", "Brown", "Gray", "White", "Black", "C"},
                    {"Where are Arctic foxes found?", "Antarctica", "Arctic regions", "Alpine forests", "North America", "B"}
            },
            { // Questions for second passage
                    {"What is the giant panda's primary food?", "Bamboo", "Meat", "Fruits", "Fish", "A"},
                    {"How much bamboo can a panda eat daily?", "5-10 kg", "12-38 kg", "40-50 kg", "1-5 kg", "B"},
                    {"Pandas are native to which country?", "Japan", "India", "China", "Russia", "C"},
                    {"What helps pandas camouflage?", "Stripes", "Black-and-white coloring", "Speed", "Size", "B"},
                    {"Pandas are classified as:", "Herbivores", "Carnivores", "Omnivores", "Insectivores", "B"}
            }
    };
    private int currentPassageIndex = 0;

    // Modify the stopReadingTimer method
    @FXML
    private void stopReadingTimer() {
        if (timeline != null) {
            timeline.stop();
        }
        stopTimerButton.setDisable(true);

        // Initialize MCQ session
        questionCounter = 0;
        readingScoreLabel.setText("0");
        mcqSection.setVisible(true);
        mcqSection.setManaged(true);
        loadNewPassageAndQuestions();
    }

    // Add these new methods
    private void loadNewPassageAndQuestions() {
        // Set new passage text
        readingTextArea.setText(demoPassages[currentPassageIndex]);

        // Load first question
        loadQuestion(0);
    }

    @FXML
    public Button mcqOptionA, mcqOptionB, mcqOptionC, mcqOptionD;
    private void loadQuestion(int questionIndex) {
        String[] questionData = demoQuestions[currentPassageIndex][questionIndex];
        mcqQuestionLabel.setText(questionData[0]);
        mcqOptionA.setText(questionData[1]);
        mcqOptionB.setText(questionData[2]);
        mcqOptionC.setText(questionData[3]);
        mcqOptionD.setText(questionData[4]);
    }

    @FXML
    private void handleAnswerA() {
        checkAnswer("A");
    }

    @FXML
    private void handleAnswerB() {
        checkAnswer("B");
    }

    @FXML
    private void handleAnswerC() {
        checkAnswer("C");
    }

    @FXML
    private void handleAnswerD() {
        checkAnswer("D");
    }

    private void checkAnswer(String selectedOption) {
        String[] questionData = demoQuestions[currentPassageIndex][questionCounter];
        String correctAnswer = questionData[5];
        Button selectedButton = null;

        // Get the selected button
        switch(selectedOption) {
            case "A": selectedButton = mcqOptionA; break;
            case "B": selectedButton = mcqOptionB; break;
            case "C": selectedButton = mcqOptionC; break;
            case "D": selectedButton = mcqOptionD; break;
        }

        if (selectedButton != null) {
            if (selectedOption.equals(correctAnswer)) {
                selectedButton.setText("✅ Correct");
                currentScore += 20;
            } else {
                selectedButton.setText("❌ Wrong");
                currentScore = Math.max(currentScore - 5, 0);  // Prevent negative scores

                // Highlight correct answer
                switch(correctAnswer) {
                    case "A": mcqOptionA.setText("✅ " + mcqOptionA.getText()); break;
                    case "B": mcqOptionB.setText("✅ " + mcqOptionB.getText()); break;
                    case "C": mcqOptionC.setText("✅ " + mcqOptionC.getText()); break;
                    case "D": mcqOptionD.setText("✅ " + mcqOptionD.getText()); break;
                }
            }

            readingScoreLabel.setText(String.valueOf(currentScore));

            // Disable all buttons after answering
            mcqOptionA.setDisable(true);
            mcqOptionB.setDisable(true);
            mcqOptionC.setDisable(true);
            mcqOptionD.setDisable(true);

            // Proceed after 1 second delay
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> {
                if (questionCounter + 1 < 5) {
                    questionCounter++;
                    loadQuestion(questionCounter);
                    // Re-enable buttons for next question
                    mcqOptionA.setDisable(false);
                    mcqOptionB.setDisable(false);
                    mcqOptionC.setDisable(false);
                    mcqOptionD.setDisable(false);
                } else {
                    mcqSection.setVisible(false);
                    resetReadingSession();
                }
            });
            pause.play();
        }
    }


    private void resetReadingSession() {
        // Reset timer
        secondsElapsed = 0;
        timerLabel.setText("00:00");
        startTimerButton.setDisable(false);
        stopTimerButton.setVisible(false);
        stopTimerButton.setDisable(false);

        // Show reading section again
        setSectionVisible(readingSection9to12);
    }
    @FXML
    public VBox readingSection9to12Updated;
    @FXML
    public void showreading5to8(){
        setSectionVisible(readingSection9to12Updated);
    }
    @FXML
    private Label fillInScoreLabelUpdated;
    @FXML
    private Label fillInQuestionLabelUpdated;
    @FXML
    private TextField fillInAnswerFieldUpdated;
    private int currentParagraphIndex = 0;
    private int currentFillInScore = 0;
    private int currentFillInQuestionIndex = 0;

    // Example fill-in-the-blank questions
    private final String[][] fillInQuestions = {
            {"Liam found a mysterious ___ in the forest.", "cave"},
            {"The cave was filled with glowing ___ .", "crystals"},
            {"Liam was an ___ from the village.", "explorer"},
    };

    @FXML
    private void submitFillInAnswerUpdated() {
        String userAnswer = fillInAnswerFieldUpdated.getText().trim().toLowerCase();
        String correctAnswer = fillInQuestionsUpdated[currentParagraphIndex][currentFillInQuestionIndex][1];

        if (userAnswer.equals(correctAnswer)) {
            currentFillInScore += 20;
            fillInScoreLabelUpdated.setText(String.valueOf(currentFillInScore));
            fillInQuestionLabelUpdated.setText("Correct! ✅");
        } else {
            fillInQuestionLabelUpdated.setText("Wrong! ❌ The correct answer was '" + correctAnswer + "'.");
        }

        // Load next question after a short delay
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> loadNextFillInQuestionUpdated());
        pause.play();
    }


    private void loadNextFillInQuestionUpdated() {
        currentFillInQuestionIndex++;

        if (currentFillInQuestionIndex < fillInQuestionsUpdated[currentParagraphIndex].length) {
            fillInQuestionLabelUpdated.setText(fillInQuestionsUpdated[currentParagraphIndex][currentFillInQuestionIndex][0]);
            fillInAnswerFieldUpdated.clear(); // Clear previous answer
        } else {
            // Hide Fill in the Blanks section and show Reading Section again
            fillInTheBlanksSectionUpdated.setVisible(false);

            // Show next paragraph and reset for next set of questions
            currentParagraphIndex++;

            if (currentParagraphIndex < paragraphsUpdated.length) {
                resetFillInSessionUpdated(); // Reset session for new round

                // Show reading section with new paragraph
                readingSection9to12Updated.setVisible(true);
                readingTextAreaUpdated.setText(paragraphsUpdated[currentParagraphIndex]);

                // Load first question of the new paragraph
                currentFillInQuestionIndex = 0;
                loadNextFillInQuestionUpdated();
            } else {
                // Optionally handle end of all questions (e.g., show final score)
                resetFillInSessionUpdated();
            }
        }
    }


    private void resetFillInSessionUpdated() {
        currentFillInScore = 0;
        currentFillInQuestionIndex = 0;
        currentParagraphIndex = 0;
        fillInScoreLabelUpdated.setText("0");

        // Reset timer logic similar to your existing one
        secondsElapsed = 0;
        timerLabelUpdated.setText("00:00");
        startTimerButtonUpdated.setDisable(false);
        stopTimerButtonUpdated.setVisible(false);
        stopTimerButtonUpdated.setDisable(false);
    }

    @FXML
    private Label timerLabelUpdated; // Label to display the timer
    private Timeline timer; // Timeline for the timer
    private int secondsElapsed1 = 0; // Variable to track elapsed seconds

    @FXML
    private void startReadingTimerUpdated() {
        secondsElapsed1 = 0; // Reset elapsed time
        timerLabelUpdated.setText("00:00"); // Reset display

        // Create a new Timeline for the timer
        timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            secondsElapsed1++;
            int minutes = secondsElapsed1 / 60;
            int seconds = secondsElapsed1 % 60;
            timerLabelUpdated.setText(String.format("%02d:%02d", minutes, seconds)); // Update display
        }));

        timer.setCycleCount(Timeline.INDEFINITE); // Set to run indefinitely
        timer.play(); // Start the timer

        // Disable start button and show stop button
        startTimerButtonUpdated.setDisable(true);
        stopTimerButtonUpdated.setVisible(true);
    }
    @FXML
    private Button startTimerButtonUpdated, stopTimerButtonUpdated;
    @FXML
    private void stopReadingTimerUpdated() {
        if (timer != null) {
            timer.stop(); // Stop the timer
        }

        // Disable stop button and enable start button
        stopTimerButtonUpdated.setDisable(true);
        startTimerButtonUpdated.setDisable(false);

        // Make the Fill in the Blanks section visible
        fillInTheBlanksSectionUpdated.setVisible(true);


        // Optionally, you can show a message or perform other actions when the timer stops
    }

    @FXML
    private void showAgeSelectionUpdated() {
    setSectionVisible(ageSelectionScreen);
    }

    // Example fill-in-the-blank questions for both paragraphs
    private final String[][][] fillInQuestionsUpdated = {
            { // First paragraph questions
                    {"Liam found a mysterious ___ in the forest.", "cave"},
                    {"The cave was filled with glowing ___ .", "crystals"},
                    {"Liam was an ___ from the village.", "explorer"},
                    {"The forest was home to many ___ creatures.", "small"},
                    {"Liam loved to ___ the hidden trails.", "explore"}
            },
            { // Second paragraph questions
                    {"The giant panda primarily eats ___ .", "bamboo"},
                    {"Pandas are native to ___ .", "China"},
                    {"An adult panda can eat up to ___ kg of bamboo daily.", "38"},
                    {"Pandas are classified as ___ .", "carnivores"},
                    {"The distinctive black-and-white coloring helps pandas ___ .", "camouflage"}
            }
    };

    // Example paragraphs
    private final String[] paragraphsUpdated = {
            "Once upon a time in a small village, there lived a young boy named Liam. He loved exploring the dense forest near his home, where he would often encounter small creatures and discover hidden trails. One day, while venturing deeper into the woods, he stumbled upon a mysterious cave. Curiosity got the best of him, and he decided to step inside. The cave was filled with glowing crystals, casting beautiful lights all around.",

            "The giant panda is a bear species endemic to China. Despite being classified as carnivores, pandas' diet consists almost exclusively of bamboo. An adult panda can eat up to 38 kg of bamboo daily! Their distinctive black-and-white coloring helps them camouflage in snowy and rocky habitats."
    };

    //chatting section

    @FXML
    private VBox chatContainer, addFriendContainer, friendsListContainer;

    @FXML
    private ListView<String> messageListView;
    @FXML
    private TextField messageInputField;

    @FXML
    private TextField friendNameInputField;
    @FXML
    private Label addFriendResultLabel;

    @FXML
    private ListView<String> friendsListView;

    private ArrayList<String> friends = new ArrayList<>(); // Store friends list

    // Show Chat Section
    @FXML
    public void showChatSection() {
        hideAllSections();
        chatContainer.setVisible(true);
        messageInputField.clear();
        messageListView.getItems().clear();
    }


    // Show Add Friends Section
    @FXML
    public void showAddFriendsSection() {
        hideAllSections();
        addFriendContainer.setVisible(true);
        friendNameInputField.clear();
        addFriendResultLabel.setText("");
    }

    // Add Friend to Friends List
    @FXML
    public void addFriend() {
        String friendName = friendNameInputField.getText().trim();
        if (!friendName.isEmpty() && !friends.contains(friendName)) {
            friends.add(friendName);
            addFriendResultLabel.setText("Friend added!");
            friendNameInputField.clear();
        } else {
            addFriendResultLabel.setText("Enter a valid name or friend already exists.");
        }
    }

    // Show Friends List Section
    @FXML
    public void showFriendsListSection() {
        hideAllSections();
        friendsListContainer.setVisible(true);
        friendsListView.getItems().clear();
        friendsListView.getItems().addAll(friends);
    }

    // Chat with Selected Friend from Friends List
    @FXML
    public void chatWithSelectedFriend() {
        String selectedFriend = friendsListView.getSelectionModel().getSelectedItem();
        if (selectedFriend != null) {
            showChatSection(); // Switch to chat section
            messageListView.getItems().add("Chatting with " + selectedFriend);
        }
    }

    // Hide all sections (helper method)
    private void hideAllSections() {
        chatContainer.setVisible(false);
        addFriendContainer.setVisible(false);
        friendsListContainer.setVisible(false);
    }

    // Networking components
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private boolean isConnected = false;

    private void connectToServer() {
        new Thread(() -> {
            try {
                socket = new Socket("192.168.212.111", 6666);
                dos = new DataOutputStream(socket.getOutputStream());
                dis = new DataInputStream(socket.getInputStream());
                isConnected = true;

                // Start message receiving thread
                startReceiverThread();

                Platform.runLater(() ->
                        showAlert("Connected to server!", Alert.AlertType.INFORMATION));

            } catch (Exception e) {
                Platform.runLater(() ->
                        showAlert("Connection failed: " + e.getMessage(), Alert.AlertType.ERROR));
            }
        }).start();
    }

    private void startReceiverThread() {
        new Thread(() -> {
            while (isConnected) {
                try {
                    String message = dis.readUTF();
                    Platform.runLater(() ->
                            messageListView.getItems().add("Friend: " + message));

                    if(message.equalsIgnoreCase("sayonara")) {
                        closeConnection();
                        break;
                    }
                } catch (Exception e) {
                    closeConnection();
                    break;
                }
            }
        }).start();
    }

    @FXML
    public void sendMessage() {
        if(!isConnected) {
            showAlert("Not connected to server!", Alert.AlertType.WARNING);
            return;
        }

        String message = messageInputField.getText().trim();
        if (!message.isEmpty()) {
            try {
                dos.writeUTF(message);
                dos.flush();
                Platform.runLater(() -> {
                    messageListView.getItems().add("You: " + message);
                    messageInputField.clear();
                });

                if(message.equalsIgnoreCase("sayonara")) {
                    closeConnection();
                }

            } catch (Exception e) {
                closeConnection();
                showAlert("Message send failed: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private void closeConnection() {
        isConnected = false;
        try {
            if(dos != null) dos.close();
            if(dis != null) dis.close();
            if(socket != null) socket.close();
        } catch (Exception e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }

    private void showAlert(String message, Alert.AlertType type) {
        Platform.runLater(() -> {
            Alert alert = new Alert(type);
            alert.setContentText(message);
            alert.show();
        });
    }
    
    //League part

    @FXML
    private ListView<String> topPlayersListView; // ListView for top players
    @FXML
    private ListView<String> allPlayersListView; // ListView for all players
    @FXML
    private ListView<String> achievementsListView; // ListView for achievements

    private void loadTopPlayers() {
        List<Player> topPlayers = Backend.getTopPlayers(); // Fetch top players from backend

        for (Player player : topPlayers) {
            String formattedName = player.getName() + " - Score: " + player.getScore();
            topPlayersListView.getItems().add(formattedName);
        }
    }

    private void loadAllPlayers() {
        List<Player> allPlayers = Backend.getAllPlayers(); // Fetch all players from backend

        for (Player player : allPlayers) {
            String formattedName = player.getName() + " - Score: " + player.getScore();
            if (!topPlayersListView.getItems().contains(formattedName)) { // Avoid duplicates in the common list
                allPlayersListView.getItems().add(formattedName);
            }
        }
    }

    @FXML
    private VBox achievementsPane;

    @FXML
    private Label overallProgressLabel;

    @FXML
    private VBox achievementsList;

//    private void loadAchievements() {
//        // Overall Progress
//        overallProgressLabel.setText("🏆 40/154");
//
//        // Set Achievements - You can load this from a database or a list
//        // Update these in FXML to match your locked/unlocked state
//    }

    @FXML
    public VBox leaguePane1;
    @FXML
    public void showLeagueSection() {
        setSectionVisible(leaguePane1);
    }

    // Show Achievements Section
    @FXML
    public void showAchievementsSection() {
        setSectionVisible(achievementsPane);
    }
}
