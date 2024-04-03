package com.example.photoapplication.controller;

import com.example.photoapplication.data.PhotoDataBase;
import com.example.photoapplication.model.User;
import com.example.photoapplication.utill.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboard implements Initializable {

    @FXML
    private Button btnCreate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnLogout;
    @FXML
    private ListView<String> listUsers;
    private final ObservableList<String> users = FXCollections.observableArrayList();

    @FXML
    private TextField tfUsername;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listUsers.setItems(users);
        users.addAll(PhotoDataBase.getInstance().getUsernames());
        if (users.isEmpty()) {
            btnDelete.setDisable(true);
        }
    }

    @FXML
    void actionOnCreate(ActionEvent event) {

        String user = tfUsername.getText();
        if (user.isEmpty()) {
            Utils.showError("Create New User", "Please Enter Username!");
        } else if (PhotoDataBase.getInstance().containsUser(user)) {
            Utils.showError("Create New User", "User is already exist!");
        } else {
            users.add(user);
            listUsers.getSelectionModel().select(user);
            PhotoDataBase.getInstance().addUser(new User(user));
            btnDelete.setDisable(false);
            tfUsername.clear();
            try {
                PhotoDataBase userData = PhotoDataBase.getInstance();
                userData.writeToAFile();
            } catch (Exception ex) {

            }
        }
    }

    @FXML
    void actionOnDelete(ActionEvent event) {

        String user = listUsers.getSelectionModel().getSelectedItem();
        if (user == null) {
            Utils.showError("Delete User", "Select a user for deleting");
        } else {
            if (Utils.getConfirmation("Delete User", "Are you sure you want to delete \"" + user + "\" User?")) {
                users.remove(user);
                PhotoDataBase.getInstance().deleteUser(user);
                if (users.isEmpty()) {
                    btnDelete.setDisable(true);
                }
                try {
                    PhotoDataBase userData = PhotoDataBase.getInstance();
                    userData.writeToAFile();
                } catch (Exception ex) {

                }
            }
        }
    }

    @FXML
    void actionOnLogout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/photoapplication/login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
