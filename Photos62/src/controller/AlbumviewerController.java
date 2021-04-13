package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.*;
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
import model.Album;
import model.User;


public class AlbumviewerController {
    @FXML private Button AddphotoButton, DeletePhotoButton,EditphotoButton;

    @FXML
    private Button BacktoalbumButton,LogoutButton;

    @FXML
    private ImageView AlbumImage;

    @FXML
    private Text DateText,NumberofphotoText,NewestphotoText,OldestPhotoText;

    @FXML
    private ImageView SelectedImage;

    @FXML
    private Text PhotodateText;

    @FXML
    private Button MovetoButton, CopytoButton,RenameButton;

    @FXML
    private Label CaptionLabel,  TagLabel;

    @FXML
    private TextField SearchDateTextField, SearchTagTextField;

    private Photo photo;
    private String album;
    ArrayList<User> users;
    ListView<Photo> photos;
    private ObservableList<String> obsTags;
    private ObservableList<String> obsValues;
    private ListView<Tag> tags;
   // private Album selectedAlbum;
    private User user;


    public void start(ArrayList<User> users, User user,ListView<Photo> photos,  Album selectedAlbum) {
        this.users = users;
        this.photos = photos;
        this.user = user;
       // this.selectedAlbum = selectedAlbum;
        //Photo selectedPhoto = photos.getSelectionModel().getSelectedItem();

    }


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
            controller.start(user, users);
            stage.setScene(scene);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}
