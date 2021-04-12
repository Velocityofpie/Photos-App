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
import javafx.stage.Stage;
import model.Album;
import model.User;

public class NewAlbumController {

    @FXML
    private Button UploadButton, UndoButton, CreateButton;

    @FXML
    private TextField NameTextfield;

    private User user;

    public void start(User u) {
        user = u;
    }

    public void create(ActionEvent e) {
        //check if the album name exists
        String name = NameTextfield.getText();
        System.out.println(name);
        Boolean exist = user.albumNameExists(name);
        if (exist) {
            duplicateAlbumAlert();
            return;
        }

        //create new album
        Album album = new Album(name);
        user.addAlbum(album);

        //switch back to UserController
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserInterface.fxml"));
            Parent parent = (Parent) loader.load();
            UserController controller = loader.<UserController>getController();
            ScrollPane scroll = new ScrollPane(parent);

            Scene scene = new Scene(scroll);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            controller.start(user);
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {

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
}
