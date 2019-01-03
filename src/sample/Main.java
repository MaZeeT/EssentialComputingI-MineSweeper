package sample;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage minesweeperStage) throws Exception {
        AppController appC = new AppController();
        minesweeperStage.setTitle("MineSweeper");
        minesweeperStage.setScene(new Scene(appC.border, 382, 500));
        appC.getBorder();
        minesweeperStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

} // end class
