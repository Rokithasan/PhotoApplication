module com.example.photoapplication {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.photoapplication to javafx.fxml;
    exports com.example.photoapplication;
}