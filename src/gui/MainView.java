package gui;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainView {
    private static final GameView gameView = new GameView();
    private static final BorderPane border = new BorderPane(gameView.getPane());
    private static final Button newGame = new Button("New Game");
    private static final Button settingButton = new Button("Settings");
    private static final Button save = new Button("Save settings");
    private static final Pane spacePane = new Pane();
    private static final VBox vBoxButtons = new VBox(newGame, settingButton);
    private static final HBox hBox = new HBox(vBoxButtons, spacePane);

    private static void applyConfiguration(){
        spacePane.setPrefSize(100, 200);
        hBox.setMinHeight(100);
        hBox.setMaxHeight(100);
        border.setTop(hBox);

        newGame.setOnAction(event -> {
            border.setCenter(gameView.getPane());
            vBoxButtons.getChildren().remove(save);
        });

        settingButton.setOnAction(event -> {
            if (!vBoxButtons.getChildren().contains(save)) {
                vBoxButtons.getChildren().add(save);}

            border.setCenter(SettingView.getPane());
        });

        save.setOnAction(event -> {
            SettingView.saveSettings();
            vBoxButtons.getChildren().remove(save);
            border.setCenter(WelcomeView.getPane());
        });
    }

    public static Pane getBorderPane(){
        applyConfiguration();
        return border;
    }
}