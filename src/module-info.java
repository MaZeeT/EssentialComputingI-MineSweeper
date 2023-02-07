module MineSweeper {
    requires javafx.controls;

    opens sample to javafx.controls;
    exports sample;
    exports gui;
    opens gui to javafx.controls;
    exports logic;
    opens logic to javafx.controls;
    exports model;
    opens model to javafx.controls;
}