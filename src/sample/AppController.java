package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


public class AppController {
    public BorderPane border = new BorderPane();
    private HBox hBox = new HBox();
    private VBox vBoxButtons = new VBox();
    private Pane centralPane = new Pane();
    private Pane spacePane = new Pane();

    // construct populate panes for the border Pane
    public void getBorder() {
        populateBorder();
        centerPane();
    }

    // set different elements to the borderPane
    private void populateBorder() {
        Game minesweeper = new Game();
        Button newGame = new Button("New Game");
        Button settingButton = new Button("Settings");
        Button save = new Button("Save settings");

        spacePane.setPrefSize(100, 200);
        vBoxButtons.getChildren().addAll(newGame, settingButton);
        hBox.getChildren().addAll(vBoxButtons, spacePane);
        hBox.setMinHeight(100);
        hBox.setMaxHeight(100);
        border.setTop(hBox);


        newGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                minesweeper.newGame();
                border.setCenter(minesweeper.getMinefieldPane());
                if (vBoxButtons.getChildren().contains(save)) {
                    vBoxButtons.getChildren().remove(save);
                }
            }
        });

        settingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!vBoxButtons.getChildren().contains(save)) {
                    vBoxButtons.getChildren().add(save);
                }

                border.setCenter(Setting.getSettingPane());
            }
        });

        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Setting.saveSettings();
                vBoxButtons.getChildren().remove(save);
                border.setCenter(centralPane);
            }
        });
    }

    // welcome and info message
    private void centerPane() {
        border.setCenter(centralPane);
        TextArea text = new TextArea();
        text.setText("Welcome to Minesweeper. \nMade by Mads E. \n\nLeft click to explore a field. \nMiddle click to explore nearby fields. \nRight click to flag a field as a mine.");
        centralPane.getChildren().add(text);
    }

} // end class