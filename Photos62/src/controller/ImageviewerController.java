package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.*;
import javafx.scene.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Optional;

/**
 * @author Joshua Hernandez
 * @author John Lavin
 */



public class ImageviewerController {

    @FXML
    private ImageView imgView;

    @FXML
    private Label lblDate;

    @FXML
    private TextField TagTextfield;

    @FXML
    private TextField TagValueTextfield;

    @FXML
    private ListView<Tag> lvTags;

    @FXML
    private TextArea captionTextArea;

    private ArrayList<User> users;
    private User user;
    private Album selectedAlbum;
    private Photo selectedPhoto;
     //list of users
    private ArrayList<Photo> photos;
    ListView<Photo> photos2;
    private ObservableList<Tag> obsListTags;

    /**
     * start the call and the images to display them in the list
     * @param u , us, album, p
     * @throws IOException
     */

    public void start(User u, ArrayList<User> us, Album album, Photo p) throws FileNotFoundException {
        users = us;
        user = u;
        selectedAlbum = album;
        selectedPhoto = p;
        photos = album.getPhotos();
        try {
            lvTags.setItems(FXCollections.observableArrayList(selectedPhoto.getTags()));
        } catch (Exception e) {

        }

        // set listener for the items
        lvTags.getSelectionModel().selectedIndexProperty().addListener((obsList, oldVal, newVal) -> showItem());

        update(selectedPhoto.getImgsrc());

    }

    /**
     * Called to get the lastest information on the photo and the lastest sets of photo
     *
     * @throws FileNotFoundException
     */

    public void update(String s) throws FileNotFoundException {
        InputStream stream = new FileInputStream(s);
        Image image = new Image(stream);
        imgView.setImage(image);
        //update date
        lblDate.setText("Date: " + selectedPhoto.getDate());
        //update caption
        captionTextArea.setText(selectedPhoto.getCaption());
        //set tag text fields to be blank, it will be update when the user selects a tag
        TagTextfield.setText("");
        TagValueTextfield.setText("");
        //update the listview of tags
        obsListTags = FXCollections.observableArrayList(selectedPhoto.getTags());
        lvTags.setItems(obsListTags);

        DataSaving.saveData(users);
    }

    /**
     * Method to put the values of the selected item into the textfields
     */
    private void showItem() {

        //get the song from the obsList
        int index = lvTags.getSelectionModel().getSelectedIndex();
        Tag s = null;
        try {
            s = obsListTags.get(index);
            //put necessary values in the text field
            TagTextfield.setText(s.getTagName());
            TagValueTextfield.setText(s.getTagValue());
        } catch (Exception e) {

        }
    }

    /**
     * Called when users presses "back to album" Button
     * Switches the scene back to the AlbumViewer controller
     * @param event
     */

    public void convertBacktoalbumButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AlbumSelected.fxml"));
            Parent parent = (Parent) loader.load();
            AlbumviewerController controller = loader.<AlbumviewerController>getController();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            controller.start(users,user, photos2, selectedAlbum);
            stage.setScene(scene);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Called when users presses "LogOut" Button
     * Switches the scene back to the login screen
     * @param event
     */

    public void convertLogOutButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
            Parent parent = (Parent) loader.load();
            LoginController controller = loader.<LoginController>getController();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Called when users presses "the right arrrow" Button
     * Switches to the next photo
     * @param event
     * @throws FileNotFoundException
     */

    public void nextPhotoFunction(ActionEvent event) throws FileNotFoundException {

        //get index of the selected photo
        int index = selectedAlbum.getPhotoIndexByPhoto(selectedPhoto);
        if (index == selectedAlbum.getPhotoCount() - 1) {
            index = 0;
        } else {
            index++;
        }
        selectedPhoto = photos.get(index);
        update(selectedPhoto.getImgsrc());
    }

    /**
     * Called when users presses "the left arrrow" Button
     * Switches to the pervious
     * @param event
     * @throws FileNotFoundException
     */


    public void previousPhotoFunction(ActionEvent event) throws FileNotFoundException {

        //get index of the selected photo
        int index = selectedAlbum.getPhotoIndexByPhoto(selectedPhoto);
        if (index == 0) {
            index = selectedAlbum.getPhotoCount()-1;
        } else {
            index--;
        }

        selectedPhoto = photos.get(index);
        update(selectedPhoto.getImgsrc());
    }

    /**
     * Called when users presses "add photo" Button
     * adds a new photo to the album
     * @param event
     *
     */

    public void addPhotoFunction(ActionEvent event) {
    }

    /**
     * Called when a user presses delete photo button
     * deletes the photo from the album
     * @param event
     */
    public void deletePhotoFromAlbum(ActionEvent event) {
        ArrayList<Photo> p = selectedAlbum.getPhotos();
        int i = selectedAlbum.getPhotoIndexByPhoto(selectedPhoto);

        //check if album only has one photo, if it does let them know it will delete the whole album
        if (selectedAlbum.getPhotoCount() == 1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("If you delete this photo, it will delete the album");
            alert.setContentText("Are you ok with this?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                //remove album from user
                user.removeAlbum(selectedAlbum);
                DataSaving.saveData(users);
                //switch back to user controller
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserInterface.fxml"));
                    Parent parent = (Parent) loader.load();
                    UserController controller = loader.<UserController>getController();
                    Scene scene = new Scene(parent);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    controller.start(user, users);
                    stage.setScene(scene);
                    stage.show();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            } else {
                // ... user chose CANCEL or closed the dialog
            }
            return;
        }



        selectedAlbum.getPhotos().remove(i);
        //select a new photo
        selectedPhoto = p.get(0);

        //save data
        DataSaving.saveData(users);
        try {
            update(selectedPhoto.getImgsrc());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when users presses "edit photo" Button
     * changes the name of the photo
     * @param event
     *
     */
    public void EditPhotoFunction(ActionEvent event) {
    }

    /**
     * Called when users presses "copy to" Button
     * Copies the photo select to be copied into the new selected album
     * @param event
     *
     */
    public void copyToFunction(ActionEvent event) {
        //pop up asking for new name
        TextInputDialog dialog = new TextInputDialog();
        dialog.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());
        dialog.setTitle("Copy Photo");
        dialog.setHeaderText("Copy the photo to an existing album");
        dialog.setContentText("Enter album: ");

        boolean cont = true;
        while (cont) {
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String n = result.get();
                if (!(user.albumNameExists(n))) {
                    dialog.setContentText("Enter name: ");
                    dialog.setHeaderText("That album does not exist");
                } else if (n.equals(selectedAlbum.getName())) {
                    dialog.setContentText("Enter name: ");
                    dialog.setHeaderText("This photo is already in that album");
                } else {
                    //add selected photo to the album they listed
                    Album addToThisAlbum = user.getAlbumByName(n);
                    addToThisAlbum.addPhoto(selectedPhoto);
                    cont = false;
                }
            } else {
                cont = false;
            }
        }
        //save data
        DataSaving.saveData(users);
    }

    /**
     * Called when users presses "move to" Button
     * moves the photo select to be copied into the new selected album and remove it from the old album
     * @param event
     *
     */
    public void moveToFunction(ActionEvent event) {
        //same code as copyToFunction, but also deletes photo
        //pop up asking for new name
        TextInputDialog dialog = new TextInputDialog();
        dialog.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());
        dialog.setTitle("Copy Photo");
        dialog.setHeaderText("Copy the photo to an existing album");
        dialog.setContentText("Enter album: ");

        boolean cont = true;
        while (cont) {
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String n = result.get();
                if (!(user.albumNameExists(n))) {
                    dialog.setContentText("Enter name: ");
                    dialog.setHeaderText("That album does not exist");
                } else if (n.equals(selectedAlbum.getName())) {
                    dialog.setContentText("Enter name: ");
                    dialog.setHeaderText("This photo is already in that album");
                } else {
                    //add selected photo to the album they listed
                    Album addToThisAlbum = user.getAlbumByName(n);
                    addToThisAlbum.addPhoto(selectedPhoto);
                    cont = false;
                }
            } else {
                return;
            }
        }
        deletePhotoFromAlbum(event);

        //save data
        DataSaving.saveData(users);
    }
    /**
     * Called when users presses "update" Button
     * Updates the caption
     * @param event
     *
     */

    public void updateCaptionFunction(ActionEvent event) {
        selectedPhoto.setCaption(captionTextArea.getText());
        DataSaving.saveData(users);
    }
    /**
     * Called when users presses "add" Button
     * Addes a tag and it value to be stored under its respected user
     * @param event
     *
     */
    public void addTagFunction(ActionEvent event) {
        String tName = TagTextfield.getText();
        String tValue = TagValueTextfield.getText();
        if (tName.equals("") || tValue.equals("")) {
            missingInfoAlert();
            return;
        }

        Tag tag = new Tag(tName,tValue);
        if (selectedPhoto.tagExists(tag)) {
            duplicateTagAlert();
            return;
        } else {
            selectedPhoto.addTag(tag);
        }

        try {
            update(selectedPhoto.getImgsrc());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        DataSaving.saveData(users);
    }

    /**
     * helper method to create an information alert box
     */
    private void duplicateTagAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(
                "That tag is already in the library.");

        String content = "Please change the name or value of the tag";

        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * helper method to create an information alert box
     */
    private void missingInfoAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(
                "Missing tag name or value.");

        String content = "Please input tag name or value";

        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Called when users presses "delete" Button
     * deletes a tag and it value to be stored under its respected user
     * @param event
     *
     */

    public void deleteTagFunction(ActionEvent event) {
        int index = lvTags.getSelectionModel().getSelectedIndex();
        Tag selectedTag = lvTags.getSelectionModel().getSelectedItem();
        obsListTags.remove(index);
        int i = selectedPhoto.getTagIndex(selectedTag);
        if (i>-1) {
            selectedPhoto.removeTag(i);
        }
        try {
            update(selectedPhoto.getImgsrc());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        DataSaving.saveData(users);

    }

    /**
     * Method to update the tags for a selected photo
     * @param event
     */
    public void updateTagFunction(ActionEvent event) {
        String tName = TagTextfield.getText();
        String tValue = TagValueTextfield.getText();
        if (tName.equals("") || tValue.equals("")) {
            missingInfoAlert();
            return;
        }

        Tag tag = new Tag(tName,tValue);
        if (selectedPhoto.tagExists(tag)) {
            duplicateTagAlert();
            return;
        }
        //delete
        int index = lvTags.getSelectionModel().getSelectedIndex();
        Tag selectedTag = lvTags.getSelectionModel().getSelectedItem();

        int i = selectedPhoto.getTagIndex(selectedTag);
        selectedPhoto.removeTag(i);

        selectedPhoto.addTag(tag);
        try {
            update(selectedPhoto.getImgsrc());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
