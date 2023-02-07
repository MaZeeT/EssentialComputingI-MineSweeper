package gui;

import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

public class WelcomeView {
    public static Pane getPane() {
        String message = """
                Welcome to Minesweeper.
                Made by Mads E.
                
                Left click to explore a field.
                Middle click to explore nearby fields.
                Right click to flag a field as a mine.
                """;

        TextArea text = new TextArea(message);
        return new Pane(text);
    }
}
