package sample;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


// class to define some settings
public class Setting {

    private static int xSize = 10;
    private static int ySize = 10;
    private static int numberOfMines = 10;
    public static double buttonSize = 38;

    private static TextField xSizeText, ySizeText, mineText;
    private static Label xSizeLabel, ySizeLabel, mineLabel;
    private static Pane settingPane;


    // read input and get variables to the value
    public static void saveSettings() {
        if(Integer.parseInt(xSizeText.getText()) > 0){
            xSize = Integer.parseInt(xSizeText.getText());
        }

        if(Integer.parseInt(ySizeText.getText()) > 0){
            ySize = Integer.parseInt(ySizeText.getText());
        }

        if(Integer.parseInt(mineText.getText()) >= 0){
            numberOfMines = Integer.parseInt(mineText.getText());
        }

    }

    // build the pane for settings
    private static void settingPane() {
        settingPane = new Pane();
        xSizeLabel = new Label();
        ySizeLabel = new Label();
        mineLabel = new Label();
        xSizeText = new TextField();
        ySizeText = new TextField();
        mineText = new TextField();

        settingPane.getChildren().addAll(xSizeLabel, ySizeLabel, mineLabel);
        settingPane.getChildren().addAll(xSizeText, ySizeText, mineText);

        xSizeLabel.setText("Width:");
        xSizeLabel.setLayoutX(10);
        xSizeLabel.setLayoutY(10);
        xSizeText.setText(String.valueOf(xSize));
        xSizeText.setLayoutX(60);
        xSizeText.setLayoutY(5);
        xSizeText.setPrefSize(40.0, 10.0);

        ySizeLabel.setText("Height:");
        ySizeLabel.setLayoutX(10);
        ySizeLabel.setLayoutY(buttonSize + 5);
        ySizeText.setText(String.valueOf(ySize));
        ySizeText.setLayoutX(60);
        ySizeText.setLayoutY(buttonSize + 5);
        ySizeText.setPrefSize(40.0, 10.0);

        mineLabel.setText("#Mines:");
        mineLabel.setLayoutX(10);
        mineLabel.setLayoutY((2 * buttonSize) + 5);
        mineText.setText(String.valueOf(numberOfMines));
        mineText.setLayoutX(60);
        mineText.setLayoutY((2 * buttonSize) + 5);
        mineText.setPrefSize(40.0, 10.0);
    }

    // grab the constructed pane
    public static Pane getSettingPane() {
        settingPane();
        return settingPane;
    }

    // getters
    public static int getxSize() {
        return xSize;
    }

    public static int getySize() {
        return ySize;
    }

    public static int getNumberOfMines() {
        return numberOfMines;
    }

} // end class