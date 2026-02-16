package lab;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.util.ArrayList;

public class Controller {

    @FXML private Pane gamePane;
    @FXML private MenuItem normalModeMenuItem, extraModeMenuItem;
    @FXML private Button playButton, playAgainButton;
    @FXML private Text winText;
    @FXML private Text instruction;

    private ArrayList<RandomCircle> circles = new ArrayList<>();
    private final int TOTAL_CIRCLES = 5;
    private Timeline gameTimeline;
    private boolean isExtraMode = false;

    @FXML
    private void initialize() {
        setupUI();
    }

    @FXML
    private void handleNormalMode() {
        isExtraMode = false;
        resetAndStartGame();
    }

    @FXML
    private void handleExtraMode() {
        isExtraMode = true;
        resetAndStartGame();
    }

    @FXML
    private void handlePlay() {
        resetAndStartGame();
    }

    @FXML
    private void handlePlayAgain() {
        resetAndStartGame();
    }

    private void setupUI() {
        winText.setVisible(false);
        playAgainButton.setVisible(false);
        playButton.setVisible(true); 
    }

    private void resetAndStartGame() {
        winText.setVisible(false);
        playAgainButton.setVisible(false);
        playButton.setVisible(true);

        resetGameState();  
        if (isExtraMode) {
            startExtraGame();
        } else {
            for (int i = 0; i < TOTAL_CIRCLES; i++) {
                addNewCircle();
            }
        }
    }

    private void startExtraGame() {
        resetGameState(); 
         // Spawn 5 circles immediately at the start
        for (int i = 0; i < TOTAL_CIRCLES; i++) {
            addNewCircle();
        }
        gameTimeline = new Timeline(new KeyFrame(Duration.seconds(1.2), e -> Platform.runLater(this::addNewCircle)));
        gameTimeline.setCycleCount(Timeline.INDEFINITE);
        gameTimeline.play();
    }

    private void addNewCircle() {
        RandomCircle circle = new RandomCircle(gamePane.getWidth(), gamePane.getHeight());
        circle.setOnMouseClicked(ev -> captureCircle(circle));
        circles.add(circle);
        gamePane.getChildren().add(circle);
    }

    private void captureCircle(RandomCircle circle) {
        circle.captureAndCenter();
        checkGameWon();
    }

    private void checkGameWon() {
        for (RandomCircle circle : circles) {
            if (!circle.isCaptured()) {
                return; 
            }
        }
        gameWon(); 
    }

    private void gameWon() {
        winText.setVisible(true);
        playAgainButton.setVisible(true);
        playButton.setVisible(false);
        suggestModeSwitch();
    }
    
    private void suggestModeSwitch() {
        if (isExtraMode) {
            normalModeMenuItem.setDisable(false);
            extraModeMenuItem.setDisable(true);  
        } else {
            normalModeMenuItem.setDisable(true);
            extraModeMenuItem.setDisable(false);  
        }
    }
    
    private void resetGameState() {
        stopAndClearGameTimeline();
        
        for (RandomCircle circle : circles) {
            gamePane.getChildren().remove(circle);
        }
        circles.clear();

        playButton.setVisible(false);
        winText.setVisible(false);  
        playAgainButton.setVisible(false);
        instruction.setVisible(false);  
    }

    private void stopAndClearGameTimeline() {
        if (gameTimeline != null) {
            gameTimeline.stop();
            gameTimeline = null;
        }
    }
}


