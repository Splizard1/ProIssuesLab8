package uk.ac.swansea.cs135.labs.lab8;



import javafx.scene.control.Labeled;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DisplayClickStatusTest extends ApplicationTest {
    // Holds the  current running game.
    private Game game;

    /**
     * This is called each time a test is run to start the game up.
     * You need this exact method in every test suite.
     */
    public void start(Stage stage) throws Exception {
        // Create the JavaFX application by instantiating it and
        // manually calling the JavaFX start method.
        game = new Game();
        game.start(stage);
        stage.toFront();
    }

    /**
     * Helper method to observe the displayed text in the result box.
     */
    public String observeDisplay() {
        Labeled display = lookup("#resultBox").queryLabeled();
        return display.getText();
    }

    @Test
    public void testCorrectClick() throws InterruptedException {
        Thread.sleep(500);
        // Step 1: Fix an x-y coordinate
        int correctX = 0;
        int correctY = 0;

        // Step 2: Display the square at the coordinate
        game.displaySquare(correctX, correctY);

        // Step 3: Simulate clicking the correct button
        clickOn("#button0");
        String displayedText = observeDisplay();


        assertEquals("Correct", displayedText);


        Thread.sleep(500);
    }

    @Test
    public void testIncorrectClick() throws InterruptedException {
        Thread.sleep(500);
        // Step 1: Fix an x-y coordinate
        int correctX = 1;
        int correctY = 1;

        // Step 2: Display the square at the coordinate
        game.displaySquare(correctX, correctY);

        // Step 3: Simulate clicking an incorrect button
        clickOn("#button7" );
        String displayedText = observeDisplay();


        assertEquals("Incorrect", displayedText);

        // Pause for another 0.5 seconds before finishing the test
        Thread.sleep(500);
    }
}
