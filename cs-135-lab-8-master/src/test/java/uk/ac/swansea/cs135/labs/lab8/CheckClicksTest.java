package uk.ac.swansea.cs135.labs.lab8;


import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;

public class CheckClicksTest extends ApplicationTest {

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
    public void testClickX0Y0() throws InterruptedException {
        testClick(0, 0, "#button0");
    }

    @Test
    public void testClickX1Y1() throws InterruptedException {
        testClick(1, 1, "#button5");
    }

    @Test
    public void testClickX2Y2() throws InterruptedException {
        testClick(2, 2, "#button10");
    }

    @Test
    public void testClickX3Y3() throws InterruptedException {
        testClick(3, 3, "#button15");
    }

    private void testClick(int x, int y, String buttonId) throws InterruptedException {
        Thread.sleep(500); // Allow UI initialization
        game.displaySquare(x, y);
        clickOn(buttonId, MouseButton.PRIMARY);
        Thread.sleep(1000);
        assertEquals(x, game.getLastClickedSquareX());
        assertEquals(y, game.getLastClickedSquareY());
    }

}
