package com.example.photoapplication.utill;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Utils {
    public static void showError(String title, String msg) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle(title);
        error.setContentText(msg);
        error.showAndWait();
    }
    public static void showInfo(String title, String msg) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle(title);
        info.setContentText(msg);
        info.showAndWait();
    }
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
