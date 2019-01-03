package sample;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Random;

// a class to run the minesweeper game
public class Game {

    private int xSize, ySize, numberOfMines;
    private int mineCount, flagCount;

    private boolean firstClick, won, dead;

    private Pane minefieldPane;
    private Label mineLabel = new Label();
    private FieldButton[][] fields;



    //// Below are 2 methods that fetch settings and start a new game ////
    // getting settings into class-local variables
    private void grabSettings() {
        xSize = Setting.getxSize();
        ySize = Setting.getySize();
        numberOfMines = Setting.getNumberOfMines();
    }

    // call the different method for running the game and set the text of the mine-counter label
    public void newGame() {
        grabSettings();
        minefieldPane();
        mineCount = 0;
        firstClick = true;
        won = false;
        dead = false;
        fields = new FieldButton[xSize][ySize];
        generateFields();
        toMineOrNotToMine();
        calDangerLevel();
        mineLabel();
    }



    //// Below are 3 core methods to the game, that handle the board mines and danger levels ////
    // generate an array of fields and sets the properties of the fields
    private void generateFields() {
        for (int j = 0; j < ySize; j++) {
            for (int i = 0; i < xSize; i++) {
                fields[i][j] = new FieldButton();
                fields[i][j].setExplored(false);
                fields[i][j].setPosition(i, j, Setting.buttonSize);
                minefieldPane.getChildren().add(fields[i][j]);
                clickButton(i, j);
            }
        }
    }

    // determent if the field gets a mine
    private void toMineOrNotToMine() {
        Random rand = new Random();

        while (mineCount < numberOfMines) {
            int xRand = rand.nextInt(xSize);
            int yRand = rand.nextInt(ySize);

            // check if the field have a mine
            if (!fields[xRand][yRand].getMine() && !fields[xRand][yRand].getExplored()) {
                fields[xRand][yRand].setMine(true);
                mineCount++;
            }
        }
    }

    // calculate how many mines there are around the clicked field
    private void calDangerLevel() {
        // Loop across the array
        for (int j = 0; j < ySize; j++) {
            for (int i = 0; i < xSize; i++) {
                int dangerLevel = 0;

                // Loop across + - 1 from a field
                for (int y = -1; y <= 1; y++) {
                    for (int x = -1; x <= 1; x++) {

                        if (0 <= y + j && y + j < ySize && 0<= x + i && x + i < xSize) {
                            if (fields[x + i][y + j].getMine()) {
                                dangerLevel++;
                            }
                        }
                    }
                }
                fields[i][j].setDangerLevel(dangerLevel);
            }
        }
    }



    //// Below are 2 methods for winning and losing conditions ////
    // check for winning conditions
    private void won() {
        int exploredFields = (xSize * ySize) - numberOfMines;
        for (int j = 0; j < ySize; j++) {
            for (int i = 0; i < xSize; i++) {

                if (!fields[i][j].getMine() && fields[i][j].getExplored()) {
                    exploredFields--;
                }

                if (exploredFields == 0) {
                    won = true;
                }
            }
        }
    }

    // check for death conditions
    private void death() {
        for (int j = 0; j < ySize; j++) {
            for (int i = 0; i < xSize; i++) {
                if (fields[i][j].getMine() && fields[i][j].getExplored()) {
                    dead = true;
                }
            }
        }
    }



    //// Below are 3 methods that manage the count of flagged and mined fields and shows it in the mineLabel ////
    // count the number of mines on the board
    private void countMines() {
        mineCount = 0;
        for (int j = 0; j < ySize; j++) {
            for (int i = 0; i < xSize; i++) {
                if (fields[i][j].getMine()) {
                    mineCount++;
                }
            }
        }
    }

    // count flags on the board
    private void countFlags() {
        flagCount = 0;
        for (int j = 0; j < ySize; j++) {
            for (int i = 0; i < xSize; i++) {
                if (fields[i][j].getFlagged()) {
                    flagCount++;
                }
            }
        }
    }

    // manage the mine counter label
    private void mineLabel() {
        countMines();
        countFlags();
        int mineLeft = mineCount - flagCount;
        if (mineLeft >= 0 && !dead && !won) {
            mineLabel.setText("Mines left on field: " + mineLeft);
        }
        if (dead && !won) {
            mineLabel.setText("You are DEAD, so so dead  (x . X)  please try again");
        }
        if (won && !dead) {
            mineLabel.setText("Good job on clearing the field. On to your next assigned field ");
        }
    }



    //// Below are 5 methods that manage the logic when clicking a field ////
    // 3 different functions depending on which button that have been clicked
    private void clickButton(int xCoor, int yCoor) {
        fields[xCoor][yCoor].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                // right click
                if (event.getButton() == MouseButton.SECONDARY) {
                    flagField(xCoor, yCoor);
                }

                // middle click
                if (event.getButton() == MouseButton.MIDDLE && fields[xCoor][yCoor].getExplored()) {
                    exploreGrid(xCoor, yCoor);
                }

                // left click
                if (event.getButton() == MouseButton.PRIMARY && !fields[xCoor][yCoor].getFlagged()) {
                    explore(xCoor, yCoor);
                }

                won();
                death();
                mineLabel();

                firstClick = false;
            }
        });
    }

    // replace mine if the first click is a mine
    private void firstClick(int xCoor, int yCoor) {
        fields[xCoor][yCoor].setMine(false);
        mineCount--;
        toMineOrNotToMine();
        calDangerLevel();
    }

    // set a field as explored and do some checks to see if it can be explored or it shall check nearby fields also
    private void explore(int xCoor, int yCoor) {

        if (!fields[xCoor][yCoor].getFlagged()) {
            fields[xCoor][yCoor].setExplored(true);


            if (firstClick && fields[xCoor][yCoor].getMine()) {
                firstClick(xCoor, yCoor);
            }

            // this make a recursive behaviour with explore() and exploreGrid() if there are no nearby mines
            if (fields[xCoor][yCoor].getDangerLevel() == 0) {
                exploreGrid(xCoor, yCoor);
            }
        }
        fields[xCoor][yCoor].drawClickedButton();
    }

    // check and click a 3x3 grid
    private void exploreGrid(int xCoor, int yCoor) {
        for (int j = -1; j <= 1; j++) {
            for (int i = -1; i <= 1; i++) {

                // check to stay inside of array
                if (0 <= yCoor + j && yCoor + j < ySize && 0 <= xCoor + i && xCoor + i < xSize) {

                    // check if field is not explored and flagged and run explore()
                    if (!fields[xCoor + i][yCoor + j].getExplored() && !fields[xCoor + i][yCoor + j].getFlagged()) {
                        explore(xCoor + i, yCoor + j);
                    }
                }
            }
        }
    }

    // flag and unflag field
    private void flagField(int xCoor, int yCoor) {
        if (!fields[xCoor][yCoor].getExplored()) {
            if (fields[xCoor][yCoor].getFlagged()) {
                fields[xCoor][yCoor].setFlagged(false);
            } else {
                fields[xCoor][yCoor].setFlagged(true);
            }
        }
        fields[xCoor][yCoor].drawClickedButton();
    }



    //// Below are 2 methods that take care of GUI elements ////
    // make a new pane of a game
    private void minefieldPane() {
        minefieldPane = new Pane();
    }

    // passes the pane
    public Pane getMinefieldPane() {
        newGame();
        VBox minefieldBox = new VBox();
        minefieldBox.getChildren().addAll(mineLabel, minefieldPane);
        return minefieldBox;
    }

} // end class