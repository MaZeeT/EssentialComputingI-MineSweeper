package sample;

import javafx.scene.control.Button;

// an extended class to give buttons some behavior wanted to make minefields
public class FieldButton extends Button {

    private boolean explored = false;
    private boolean isMine = false;
    private int dangerLevel;
    private boolean flagged = false;


    // manage the text and color of a field
    public void drawClickedButton() {

        if (!explored && !flagged) {
            setText("");
            setStyle("");
        }

        if (flagged) {
            setText("F");
            setStyle("-fx-background-color: #cd9ea2");
        }

        if (explored && isMine) {
            setText("x");
            setStyle("-fx-background-color: #6d6d6d");
        }

// colours for the fields depending on dangerLevel
        if (explored && !isMine) {
            setText(String.valueOf(dangerLevel));

            if (dangerLevel <= 8) {
                setStyle("-fx-background-color: rgba(255,84,0,0.76)");
            }
            if (dangerLevel <= 6) {
                setStyle("-fx-background-color: rgba(255,165,0,0.82)");
            }
            if (dangerLevel <= 4) {
                setStyle("-fx-background-color: rgba(173,255,47,0.7)");
            }
            if (dangerLevel <= 2) {
                setStyle("-fx-background-color: rgba(0,0,255,0.34)");
            }
            if (dangerLevel == 0) {
                setStyle("-fx-background-color: #d1d1d1");
            }
        }
    }


    // setting the GUI-position of the field
    public void setPosition(int xCoor, int yCoor, double size) {
        setLayoutX(xCoor * size);
        setLayoutY(yCoor * size);
        setPrefSize(size, size);
    }

    // setter for explored with a check for flagged
    public void setExplored(boolean _explored) {
        if (!flagged) {
            explored = _explored;
        }
    }

    // getters and setters
    public void setMine(Boolean _isMine) {
        isMine = _isMine;
    }

    public boolean getMine() {
        return isMine;
    }

    public boolean getExplored() {
        return explored;
    }

    public void setDangerLevel(int _dangerLevel) {
        dangerLevel = _dangerLevel;
    }

    public int getDangerLevel() {
        return dangerLevel;
    }

    public void setFlagged(boolean _flagged) {
        flagged = _flagged;
    }

    public boolean getFlagged() {
        return flagged;
    }

} // end class

