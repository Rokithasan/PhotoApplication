module com.example.photoapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;


    opens com.example.photoapplication to javafx.fxml;
    exports com.example.photoapplication;
    exports com.example.photoapplication.controller;
    opens com.example.photoapplication.controller to javafx.fxml;
}