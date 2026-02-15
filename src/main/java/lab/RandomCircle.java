package lab;

import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class RandomCircle extends Circle {
	
    private boolean captured = false;
    private final double paneWidth;
    private final double paneHeight;
    private TranslateTransition transition;

    public RandomCircle(double paneWidth, double paneHeight) {
        super(Math.random() * 20 + 10); // Radius between 10 and 30
        this.paneWidth = paneWidth;
        this.paneHeight = paneHeight;
        this.setFill(Color.color(Math.random(), Math.random(), Math.random()));
        adjustInitialPosition();
        initMovementAnimation();
    }

    private void adjustInitialPosition() {
        double minX = getRadius();
        double maxX = paneWidth - getRadius();
        double minY = getRadius();
        double maxY = paneHeight - getRadius();
        this.setCenterX(minX + (Math.random() * (maxX - minX)));
        this.setCenterY(minY + (Math.random() * (maxY - minY)));
    }

    private void initMovementAnimation() {
        transition = new TranslateTransition(Duration.seconds(3), this);
        double maxTranslateX = paneWidth - 2 * getRadius();
        double maxTranslateY = paneHeight - 2 * getRadius();
        transition.setByX(Math.random() * maxTranslateX - getCenterX() + getRadius());
        transition.setByY(Math.random() * maxTranslateY - getCenterY() + getRadius());
        transition.setAutoReverse(true);
        transition.setCycleCount(TranslateTransition.INDEFINITE);
        transition.play();
    }

    public void captureAndCenter() {
        this.captured = true;
        if (transition != null) {
            transition.stop(); 
        }
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), this);
        transition.setToX(paneWidth / 2 - getCenterX());
        transition.setToY(paneHeight / 2 - getCenterY());
        transition.play(); 
        this.setDisable(true); 
    }



    public boolean isCaptured() {
        return captured;
    }
}

