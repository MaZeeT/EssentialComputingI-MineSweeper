package gui;

import javafx.scene.control.Button;
import core.Field;
import core.Plot;

public class FieldButton extends Button {

    private final Field field;
    private final Plot plot;

    public FieldButton(Field field, Plot plot) {
        this.field = field;
        this.plot = plot;
    }

    public void leftClick() {
        if (Field.isNotFlagged(plot)) {
            field.explore(plot);
        }
    }

    public void middleClick() {
        if (plot.explored) {
            field.exploreGrid(plot);
        }
    }

    public void rightClick() {
        field.toggleFlag(plot);
    }

    // manage the text and color of a field
    public void drawClickedButton() {
        setLayoutOfNotExploredAndNotFlaggedPlot();
        setLayoutOfFlaggedPlot();
        setLayoutOfExploredMine();
        setLayoutOfExploredPlot();
    }

    private void setLayoutOfExploredPlot() {
        if (plot.explored && !plot.isMine) {
            setText(String.valueOf(plot.dangerLevel));
            setDangerLevelColor();
        }
    }

    private void setDangerLevelColor() {
        if (plot.dangerLevel <= 8) setStyle("-fx-background-color: rgba(255,84,0,0.76)");
        if (plot.dangerLevel <= 6) setStyle("-fx-background-color: rgba(255,165,0,0.82)");
        if (plot.dangerLevel <= 4) setStyle("-fx-background-color: rgba(173,255,47,0.7)");
        if (plot.dangerLevel <= 2) setStyle("-fx-background-color: rgba(0,0,255,0.34)");
        if (plot.dangerLevel == 0) setStyle("-fx-background-color: #d1d1d1");
    }

    private void setLayoutOfExploredMine() {
        if (plot.explored && plot.isMine) {
            setText("x");
            setStyle("-fx-background-color: #6d6d6d");
        }
    }

    private void setLayoutOfFlaggedPlot() {
        if (plot.flagged) {
            setText("F");
            setStyle("-fx-background-color: #cd9ea2");
        }
    }

    private void setLayoutOfNotExploredAndNotFlaggedPlot() {
        if (!plot.explored && !plot.flagged) {
            setText("");
            setStyle("");
        }
    }

    // setting the GUI-position of the field
    public void setPosition(Plot plot, double size) {
        setLayoutX(plot.x * size);
        setLayoutY(plot.y * size);
        setPrefSize(size, size);
    }

}

