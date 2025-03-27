package uk.ac.swansea.cs135.labs.lab8;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

/**
 * JavaFX App
 */
public class Game extends Application {

    public static final int GRID_SIZE = 4;
    public static final String BUTTON_STYLE_RED = "-fx-background-color: #ff0000;";
    // Duration of game in milliseconds
    private static final int GAME_DURATION = 8000;
    // Constants used to layout GUI
    private static int PADDING = 20;
    private static final int BUTTON_MIN_SIZE = 50;

    // Used to allow the game to start when launching normally
    private static boolean playGameAutomatically = false;

    // Data structure of hold buttons of the GUI
    private ArrayList<Button> buttons = new ArrayList<Button>();
    private Label resultBox;

    private final Random rand = new Random();
    // The time the game was started
    private long startTime;
    private int result = 0;
    // The coordinates of the square that is displayed
    private int displaySquareX;
    private int displaySquareY;
    // The index of the square that was last clicked
    private int clickedSquare = -1;

    /**
     * JavaFX method that is called to start the JavaFX program.
     *
     * @param stage The stage to display the GUI in.
     */
    @Override
    public void start(Stage stage) {
        // Create root display box
        Parent root = createSceneGraph();

        // Set Stage
        stage.setTitle("Reaction Game");
        stage.setScene(new Scene(root));
        stage.show();

        // Record the current system time
        startTime = System.currentTimeMillis();

        if (playGameAutomatically) {
            startGame();
        }
    }
    public ArrayList<Button> getButtons() {
        return buttons;
    }

    /**
     * Create the scene graph for the game.
     *
     * @return The scene graph's root node.
     */
    private Parent createSceneGraph() {
        BorderPane root = new BorderPane();

        // Add grid of buttons to root pane
        GridPane grid = createGridOfButtons();
        grid.setPadding(new Insets(PADDING));
        grid.setAlignment(Pos.CENTER);
        root.setCenter(grid);

        // Add result label to root pane
        resultBox = new Label();
        resultBox.setId("resultBox");
        resultBox.setPadding(new Insets(PADDING));
        resultBox.setMaxWidth(Integer.MAX_VALUE);
        resultBox.setAlignment(Pos.CENTER);
        root.setBottom(resultBox);

        return root;
    }


    /**
     * Create the grid of buttons.
     *
     * @return The grid's root node.
     */
    private GridPane createGridOfButtons() {
        GridPane grid = new GridPane();

        // Allow each row in the grid to grow in the vertical direction as needed
        for (int row = 0; row < GRID_SIZE; row++) {
            RowConstraints rc = new RowConstraints();
            rc.setFillHeight(true);
            rc.setVgrow(Priority.ALWAYS);
            grid.getRowConstraints().add(rc);
        }

        // Allow each row in the grid to grow in the horizontal direction as needed
        for (int col = 0; col < GRID_SIZE; col++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setFillWidth(true);
            cc.setHgrow(Priority.ALWAYS);
            grid.getColumnConstraints().add(cc);
        }

        // Fill the grid with buttons
        for (int i = 0; i < GRID_SIZE * GRID_SIZE; i++) {
            Button button = createButton(i);
            grid.add(button, i % GRID_SIZE, i / GRID_SIZE);
            buttons.add(button);
        }

        return grid;
    }

    /**
     * Create a button
     *
     * @param buttonNumber The number of the button to create.
     * @return The button.
     */
    private Button createButton(int buttonNumber) {
        Button button = new Button();
        button.setId("button" + buttonNumber);

        button.setMinHeight(BUTTON_MIN_SIZE);
        button.setMaxHeight(Integer.MAX_VALUE);
        button.setMinWidth(BUTTON_MIN_SIZE);
        button.setMaxWidth(Integer.MAX_VALUE);

        button.setOnAction(e -> {
            onButtonClick(button);
        });

        return button;
    }

    /**
     * This is called automatically when a button is clicked.
     *
     * @param button The button that was clicked.
     */
    public void onButtonClick(Button button) {
        long currentTime = System.currentTimeMillis();
        long timeButtonClickedSinceStart = currentTime - startTime;

        if (timeButtonClickedSinceStart < GAME_DURATION) {
            // The button was clicked within the time limit

            // Each button's ID is "buttonX". We extract the X.
            clickedSquare = Integer.parseInt(button.getId().substring(6));
            displayClickStatus();

            // Make a new square red
            resetSquares();
            int xRandom = rand.nextInt(GRID_SIZE);
            int yRandom = rand.nextInt(GRID_SIZE);
            displaySquare(xRandom, yRandom);
        } else {
            // Clicked outside of time limit

            displayResult(); // Display result when time is up
            stopGame(); // Stop the game
        }
    }


    /**
     * Start the game.
     */
    public void startGame() {
        // Reset the score at the start of the game
        result = 0;

        // Make a random square red
        int xRandom = rand.nextInt(GRID_SIZE);
        int yRandom = rand.nextInt(GRID_SIZE);
        displaySquare(xRandom, yRandom);
    }

    /**
     * Reset all squares so that none of them are red.
     */
    public void resetSquares() {
        for (Button b : buttons) {
            // Set the style to empty - so the button displays normally
            b.setStyle(null);
        }
    }

    /**
     * Stop the game.
     */
    public void stopGame() {
        // Disable all squares and make sure they are not red
        resetSquares();
        for (Button b : buttons) {
            b.setDisable(true);  // Disable all buttons so no more clicks can happen
        }
    }


    /**
     * Turns a square red at a specified coordinate.
     *
     * @param x The x coordinate of the square (counting from 0 at the left).
     * @param y The y coordinate of the square (counting from 0 at the top).
     */
    public void displaySquare(int x, int y) {
        // Ensure coordinates are within bounds
        if (x < 0 || x >= GRID_SIZE || y < 0 || y >= GRID_SIZE) {
            throw new IllegalArgumentException("Coordinates out of bounds");
        }

        // Convert (x, y) to button index
        int index = y * GRID_SIZE + x;

        // Reset all squares to remove previous red highlight
        resetSquares();

        // Set the new square to red
        buttons.get(index).setStyle(BUTTON_STYLE_RED);

        // Store the displayed coordinates
        displaySquareX = x;
        displaySquareY = y;
    }


    /**
     * Get the x coordinate of the last square clicked.
     *
     * @return The x coordinate of the square (counting from 0 at the top).
     */
    public int getLastClickedSquareX() {
        // To be implemented in Lab 8
        return clickedSquare % GRID_SIZE;
    }

    /**
     * Get the y coordinate of the last square clicked.
     *
     * @return The y coordinate of the square (counting from 0 at the top).
     */
    public int getLastClickedSquareY() {
        // To be implemented in Lab 8
        return clickedSquare / GRID_SIZE;
    }

    /**
     * Returns the x coordinate of the square that is red. Grid coordinates start from 0.
     *
     * @return the x coordinate of the square that is red. Returns -1 if no square is red.
     */
    public int getDisplayedSquareX() {
        return displaySquareX;
    }

    /**
     * Returns the y coordinate of the square that is red. Grid coordinates start from 0.
     *
     * @return the y coordinate of the square that is red. Returns -1 if no square is red.
     */
    public int getDisplayedSquareY() {
        return displaySquareY;
    }

    /**
     * Display if the user clicked the correct square.
     */
    private void displayClickStatus() {
        // Get the coordinates of the last clicked square
        int clickedX = getLastClickedSquareX();
        int clickedY = getLastClickedSquareY();

        // Check if the clicked square matches the displayed square
        if (clickedX == displaySquareX && clickedY == displaySquareY) {
            // Correct click
            result++; // Increment score
            resultBox.setText("Correct! Current score: " + result);
        } else {
            // Wrong click
            resultBox.setText("You lost! Final score: " + result);
            stopGame(); // Stop the game immediately after a wrong click
        }
    }


    /**
     * Display the win message.
     */
    private void displayResult() {
        // Display the result (score) when time is up
        resultBox.setText("Time's up! Your final score is: " + result);
    }


    /**
     * Main method that starts the JavaFX application.
     */
    public static void main(String[] args) {
        playGameAutomatically = true;
        launch();
    }


}