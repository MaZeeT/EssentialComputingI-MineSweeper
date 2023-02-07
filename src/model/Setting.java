package model;

public class Setting {

    private static int xSize = 10;
    private static int ySize = 10;
    private static int numberOfMines = 10;
    private static final double buttonSize = 38;

    public static int getXSize() {
        return xSize;
    }

    public static void setXSize(int number) {
        if (isNumberPositive(number)) {
            xSize = number;
        }
    }

    public static int getYSize() {
        return ySize;
    }

    public static void setYSize(int number) {
        if (isNumberPositive(number)) {
            ySize = number;
        }
    }

    public static int getNumberOfMines() {
        return numberOfMines;
    }

    public static void setNumberOfMines(int number) {
        if (isNumberPositive(number)) {
            numberOfMines = number;
        }
    }

    public static double getButtonSize(){
        return buttonSize;
    }

    private static boolean isNumberPositive(int number) {
        return number > 0;
    }
}