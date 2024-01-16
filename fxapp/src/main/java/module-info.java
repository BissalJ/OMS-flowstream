module com.example.fxapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens com.example.fxapp to javafx.fxml;
    exports com.example.fxapp;
}