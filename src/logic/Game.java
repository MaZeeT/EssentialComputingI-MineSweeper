package logic;

import model.Field;
import model.Plot;
import model.Setting;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sample.FieldButton;

import java.util.ArrayList;
import java.util.List;


public class Game {

    private int xSize, ySize, numberOfMines;
    private int mineCount, flagCount;

    public boolean firstClick, won, dead;

    private Pane minefieldPane;
    private List<FieldButton> minefieldButtons;
    private Label mineLabel = new Label();
    private Field field;


    //// Below are 2 methods that fetch settings and start a new game ////
    // getting settings into class-local variables
    private void grabSettings() {
        xSize = Setting.getXSize();
        ySize = Setting.getYSize();
        numberOfMines = Setting.getNumberOfMines();
    }

    // call the different method for running the game and set the text of the mine-counter label
    public void newGame() {
        grabSettings();
        field = new Field(xSize, ySize, numberOfMines);
        minefieldPane = new Pane();
        minefieldButtons = new ArrayList<>();
        generateButtons();
        updateMineLabel();
    }

    //// Below are 3 core methods to the game, that handle the board mines and danger levels ////
    // generate an array of fields and sets the properties of the fields
    private void generateButtons() {
        for (Plot plot:field.getPlots()) {
            FieldButton button = new FieldButton(field, plot, this);

            button.setPosition(plot.x, plot.y, Setting.getButtonSize());

            button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                switch (event.getButton()) {
                    case PRIMARY -> button.leftClick();
                    case MIDDLE -> button.middleClick();
                    case SECONDARY -> button.rightClick();
                }

                won = field.isGameWon();
                dead = field.isPlayerDead();
                updateMineLabel();
                renderField();

                firstClick = false;
            });

            minefieldButtons.add(button);
        }
        minefieldPane.getChildren().addAll(minefieldButtons);
    }

    public void renderField(){
        for (FieldButton button: minefieldButtons) {
            button.drawClickedButton();
        }
    }

    // manage the mine counter label
    public void updateMineLabel() {
        mineCount = field.countMines();
        flagCount = field.countFlags();
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

    public Pane getMinefieldPane() {
        newGame();
        VBox minefieldBox = new VBox();
        minefieldBox.getChildren().addAll(mineLabel, minefieldPane);
        return minefieldBox;
    }
}