package gui;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import core.Setting;

public class SettingView {

    private static TextField xSizeText, ySizeText, mineText;

    public static Pane getPane() {
        Pane settingPane = new Pane();
        Label xSizeLabel = new Label();
        Label ySizeLabel = new Label();
        Label mineLabel = new Label();
        xSizeText = new TextField();
        ySizeText = new TextField();
        mineText = new TextField();

        settingPane.getChildren().addAll(xSizeLabel, ySizeLabel, mineLabel);
        settingPane.getChildren().addAll(xSizeText, ySizeText, mineText);

        xSizeLabel.setText("Width:");
        xSizeLabel.setLayoutX(10);
        xSizeLabel.setLayoutY(10);
        xSizeText.setText(String.valueOf(Setting.getXSize()));
        xSizeText.setLayoutX(60);
        xSizeText.setLayoutY(5);
        xSizeText.setPrefSize(40.0, 10.0);

        ySizeLabel.setText("Height:");
        ySizeLabel.setLayoutX(10);
        ySizeLabel.setLayoutY(Setting.getButtonSize() + 5);
        ySizeText.setText(String.valueOf(Setting.getYSize()));
        ySizeText.setLayoutX(60);
        ySizeText.setLayoutY(Setting.getButtonSize() + 5);
        ySizeText.setPrefSize(40.0, 10.0);

        mineLabel.setText("#Mines:");
        mineLabel.setLayoutX(10);
        mineLabel.setLayoutY((2 * Setting.getButtonSize()) + 5);
        mineText.setText(String.valueOf(Setting.getNumberOfMines()));
        mineText.setLayoutX(60);
        mineText.setLayoutY((2 * Setting.getButtonSize()) + 5);
        mineText.setPrefSize(40.0, 10.0);

        return settingPane;
    }

    public static void saveSettings() {
        try {
            Setting.setXSize(
                    Integer.parseInt(xSizeText.getText()));

            Setting.setYSize(
                    Integer.parseInt(ySizeText.getText()));

            Setting.setNumberOfMines(
                    Integer.parseInt(mineText.getText()));

        } catch (NumberFormatException e) {
            String message = "One or more of the changed settings are not a number";
            System.out.println(message); //todo migrate to a logger
        }
    }
}
