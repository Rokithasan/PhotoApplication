package com.example.photoapplication.controller;

import com.example.photoapplication.data.PhotoDataBase;
import com.example.photoapplication.utill.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Login {

    @FXML
    private Button loginButtonId;
    @FXML
    private TextField loginNameTextFildId;
    @FXML
    private TextField passwordTextFildId;
    public void onActionLogin(ActionEvent actionEvent) throws IOException {
        if (loginNameTextFildId.getText().isEmpty()) {
            Utils.showError("Login", "Please enter username!");
        } else {
            boolean userFound = false;
            if (loginNameTextFildId.getText().equalsIgnoreCase("admin")) {
                userFound = true;
                Parent root = FXMLLoader.load(getClass().getResource("/com/example/photoapplication/admin-dashbord.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                PhotoDataBase userData = PhotoDataBase.getInstance();
                if (userData.containsUser(loginNameTextFildId.getText().trim())) {
                    try {
                        userData.setCurrentSessionUser(loginNameTextFildId.getText().trim());
                        userFound = true;
                        Parent root = FXMLLoader.load(getClass().getResource("/com/example/photoapplication/album-view.fxml"));
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (!userFound) {
                Utils.showError("Login", "Invalid username!");
            }
        }
    }
}
