/**
 * The AdminDashboard class represents the controller for the administrator dashboard
 * in a photo application.
 * This class controls the functionality related to user management, such as creating
 * and deleting users.
 * It interacts with the PhotoDataBase to manage user data.
 * The AdminDashboard class implements the Initializable interface to initialize the UI
 * components defined in the corresponding FXML file.
 */

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
import java.util.Objects;
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


    /**
     * Initializes the controller after its root element has been completely processed.
     * This method sets up the list of users and disables delete button if no users are available.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listUsers.setItems(users);
        users.addAll(PhotoDataBase.getInstance().getUsernames());
        if (users.isEmpty()) {
            btnDelete.setDisable(true);
        }
    }

    /**
     * Handles the action when the create button is clicked.
     * It creates a new user if the username is valid and not already taken.
     * Otherwise, it shows an error message.
     *
     * @param event The event representing the action on the create button.
     */
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

    /**
     * Handles the action when the delete button is clicked.
     * It deletes the selected user from the database and the UI.
     * Shows a confirmation dialog before deleting the user.
     *
     * @param event The event representing the action on the delete button.
     */
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

    /**
     * Handles the action when the logout button is clicked.
     * It navigates the user back to the login screen.
     *
     * @param event The event representing the action on the logout button.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    void actionOnLogout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/photoapplication/login.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
