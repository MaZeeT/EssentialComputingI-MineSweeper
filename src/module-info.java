module MineSweeper {
    requires javafx.controls;

    opens sample to javafx.controls;
    exports sample;
}