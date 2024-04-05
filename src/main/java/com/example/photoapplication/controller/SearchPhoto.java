/**
 * The SearchPhoto class represents the controller for the search photo screen in a photo application.
 * This class controls the functionality related to searching for photos based on date range or tag pairs.
 * It interacts with the PhotoDataBase and User classes to access user and photo data.
 * The SearchPhoto class implements the Initializable interface to initialize the UI components
 * defined in the corresponding FXML file.
 */

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class SearchPhoto implements Initializable {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnSearchDate;

    @FXML
    private Button btnSearchTag;

    @FXML
    private ListView<String> searchPhotosList;

    private ObservableList<String> tags;

    private final ObservableList<String> photos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchPhotosList.setItems(photos);
        tags = FXCollections.observableArrayList();
        tags.add(0, "--Select--");
        User currentUser = PhotoDataBase.getCurrentSessionUser();
        tags.addAll(currentUser.getTypeList());

    }

    @FXML
    void actionOnBack(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/com/example/photoapplication/album-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void actionOnCreateAlbum(ActionEvent event) {

        if (photos.isEmpty()) {
            Utils.showError("Create Album", "No search results to create an album");
        } else {
            ArrayList<Photo> tempP = new ArrayList<>();
            User currUser = PhotoDataBase.getCurrentSessionUser();
            ArrayList<Album> userAlbums = currUser.getAlbumList();
            for (Album a : userAlbums) {
                ArrayList<Photo> userPhotos = a.getPhotoList();
                for (Photo p : userPhotos) {
                    if (photos.contains(p.getPath())) {
                        tempP.add(p);
                    }
                }
            }
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Album Name: ");
            dialog.setHeaderText("Enter a album name: ");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String nm = result.get();
                userAlbums.add(new Album(nm, tempP));
                Utils.showInfo("Create Album", "Album created successfully");
                try {
                    PhotoDataBase userData = PhotoDataBase.getInstance();
                    userData.writeToAFile();
                } catch (Exception ex) {

                }
            }
        }

    }

    @FXML
    void actionOnDateSearch(ActionEvent event) {

        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Date Range Search");
        popup.setResizable(false);
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
                Utils.showError("Search Photos", "From Date cannot be after To Date");
            } else {
                popup.close();
                photos.clear();
                search(fromDate, toDate, null, null, null);
            }
        });
        VBox vbox = new VBox(fromDateLabel, fromDateField, toDateLabel, toDateField, searchButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        popup.setScene(new Scene(vbox));
        popup.showAndWait();

    }

    @FXML
    void actionOnTagSearch(ActionEvent event) {

        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Tag Pair Search: ");
        popup.setResizable(false);

        VBox v1 = new VBox();
        Label categoryLabel = new Label("Select Tag Category 1:");
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
        Label categoryLabel2 = new Label("Select Tag Category 1:");
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
                Utils.showError("Search Photos", "No Tag Category Selected");
            } else if (tagValue.isEmpty()) {
                Utils.showError("Search Photos", "Please Enter a Valid Tag Value");
            } else if (categoryChoiceBox2.getSelectionModel().getSelectedIndex() != 0 && tagValue2.isEmpty()) {
                Utils.showError("Search Photos", "Please Enter a Valid Tag Value");
            } else if (cbs.getSelectionModel().getSelectedIndex() == 0 && categoryChoiceBox2.getSelectionModel().getSelectedIndex() != 0 && !tagValue2.isEmpty()) {
                Utils.showError("Search Photos", "Please Select a Valid Conjunction/Disjunction Operator");
            } else if (categoryChoiceBox2.getSelectionModel().getSelectedIndex() == 0 && !tagValue2.isEmpty()) {
                Utils.showError("Search Photos", "Please Enter a Valid Tag Value");
            } else {
                popup.close();
                photos.clear();
                if (categoryChoiceBox2.getSelectionModel().getSelectedIndex() == 0) {
                    tagStr2 = null;
                    operator = null;
                }
                search(null, null, tagStr, operator, tagStr2);
            }
        });
        vm.getChildren().add(searchButton);
        h.setAlignment(Pos.CENTER);
        h.setSpacing(10);
        h.setPadding(new Insets(10));
        popup.setScene(new Scene(h));
        popup.showAndWait();

    }

    /**
     * Performs a search for photos based on specified criteria.
     *
     * @param fromDate The start date for the search.
     * @param toDate   The end date for the search.
     * @param tagStr   The tag string for the search.
     * @param operator The logical operator for tag search.
     * @param tagStr2  The second tag string for the search.
     */
    public void search(LocalDate fromDate, LocalDate toDate, String tagStr, String operator, String tagStr2) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        User currentUser = PhotoDataBase.getCurrentSessionUser();
        ArrayList<Album> currAlbums = currentUser.getAlbumList();

        Predicate<Photo> searchPredicate;
        if (operator != null && tagStr2 != null) {
            if (operator.equalsIgnoreCase("AND")) {
                searchPredicate = photo -> photo.containsTag(tagStr) && photo.containsTag(tagStr2);
            } else if (operator.equalsIgnoreCase("OR")) {
                searchPredicate = photo -> photo.containsTag(tagStr) || photo.containsTag(tagStr2);
            } else {
                searchPredicate = photo -> photo.containsTag(tagStr);
            }
        } else {
            searchPredicate = photo -> photo.containsTag(tagStr);
        }

        for (Album album : currAlbums) {
            String albumDateRange = album.dateRange();
            if (albumDateRange == null || albumDateRange.isEmpty()) {
                continue; // Skip this album if date range is missing
            }
            int delimiterIndex = albumDateRange.indexOf("-");
            if (delimiterIndex == -1) {
                continue; // Skip this album if the delimiter is not found
            }
            String albumFromDateStr = albumDateRange.substring(0, delimiterIndex).trim();
            String albumToDateStr = albumDateRange.substring(delimiterIndex + 1).trim();

            LocalDate albumFromDate;
            LocalDate albumToDate;
            try {
                albumFromDate = LocalDate.parse(albumFromDateStr, dateFormatter);
                albumToDate = LocalDate.parse(albumToDateStr, dateFormatter);
            } catch (DateTimeParseException e) {
                // Handle parsing error
                System.err.println("Error parsing album date range: " + albumDateRange);
                e.printStackTrace();
                continue; // Skip this album if date parsing fails
            }

            if ((fromDate == null && toDate == null) || ((albumFromDate.isAfter(fromDate) || albumFromDate.isEqual(fromDate)) && (albumToDate.isBefore(toDate) || albumToDate.isEqual(toDate)))) {
                ArrayList<Photo> albumPhotos = album.getPhotoList();
                for (Photo photo : albumPhotos) {
                    String photoLastModDateStr = photo.getLastModDate().substring(0, 10);
                    LocalDate photoLastModDate;
                    try {
                        photoLastModDate = LocalDate.parse(photoLastModDateStr, dateFormatter);
                    } catch (DateTimeParseException e) {
                        // Handle parsing error
                        System.err.println("Error parsing photo last modified date: " + photo.getLastModDate());
                        e.printStackTrace();
                        continue; // Skip this photo if date parsing fails
                    }
                    if ((fromDate == null && toDate == null) || ((photoLastModDate.isAfter(fromDate) || photoLastModDate.isEqual(fromDate)) && (photoLastModDate.isBefore(toDate) || photoLastModDate.isEqual(toDate)))) {
                        if ((fromDate != null && toDate != null) || searchPredicate.test(photo)) {
                            if (!photos.contains(photo.getPath())) {
                                photos.add(photo.getPath());
                            }
                        }
                    }
                }
            }
        }
        displayPhotos();
    }



    /**
     * Displays the search results in the ListView.
     */
    private void displayPhotos() {

        if (photos.isEmpty()) {
            Label noResultsLabel = new Label("No Search Results Exist.");
            noResultsLabel.setStyle("-fx-text-fill: gray;");
            searchPhotosList.setPlaceholder(noResultsLabel);
        }
        List<Image> images = new ArrayList<>();
        for (String imagePath : photos) {
            images.add(new Image("file:" + imagePath));
        }
        searchPhotosList.setCellFactory(param -> new ListCell<String>() {
            private final HBox hbox = new HBox();
            private final List<ImageView> imageViews = new ArrayList<>();

            @Override
            protected void updateItem(String imagePath, boolean empty) {
                super.updateItem(imagePath, empty);

                if (empty || imagePath == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    hbox.getChildren().clear();
                    imageViews.clear();
                    int index = getIndex() * 3;
                    for (int i = 0; i < 3 && index < images.size(); i++, index++) {
                        ImageView imageView = new ImageView(images.get(index));
                        imageView.setFitWidth(220);
                        imageView.setFitHeight(187);
                        imageViews.add(imageView);
                        hbox.getChildren().add(imageView);
                        if (i < 2 && index < images.size() - 1) {
                            Region spacer = new Region();
                            spacer.setPrefWidth(41.5);
                            hbox.getChildren().add(spacer);
                        }
                    }
                    setGraphic(hbox);
                }
            }
        });
    }


}
