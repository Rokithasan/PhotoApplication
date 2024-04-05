/**
 * The ViewSlideShow class represents the controller for the photo slideshow view in a photo application.
 * This class controls the functionality related to displaying a slideshow of photos from an album.
 * It interacts with the Album and Photo classes to access photo data.
 * The ViewSlideShow class implements the Initializable interface to initialize the UI components
 * defined in the corresponding FXML file.
 */
package com.example.photoapplication.controller;

import com.example.photoapplication.model.Album;
import com.example.photoapplication.model.User;
import com.example.photoapplication.utill.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import com.example.photoapplication.model.Photo;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewSlideShow implements Initializable {

    @FXML
    private Button btnExitSlide;
    @FXML
    private Button btnNext;
    @FXML
    private Button btnPrev;
    @FXML
    private ImageView img;
    private int pointer;
    private ArrayList<Photo> photoList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    /**
     * Starts the slideshow with the specified photo index.
     *
     * @param count The index of the photo to start the slideshow.
     */
    public void start(int count) {
        pointer = count;
        Album a = User.getCurrentSessionAlbum();
        ArrayList<Photo> photos = a.getPhotoList();
        photoList = photos;
        img.setImage(new Image("file:" + photoList.get(pointer).getPath()));
        img.setPreserveRatio(true);
    }

    @FXML
    void actionOnExitSlide(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/photoapplication/album-view.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void actionOnNext(ActionEvent event) {

        if (photoList.size() > 1) {
            pointer++;
            if (pointer == photoList.size()) {
                pointer = 0;
            }
            img.setImage(new Image("file:" + photoList.get(pointer).getPath()));
            img.setPreserveRatio(true);
        } else {
            Utils.showError("Slide Show", "This album has only 1 photo");
        }
    }

    @FXML
    void actionOnPrev(ActionEvent event) {

        if (photoList.size() > 1) {
            pointer--;
            if (pointer == -1) {
                pointer = photoList.size() - 1;
            }
            img.setImage(new Image("file:" + photoList.get(pointer).getPath()));
            img.setPreserveRatio(true);
        } else {
            Utils.showError("Slide Show", "This album has only 1 photo");
        }

    }

}
