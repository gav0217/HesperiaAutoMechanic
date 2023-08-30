module com.example.HesperiaAutoMechanic {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires junit;


    opens com.example.hesperiaautomechanic to javafx.fxml;
    exports com.example.hesperiaautomechanic;
    exports model;
    exports controller;
    opens controller to javafx.fxml;
    exports db;
    opens db to javafx.fxml;
}