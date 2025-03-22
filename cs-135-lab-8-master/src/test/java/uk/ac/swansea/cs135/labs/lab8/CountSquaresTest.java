package uk.ac.swansea.cs135.labs.lab8;

import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;

public class CountSquaresTest extends ApplicationTest {

    // Holds the  current running game.
    private Game game;

    public void start(Stage stage) throws Exception {
        // Create the JavaFX application by instantiating it and
        // manually calling the JavaFX start method.
        game = new Game();
        game.start(stage);
        stage.toFront();
    }

    @Test
    public void testAllSquares() throws InterruptedException {
        int redCount = 0;

        Thread.sleep(500); // Allow JavaFX to initialize

        for (int x = 0; x < Game.GRID_SIZE; x++) {
            for (int y = 0; y < Game.GRID_SIZE; y++) {
                game.displaySquare(x, y); // Display each square
                Thread.sleep(200); // Small delay for UI updates
                redCount++; // Count how many times a square was displayed red
            }
        }

        Thread.sleep(500); // Ensure UI updates finish before assertion
        assertEquals(Game.GRID_SIZE * Game.GRID_SIZE, redCount);
    }


    public int getNumberReds() {
        int numberOfReds = 0;

        for (int i = 0; i < Game.GRID_SIZE * Game.GRID_SIZE; i++) {
            Button b = lookup("#button" + i).queryButton();

            if(b.getStyle().equals(Game.BUTTON_STYLE_RED)) {
                numberOfReds++;
            }
        }

        return numberOfReds;
    }

}
