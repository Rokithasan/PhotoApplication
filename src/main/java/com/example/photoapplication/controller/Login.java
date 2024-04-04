package com.example.photoapplication.controller;

import com.example.photoapplication.data.PhotoDataBase;
import com.example.photoapplication.utill.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    private Button btnExit;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField tfUsername;

    private PhotoDataBase photoDataBase;

    public Login() {
    }

    public Login(PhotoDataBase photoDataBase) {
        this.photoDataBase = photoDataBase;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfUsername.requestFocus();
    }

    @FXML
    void actionOnExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void actionOnLogin(ActionEvent event) throws IOException {

        if (tfUsername.getText().isEmpty()) {
            Utils.showError("Login", "Please enter username!");
        } else {
            boolean userFound = false;
            if (tfUsername.getText().equalsIgnoreCase("admin")) {
                userFound = true;
                Parent root = FXMLLoader.load(getClass().getResource("/com/example/photoapplication/admin-dashbord.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                PhotoDataBase userData = PhotoDataBase.getInstance();
                if (userData.containsUser(tfUsername.getText().trim())) {
                    try {
                        userData.setCurrentSessionUser(tfUsername.getText().trim());
                        userFound = true;
                        Parent root = FXMLLoader.load(getClass().getResource("/com/example/photoapplication/album-view.fxml"));
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
