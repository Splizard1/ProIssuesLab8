package uk.ac.swansea.cs135.labs.lab8;

import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;


import static org.junit.jupiter.api.Assertions.*;

public class ScoreTest extends ApplicationTest {

    // Holds the  current running game.
    private Game game;

    public void start(Stage stage) throws Exception {
        // Create the JavaFX application by instantiating it and
        // manually calling the JavaFX start method.
        game = new Game();
        game.start(stage);
        stage.toFront();
    }

    /**
     * Test: Score zero points (no clicks).
     */
    @Test
    public void testScoreZeroPoints() throws InterruptedException {

        // Make the square red and wait for the game duration
        // Wait for the game duration (8.5 seconds)
        Thread.sleep(8500);

        // Click one more time (after time is up)
        clickOn(".button");

        // Check the label to ensure it says "Time's up!"
        Label resultLabel = lookup("#resultBox").query();
        assertEquals("Time's up! Your final score is: 0", resultLabel.getText());
    }

    /**
     * Test: Score one point (correct click).
     */
    @Test
    public void testScoreOnePoint() throws InterruptedException {
        // Pause for a while to simulate the game running
        Thread.sleep(500); // Pause for 0.5 seconds

        // Get the coordinates of the red square
        int x = game.getDisplayedSquareX();
        int y = game.getDisplayedSquareY();

        // Make a correct click on the displayed square
        clickOn(getButtonAt(x, y));

        // Wait for a new square to appear
        Thread.sleep(500); // Wait for the game to update the square

        // Wait for the game duration (8.5 seconds)
        Thread.sleep(8500);

        // Click one more time (after time is up)
        clickOn(".button");

        // Check the label to ensure it shows the correct score
        Label resultLabel = lookup("#resultBox").query();
        assertEquals("Time's up! Your final score is: 1", resultLabel.getText());
    }

    /**
     * Test: Score three points (combination of correct and incorrect clicks).
     */
    @Test
    public void testScoreThreePoints() throws InterruptedException {
        // Pause for a while to simulate the game running
        Thread.sleep(500); // Pause for 0.5 seconds

        // First correct click
        int x1 = game.getDisplayedSquareX();
        int y1 = game.getDisplayedSquareY();
        clickOn(getButtonAt(x1, y1));

        // Wait for a new square to appear
        Thread.sleep(500); // Wait for the game to update the square

        // Second  click
        int x2 = game.getDisplayedSquareX();
        int y2 = game.getDisplayedSquareY();
        clickOn(getButtonAt(x2 , y2));

        // Wait for a new square to appear
        Thread.sleep(500); // Wait for the game to update the square

        // Third correct click
        int x3 = game.getDisplayedSquareX();
        int y3 = game.getDisplayedSquareY();
        clickOn(getButtonAt(x3, y3));

        // Wait for the game duration (8.5 seconds)
        Thread.sleep(8500);

        // Click one more time (after time is up)
        clickOn(".button");

        // Check the label to ensure it shows the correct score
        Label resultLabel = lookup("#resultBox").query();
        assertEquals("Time's up! Your final score is: 3", resultLabel.getText());
    }

    /**
     * Helper method to get a button by its coordinates.
     */
    private Button getButtonAt(int x, int y) {
        // Calculate the index of the button to be clicked
        int index = y * 4 + x;

        // Retrieve the button from the buttons list using the getter method
        return game.getButtons().get(index);  // Use game.getButtons() instead of game.buttons
    }


}
