package com.example.photoapplication.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class PhotoView {

    @FXML
    private Button btnAddPhoto;

    @FXML
    private Button btnAddTag;

    @FXML
    private Button btnAddTagType;

    @FXML
    private Button btnCaption;

    @FXML
    private Button btnCopyPhoto;

    @FXML
    private Button btnMovePhoto;

    @FXML
    private Button btnRemovePhoto;

    @FXML
    private Button btnRemoveTag;

    @FXML
    private Button btnViewSlide;

    @FXML
    private Text capT;

    @FXML
    private Text dateTimeT;

    @FXML
    private ChoiceBox<?> ddMoveCopy;

    @FXML
    private ChoiceBox<?> ddTagTypes;

    @FXML
    private ListView<?> lvPhotos;

    @FXML
    private AnchorPane managePhotoAP;

    @FXML
    private ImageView photoDisplay;

    @FXML
    private ListView<?> tagsList;

    @FXML
    private TextField tfTagValue;

    @FXML
    void actionOnAddPhoto(ActionEvent event) {

    }

    @FXML
    void actionOnAddTag(ActionEvent event) {

    }

    @FXML
    void actionOnAddTagType(ActionEvent event) {

    }

    @FXML
    void actionOnCopyPhoto(ActionEvent event) {

    }

    @FXML
    void actionOnMovePhoto(ActionEvent event) {

    }

    @FXML
    void actionOnReCaption(ActionEvent event) {

    }

    @FXML
    void actionOnRemovePhoto(ActionEvent event) {

    }

    @FXML
    void actionOnSlideShow(ActionEvent event) {

    }

    @FXML
    void back(ActionEvent event) {

    }

    @FXML
    void removeTag(ActionEvent event) {

    }

}
