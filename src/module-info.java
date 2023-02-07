module MineSweeper {
    requires javafx.controls;

    exports gui;
    opens gui to javafx.controls;
    exports core;
}