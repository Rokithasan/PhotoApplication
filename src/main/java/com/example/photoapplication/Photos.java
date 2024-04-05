package com.example.photoapplication;

import com.example.photoapplication.controller.Login;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import com.example.photoapplication.data.PhotoDataBase;


public class Photos extends Application {

    /**
     * Start method of the application.
     * @param stage the stage will show gui.
     * @throws Exception error if gui not found.
     */
    @Override
    public void start(Stage stage) throws IOException {

        PhotoDataBase photoDataBase = PhotoDataBase.readFromAFile();
        FXMLLoader fxmlLoader = new FXMLLoader(Photos.class.getResource("login.fxml"));
        fxmlLoader.setControllerFactory(clazz -> new Login(photoDataBase)); // Pass photoDataBase to controller
        Scene scene = new Scene(fxmlLoader.load(), 900, 700);
        stage.setTitle("Photo Application");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method of the class.
     * @param args  passing arguments.
     */
    public static void main(String[] args) {
        launch();
    }
}