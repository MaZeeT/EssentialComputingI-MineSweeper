package sample;


import gui.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage minesweeperStage) throws Exception {
        minesweeperStage.setTitle("MineSweeper");
        minesweeperStage.setScene(new Scene(MainView.getBorderPane(), 382, 500));
        minesweeperStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}