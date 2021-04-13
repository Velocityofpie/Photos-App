package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Album;
import model.User;
import model.Album;
import model.Photo;
import model.Tag;

import java.io.File;
import java.util.Calendar;


public class NewAlbumController {

    @FXML
    private Button UploadButton, CancelButton, CreateButton;

    @FXML
    private TextField NameTextfield;

    private User user;
    private User updatedUser;
    private Album updatedAlbum;

    public void start(User u) {
        user = u;
        CreateButton.setDisable(true);
    }

    public void finish(ActionEvent event) {
        //check if the album name exists
        String name = NameTextfield.getText();
        System.out.println(name);
        Boolean exist = user.albumNameExists(name);
        if (exist) {
            duplicateAlbumAlert();
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
            controller.start(user);
            stage.setScene(scene);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    public void convertCancelButton(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserInterface.fxml"));
            Parent parent = (Parent) loader.load();
            UserController controller = loader.<UserController>getController();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            controller.start(user);
            stage.setScene(scene);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
    private void duplicateAlbumAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(
                "That album name is already in the library.");

        String content = "Please change the name of the album";

        alert.setContentText(content);
        alert.showAndWait();
    }

    public void convertUploadButton(ActionEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(
                    "Cannot create an album with no photos.");

            String content = "Please upload a photo or press cancel.";

            alert.setContentText(content);
            alert.showAndWait();
            return;
        }
        //display the name of the file selected
        System.out.println(selectedFile.getName());
        //create a new album, add the photo to that album
        updatedAlbum = new Album("temp");
        Photo p = new Photo("temp", Calendar.getInstance(), selectedFile.getAbsolutePath());
        updatedAlbum.addPhoto(p);

        //enable the create button
        CreateButton.setDisable(false);
    }
}
