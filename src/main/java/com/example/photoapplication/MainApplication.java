package com.example.photoapplication;

import com.example.photoapplication.controller.Login;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import com.example.photoapplication.data.PhotoDataBase;


public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        PhotoDataBase photoDataBase = PhotoDataBase.readFromAFile();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login.fxml"));
        fxmlLoader.setControllerFactory(clazz -> new Login(photoDataBase)); // Pass photoDataBase to controller
        Scene scene = new Scene(fxmlLoader.load(), 900, 700);
        stage.setTitle("Photo Application");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}