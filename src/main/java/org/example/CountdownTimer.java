package org.example;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CountdownTimer extends Application {

    private int remainingSeconds = 0;
    private Timeline timeline;
    private FadeTransition fadeTransition;

    private Label timerLabel;
    private Label errorLabel;
    private TextField inputField;
    private Button pauseResumeBtn;

    @Override
    public void start(Stage primaryStage) {
        // 1. UI Elements
        timerLabel = new Label("00:00");
        timerLabel.setFont(Font.font("Monospaced", FontWeight.BOLD, 52));

        inputField = new TextField();
        inputField.setPromptText("Enter minutes");
        inputField.setMaxWidth(120);

        errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);

        Button startBtn = new Button("Start");
        pauseResumeBtn = new Button("Pause");
        Button resetBtn = new Button("Reset");

        HBox controls = new HBox(10, startBtn, pauseResumeBtn, resetBtn);
        controls.setAlignment(Pos.CENTER);

        VBox root = new VBox(16, inputField, errorLabel, timerLabel, controls);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new javafx.geometry.Insets(30));

        setupTimeline();
        setupFadeTransition();

        startBtn.setOnAction(e -> handleStart());
        pauseResumeBtn.setOnAction(e -> handlePauseResume());
        resetBtn.setOnAction(e -> handleReset());

        Scene scene = new Scene(root, 340, 260);
        primaryStage.setTitle("Productivity Timer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            remainingSeconds--;
            updateLabel();
            if (remainingSeconds <= 0) {
                stopTimerAndAlert();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    private void setupFadeTransition() {
        fadeTransition = new FadeTransition(Duration.seconds(0.5), timerLabel);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.1);
        fadeTransition.setAutoReverse(true);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
    }

    private void handleStart() {
        try {
            int minutes = Integer.parseInt(inputField.getText().trim());
            if (minutes <= 0) throw new NumberFormatException();

            errorLabel.setText("");
            remainingSeconds = minutes * 60;
            updateLabel();
            resetAlertStyle();

            timeline.playFromStart();
            pauseResumeBtn.setText("Pause");
        } catch (NumberFormatException e) {
            errorLabel.setText("Invalid input! Enter positive integer.");
        }
    }

    private void handlePauseResume() {
        if (timeline.getStatus() == Animation.Status.RUNNING) {
            timeline.pause();
            pauseResumeBtn.setText("Resume");
        } else if (remainingSeconds > 0) {
            timeline.play();
            pauseResumeBtn.setText("Pause");
        }
    }

    private void handleReset() {
        timeline.stop();
        fadeTransition.stop();
        remainingSeconds = 0;
        updateLabel();
        resetAlertStyle();
        pauseResumeBtn.setText("Pause");
        inputField.clear();
    }

    private void updateLabel() {
        int mins = remainingSeconds / 60;
        int secs = remainingSeconds % 60;
        timerLabel.setText(String.format("%02d:%02d", mins, secs));
    }

    private void stopTimerAndAlert() {
        timeline.stop();
        timerLabel.setTextFill(Color.RED);
        fadeTransition.play();
    }

    private void resetAlertStyle() {
        timerLabel.setTextFill(Color.BLACK);
        timerLabel.setOpacity(1.0);
        fadeTransition.stop();
    }
}