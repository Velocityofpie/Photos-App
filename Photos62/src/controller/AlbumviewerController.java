package controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;
import javafx.scene.control.ScrollPane;

public class AlbumviewerController {
    @FXML private Button AddphotoButton, DeletePhotoButton,EditphotoButton;

    @FXML
    private Button BacktoalbumButton,LogoutButton;

    @FXML
    private ImageView AlbumImage;

    @FXML
    private Text DateText,NumberofphotoText,NewestphotoText;

    @FXML
    private Text OldestPhotoText;

    @FXML
    private Button RenameButton;

    @FXML
    private ImageView SelectedImage;

    @FXML
    private Text PhotodateText;

    @FXML
    private Button MovetoButton;

    @FXML
    private Button CopytoButton;

    @FXML
    private Label CaptionLabel;

    @FXML
    private Label TagLabel;

    @FXML
    private TextField SearchDateTextField;

    @FXML
    private TextField SearchTagTextField;

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

    public void convertBacktoalbumButton(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserInterface.fxml"));
            Parent parent = (Parent) loader.load();
            UserController controller = loader.<UserController>getController();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}
