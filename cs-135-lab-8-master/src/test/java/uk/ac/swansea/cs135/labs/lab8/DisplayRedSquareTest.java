package uk.ac.swansea.cs135.labs.lab8;

import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;

public class DisplayRedSquareTest extends ApplicationTest {

    // Holds the current running game instance
    private Game game;

    @Override
    public void start(Stage stage) throws Exception {
        game = new Game();
        game.start(stage);
        stage.toFront();
    }

    /**
     * Test each weak equivalence class (X0, X1, X2, X3) × (Y0, Y1, Y2, Y3)
     * Ensures that the correct square is set to red.
     */
    @Test
    public void testDisplayRedSquare() throws InterruptedException {
        Thread.sleep(500); // Allow JavaFX to initialize

        int[][] testCases = {
                {0, 0}, {1, 1}, {2, 2}, {3, 3}, // Diagonal coverage
                {0, 3}, {3, 0}, {1, 2}, {2, 1}  // Additional cases
        };

        for (int[] testCase : testCases) {
            int x = testCase[0];
            int y = testCase[1];

            game.displaySquare(x, y);
            Thread.sleep(500); // Ensure UI updates

            assertEquals("Displayed square X-coordinate incorrect", x, game.getDisplayedSquareX());
            assertEquals("Displayed square Y-coordinate incorrect", y, game.getDisplayedSquareY());
        }
    }
}
