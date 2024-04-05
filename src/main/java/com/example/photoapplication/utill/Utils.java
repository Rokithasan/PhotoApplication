package com.example.photoapplication.utill;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Utility class for displaying alerts and confirmation dialogs.
 */
public class Utils {

    /**
     * Display an error dialog.
     *
     * @param title the title of the dialog
     * @param msg   the message to display
     */
    public static void showError(String title, String msg) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle(title);
        error.setContentText(msg);
        error.showAndWait();
    }

    /**
     * Display an information dialog.
     *
     * @param title the title of the dialog
     * @param msg   the message to display
     */
    public static void showInfo(String title, String msg) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle(title);
        info.setContentText(msg);
        info.showAndWait();
    }

    /**
     * Display a confirmation dialog and return the user's choice.
     *
     * @param title the title of the confirmation dialog
     * @param msg   the message to display
     * @return true if the user clicked OK, false otherwise
     */
    public static boolean getConfirmation(String title, String msg) {
        Alert confirm = new Alert(Alert.AlertType.WARNING);
        confirm.setTitle(title);
        confirm.setContentText(msg);
        confirm.setHeaderText(null);
        confirm.setResizable(false);
        confirm.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = confirm.showAndWait();
        return result.get() == ButtonType.OK;
    }
}
