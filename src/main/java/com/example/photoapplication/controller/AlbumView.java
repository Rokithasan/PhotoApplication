package com.example.photoapplication.controller;

import com.example.photoapplication.data.PhotoDataBase;
import com.example.photoapplication.model.Album;
import com.example.photoapplication.model.Photo;
import com.example.photoapplication.model.User;
import com.example.photoapplication.utill.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AlbumView  implements Initializable {

    @FXML
    private VBox albumDetails;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnRename;

    @FXML
    private Button btnSearchDate;

    @FXML
    private Button btnSearchTag;

    @FXML
    private ListView<String> lvAlbums;

    public static ObservableList<String> albums;

    private ObservableList<String> tags;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        albums = FXCollections.observableArrayList();
        lvAlbums.setItems(albums);

        User currentUser = PhotoDataBase.getCurrentSessionUser();

        ArrayList<Album> currentAlbums = currentUser.getAlbumList();
        for (Album a : currentAlbums) {
            albums.add(a.getAlbumName());
        }
        if (!albums.isEmpty()) {
            lvAlbums.getSelectionModel().select(0);
        }
        displayAlbum();
        if (albums.isEmpty()) {
            btnDelete.setDisable(true);
            btnRename.setDisable(true);
        }

        lvAlbums.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    String path = null;
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        User u = PhotoDataBase.getCurrentSessionUser();
                        ArrayList<Album> a = u.getAlbumList();
                        for (Album am : a) {
                            if (am.getAlbumName().equals(item)) {
                                ArrayList<Photo> p = am.getPhotoList();
                                if (p.size() > 0) {
                                    Photo lastP = p.get(p.size() - 1);
                                    path = lastP.getPath();
                                }
                            }
                        }
                        if (path == null) {
                            path = "database/tempphoto.png";
                        }
                        ImageView imageView = new ImageView();
                        Image image = new Image("file:" + path);
                        Label name = new Label(item);
                        name.setFont(new Font(20));
                        imageView.setFitWidth(150);
                        imageView.setFitHeight(120);
                        imageView.setImage(image);

                        HBox hb = new HBox();
                        VBox vb = new VBox();
                        vb.getChildren().addAll(name);
                        vb.setPadding(new Insets(40, 0, 0, 40));
                        hb.getChildren().addAll(imageView, vb);
                        setGraphic(hb);
                    }
                }
            };

            cell.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                    try {
                        String aNm = lvAlbums.getSelectionModel().getSelectedItem();
                        User u = PhotoDataBase.getCurrentSessionUser();
                        u.setCurrentSessionAlbum(aNm);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/photoapplication/photo-view.fxml"));
                        Parent openAlbum = loader.load();
                        Scene allAlbumsScene = new Scene(openAlbum);
                        Stage mainStage = (Stage) lvAlbums.getScene().getWindow();
                        mainStage.setScene(allAlbumsScene);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return cell;
        });

        tags = FXCollections.observableArrayList();
        tags.add(0, "--Select--");
        tags.addAll(currentUser.getTypeList());

    }

    private void displayAlbum() {

        Label label0 = new Label("Selected Album Details: ");
        Label label1 = new Label("Album Name: ");
        Label label2 = new Label("No. of Photos: ");
        Label label3 = new Label("Date Range: ");

        if (!albums.isEmpty()) {
            int selectedID = lvAlbums.getSelectionModel().getSelectedIndex();
            if (selectedID == -1) {
                lvAlbums.getSelectionModel().select(0);
            } else {
                lvAlbums.getSelectionModel().select(selectedID);
            }

            String name = null;
            int photosNum = 0;
            String dateRange = null;

            User currentUser = PhotoDataBase.getCurrentSessionUser();

            ArrayList<Album> currentAlbums = currentUser.getAlbumList();
            for (Album a : currentAlbums) {
                if (a.getAlbumName().equals(lvAlbums.getSelectionModel().getSelectedItem())) {
                    name = a.getAlbumName();
                    photosNum = a.PhotosNum();
                    dateRange = a.dateRange();
                }
            }

            label0.setText("Selected Album Details: ");
            label0.setFont(new Font(20));
            label1.setText("Album Name: " + name);
            label1.setFont(new Font(14));
            label2.setText("No. of Photos: " + photosNum);
            label2.setFont(new Font(14));
            label3.setText("Date Range: " + dateRange);
            label3.setFont(new Font(14));
        }
        albumDetails.getChildren().clear();
        albumDetails.setSpacing(10);
        albumDetails.getChildren().addAll(label0, label1, label2, label3);
        if (albums.isEmpty()) {
            albumDetails.getChildren().clear();
        }
    }

    @FXML
    void actionOnCreateAlbum(ActionEvent event) {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create New Album: ");
        dialog.setHeaderText("Enter the name of new album: ");

        dialog.showAndWait().ifPresent(str
                -> {
            if (!albums.contains(str)) {
                Album newAlbum = new Album(str);
                User currentUser = PhotoDataBase.getCurrentSessionUser();
                currentUser.addAlbum(newAlbum);
                albums.add(str);
                int index = albums.indexOf(str);
                lvAlbums.getSelectionModel().select(index);
                displayAlbum();
                btnDelete.setDisable(false);
                btnRename.setDisable(false);
                try {
                    PhotoDataBase userData = PhotoDataBase.getInstance();
                    userData.writeToAFile();
                } catch (Exception ex) {

                }
            } else {
                Utils.showError("Create Album", "An album with the name \"" + str + "\" already exists.");
                actionOnCreateAlbum(event);
            }
        });
    }



    @FXML
    void actionOnDeleteAlbum(ActionEvent event) {

        int selectedID = lvAlbums.getSelectionModel().getSelectedIndex();

        if (selectedID != -1) {
            if (Utils.getConfirmation("Delete Album", "Are you sure you want to delete selected Album?")) {
                User currentUser = PhotoDataBase.getCurrentSessionUser();
                String selectedAlbum = lvAlbums.getSelectionModel().getSelectedItem();
                currentUser.removeAlbum(selectedAlbum);
                albums.remove(selectedID);

                if (selectedID >= albums.size()) {
                    selectedID -= 1;
                }

                if (!albums.isEmpty()) {
                    lvAlbums.getSelectionModel().select(selectedID);
                } else {
                    btnDelete.setDisable(true);
                    btnRename.setDisable(true);
                }
                try {
                    PhotoDataBase userData = PhotoDataBase.getInstance();
                    userData.writeToAFile();
                } catch (Exception ex) {

                }
            }
            displayAlbum();
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

    @FXML
    void actionOnRenameAlbum(ActionEvent event) {

        String selectedItem = lvAlbums.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Rename Album: ");
            dialog.setHeaderText("Enter a new name:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String newName = result.get();
                if (!newName.equals(selectedItem) && !albums.contains(newName)) {
                    int index = albums.indexOf(selectedItem);
                    User currentUser = PhotoDataBase.getCurrentSessionUser();
                    currentUser.renameAlbum(selectedItem, newName);
                    albums.set(index, newName);
                    displayAlbum();
                    try {
                        PhotoDataBase userData = PhotoDataBase.getInstance();
                        userData.writeToAFile();
                    } catch (Exception ex) {

                    }
                } else {
                    Utils.showError("Rename Album", "The name \"" + newName + "\" already exists.\nPlease enter a different name.");
                    actionOnRenameAlbum(event);
                }
            }
        }

    }

    @FXML
    void actionOnSearchDate(ActionEvent event) {

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Date Range Search");
        dialog.setResizable(false);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        Label fromDateLabel = new Label("From Date:");
        DatePicker fromDateField = new DatePicker();
        fromDateField.setEditable(false);
        fromDateField.getEditor().setOnMouseClicked(e -> fromDateField.show());
        fromDateField.setConverter(new LocalDateStringConverter(dateFormatter, null));
        Label toDateLabel = new Label("To Date:");
        DatePicker toDateField = new DatePicker();
        toDateField.setEditable(false);
        toDateField.getEditor().setOnMouseClicked(e -> toDateField.show());
        toDateField.setConverter(new LocalDateStringConverter(dateFormatter, null));
        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> {
            LocalDate fromDate = fromDateField.getValue();
            LocalDate toDate = toDateField.getValue();
            if (fromDate == null || toDate == null) {
                Utils.showError("Search Photos", "Please select a valid Date-Range");
            } else if (fromDate.isAfter(toDate)) {
                Utils.showError("Search Photos", "Please select a valid Date-Range");
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchPhotos.fxml"));
                try {
                    Parent searchPhotos = loader.load();
                    SearchPhoto controller = loader.getController();
                    Scene searchPhotosScene = new Scene(searchPhotos);
                    controller.search(fromDate, toDate, null, null, null);
                    Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    mainStage.setScene(searchPhotosScene);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                dialog.close();
            }
        });

        VBox vbox = new VBox(fromDateLabel, fromDateField, toDateLabel, toDateField, searchButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        dialog.setScene(new Scene(vbox));
        dialog.showAndWait();

    }

    @FXML
    void actionOnSearchTag(ActionEvent event) {

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Tag Pair Search: ");
        dialog.setResizable(false);

        VBox v1 = new VBox();
        Label categoryLabel = new Label("Select Tag Type 1:");
        ChoiceBox<String> categoryChoiceBox = new ChoiceBox<>(tags);
        categoryChoiceBox.getSelectionModel().selectFirst();
        Label tagLabel = new Label("Tag Value:");
        TextField tagTextField = new TextField();
        v1.getChildren().addAll(categoryLabel, categoryChoiceBox, tagLabel, tagTextField);
        v1.setAlignment(Pos.CENTER);
        v1.setSpacing(10);
        v1.setPadding(new Insets(10));

        VBox vm = new VBox();
        ChoiceBox<String> cbs = new ChoiceBox<>();
        cbs.getItems().add(0, "--Select--");
        cbs.getItems().add(1, "AND");
        cbs.getItems().add(2, "OR");
        cbs.getSelectionModel().select(0);
        vm.getChildren().add(cbs);
        vm.setAlignment(Pos.CENTER);
        vm.setSpacing(80);
        vm.setPadding(new Insets(10));

        VBox v2 = new VBox();
        Label categoryLabel2 = new Label("Select Tag Type 1:");
        ChoiceBox<String> categoryChoiceBox2 = new ChoiceBox<>(tags);
        categoryChoiceBox2.getSelectionModel().selectFirst();
        Label tagLabel2 = new Label("Tag Value:");
        TextField tagTextField2 = new TextField();
        v2.getChildren().addAll(categoryLabel2, categoryChoiceBox2, tagLabel2, tagTextField2);
        v2.setAlignment(Pos.CENTER);
        v2.setSpacing(10);
        v2.setPadding(new Insets(10));

        HBox h = new HBox();
        h.getChildren().addAll(v1, vm, v2);

        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> {
            String category = categoryChoiceBox.getValue();
            String tagValue = tagTextField.getText();
            String tagStr = category + " : " + tagValue;
            String category2 = categoryChoiceBox2.getValue();
            String tagValue2 = tagTextField2.getText();
            String tagStr2 = category2 + " : " + tagValue2;
            String operator = cbs.getValue();
            if (categoryChoiceBox.getSelectionModel().getSelectedIndex() == 0) {
                Utils.showError("Invalid Tag Pair", "No Tag Category Selected");
            } else if (tagValue.isEmpty()) {
                Utils.showError("Invalid Tag Pair", "Please Enter a Valid Tag Value");
            } else if (categoryChoiceBox2.getSelectionModel().getSelectedIndex() != 0 && tagValue2.isEmpty()) {
                Utils.showError("Invalid Tag Pair", "Please Enter a Valid Tag Value");
            } else if (cbs.getSelectionModel().getSelectedIndex() == 0 && categoryChoiceBox2.getSelectionModel().getSelectedIndex() != 0 && !tagValue2.isEmpty()) {
                Utils.showError("Invalid Tag Pair", "Please Enter a Valid Tag Value");
            } else if (categoryChoiceBox2.getSelectionModel().getSelectedIndex() == 0 && !tagValue2.isEmpty()) {
                Utils.showError("Invalid Tag Pair", "Please Enter a Valid Tag Value");
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("searchPhotos.fxml"));
                try {
                    Parent searchPhotos = loader.load();
                    SearchPhoto controller = loader.getController();
                    Scene searchPhotosScene = new Scene(searchPhotos);
                    if (categoryChoiceBox2.getSelectionModel().getSelectedIndex() == 0) {
                        tagStr2 = null;
                        operator = null;
                    }
                    controller.search(null, null, tagStr, operator, tagStr2);
                    Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    mainStage.setScene(searchPhotosScene);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                dialog.close();
            }
        });
        vm.getChildren().add(searchButton);
        h.setAlignment(Pos.CENTER);
        h.setSpacing(10);
        h.setPadding(new Insets(10));
        dialog.setScene(new Scene(h));
        dialog.showAndWait();

    }

}
