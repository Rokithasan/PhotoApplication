
/**
 * The Login class represents the controller for the login screen in a photo application.
 * This class controls the functionality related to user authentication.
 * It interacts with the PhotoDataBase to check user credentials.
 * The Login class implements the Initializable interface to initialize the UI components
 * defined in the corresponding FXML file.
 */

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

    /**
     * Default constructor.
     */
    public Login() {
    }

    /**
     * Constructor with PhotoDataBase parameter.
     *
     * @param photoDataBase The instance of PhotoDataBase.
     */
    public Login(PhotoDataBase photoDataBase) {
        this.photoDataBase = photoDataBase;
    }

    /**
     * Initializes the controller after its root element has been completely processed.
     * This method sets focus to the username text field.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfUsername.requestFocus();
    }

    /**
     * Handles the action when the exit button is clicked.
     * It closes the application.
     *
     * @param event The event representing the action on the exit button.
     */
    @FXML
    void actionOnExit(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Handles the action when the login button is clicked.
     * It checks the entered username against the database and navigates to the corresponding dashboard.
     *
     * @param event The event representing the action on the login button.
     * @throws IOException Signals that an I/O exception has occurred.
     */
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
