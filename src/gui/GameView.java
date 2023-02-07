package gui;

import javafx.scene.layout.Pane;
import logic.Game;

public class GameView {
    Game minesweeper;
    public GameView() {
        minesweeper = new Game();
    }

    public  Pane getPane() {
        return minesweeper.getMinefieldPane();
    }
}
