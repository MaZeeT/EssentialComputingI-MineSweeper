package gui;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import core.Field;
import core.Plot;
import core.Setting;

import java.util.ArrayList;
import java.util.List;

public class GameView {
    public long mineCount, flagCount;
    public int xSize, ySize, numberOfMines;
    public boolean firstClick, won, dead;
    public Field field;
    public Label mineLabel = new Label();
    public Pane minefieldPane;
    public List<FieldButton> minefieldButtons;


    public Pane getPane() {
        newGame();
        VBox minefieldBox = new VBox();
        minefieldBox.getChildren().addAll(mineLabel, minefieldPane);
        return minefieldBox;
    }

    public void grabSettings() {
        xSize = Setting.getXSize();
        ySize = Setting.getYSize();
        numberOfMines = Setting.getNumberOfMines();
    }

    public void newGame() {
        grabSettings();
        field = new Field(xSize, ySize, numberOfMines);
        minefieldPane = new Pane();
        minefieldButtons = new ArrayList<>();
        generateButtons();
        updateMineLabel();
    }

    private void generateButtons() {
        for (Plot plot : field.getPlots()) {
            FieldButton button = new FieldButton(field, plot);

            button.setPosition(plot, Setting.getButtonSize());

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

    // manage the mine counter label
    public void updateMineLabel() {
        mineCount = field.countMines();
        flagCount = field.countFlags();
        long mineLeft = mineCount - flagCount;
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

    public void renderField() {
        for (FieldButton button : minefieldButtons) {
            button.drawClickedButton();
        }
    }
}
