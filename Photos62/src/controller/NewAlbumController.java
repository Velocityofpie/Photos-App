package controller;

import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Album;
import model.User;
import model.Album;
import model.Photo;
import model.Tag;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author Joshua Hernandez
 * @author John Lavin
 */
public class NewAlbumController {

    @FXML
    private Button UploadButton, CancelButton, CreateButton;

    @FXML
    private TextField NameTextfield;

    @FXML
    private Label lblFiles;

    private User user;
    private User updatedUser;
    private Album updatedAlbum;
    private ArrayList<User> users;

    /**
     * Method called when switched to this scene's controller
     * @param u user currently logged in
     * @param us list of all users
     */
    public void start(User u, ArrayList<User> us) {
        user = u;
        users = us;
        CreateButton.setDisable(true);
    }

    /**
     * Method to add a new album to the logged in user
     * @param event
     */
    public void finish(ActionEvent event) {
        //check if the album name exists
        String name = NameTextfield.getText();
        //System.out.println(name);
        Boolean exist = user.albumNameExists(name);
        if (exist) {
            duplicateAlbumAlert();
            return;
        }

        //make sure the person put a name
        if (name.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(
                    "The name of the album is blank.");

            String content = "Please input the name of the album";

            alert.setContentText(content);
            alert.showAndWait();
            return;
        }

        //reset the name of the new album
        updatedAlbum.setName(name);
        //add the album to the user
        user.addAlbum(updatedAlbum);

        //switch back to UserController
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
    }

    /**
     * Method to switch back to UserController when user hits cancel button
     * @param event
     */
    public void convertCancelButton(ActionEvent event) {

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

    }

    /**
     * Helper method to display an alert box
     */
    private void duplicateAlbumAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(
                "That album name is already in the library.");

        String content = "Please change the name of the album";

        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Method to open file explorer and select a photo file when the user clicks the upload button
     * @param event
     */
    public void convertUploadButton(ActionEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile == null) {
            return;
        }

        //create a new album, add the photo to that album
        updatedAlbum = new Album("temp");
        Photo p = new Photo(selectedFile.getName(), Calendar.getInstance(), selectedFile.getAbsolutePath());
        updatedAlbum.addPhoto(p);

        //display the name of the file selected
        String str1 = lblFiles.getText();
        String str2 = str1 + "\n" + selectedFile.getName();
        lblFiles.setText(str2);
        //System.out.println(selectedFile.getName());

        //enable the create button
        CreateButton.setDisable(false);
    }
}
