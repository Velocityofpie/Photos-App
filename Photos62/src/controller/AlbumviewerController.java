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

import java.io.*;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
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


    public void start(ArrayList<User> users, User user,ListView<Photo> photos,  Album selectedAlbum) throws FileNotFoundException {
        this.users = users;
        this.photos = photos;
        this.user = user;
       // this.selectedAlbum = selectedAlbum;
        //Photo selectedPhoto = photos.getSelectionModel().getSelectedItem();
        this.users = users;
        this.photos = photos;
        this.user = user;
        InputStream stream = new FileInputStream("Photos62/data/stockuser/Stock1.png");
        Image image = new Image(stream);
        SelectedImage.setImage(image);
        //Setting the image view parameters

        SelectedImage.setFitWidth(210);
        SelectedImage.setPreserveRatio(true);
      //  Photo selectedPhoto = photos.getSelectionModel().getSelectedItem();
    }

    // in the works need to be converting for photos
    public void convert(ActionEvent actionEvent) {
//        Button b = (Button) actionEvent.getSource();
//        if (b == DeletePhotoButton) {
//
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setTitle("Confirmation Dialog");
//            alert.setHeaderText("Need Confirmation");
//            alert.setContentText("Are you ok with this?");
//            Optional<ButtonType> result = alert.showAndWait();
//            if (result.get() == ButtonType.OK){
//                Album deleteThis = AlbumListview.getSelectionModel().getSelectedItem();
//                user.removeAlbum(deleteThis);
//
//                //update listview
//                AlbumListview.setItems(FXCollections.observableArrayList(user.getAlbums()));
//            } else {
//                // ... user chose CANCEL or closed the dialog
//            }
//
//        } else if (b == EditphotoButton) {
//            Album item = AlbumListview.getSelectionModel().getSelectedItem();
//            int index = AlbumListview.getSelectionModel().getSelectedIndex();
//            if (item == null) {
//                return;
//            }
//            //pop up asking for new name
//            TextInputDialog dialog = new TextInputDialog(item.getName());
//            dialog.initOwner((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
//            dialog.setTitle("Edit selected album");
//            dialog.setHeaderText("Change the name of the album");
//            dialog.setContentText("Enter name: ");
//
//            boolean cont = true;
//            while (cont) {
//                Optional<String> result = dialog.showAndWait();
//                if (result.isPresent()) {
//                    String n = result.get();
//                    if (user.albumNameExists(n)) {
//                        dialog.setContentText("Enter name: ");
//                        dialog.setHeaderText("That album name is taken");
//                    } else if (n.length() == 0) {
//                        dialog.setContentText("Enter name: ");
//                        dialog.setHeaderText("Album name cannot be blank");
//                    } else {
//                        item.setName(n);
//                        obsList.set(index, item);
//                        AlbumListview.setItems(obsList);
//                        cont = false;
//                    }
//                } else {
//                    cont = false;
//                }
//            }
//        }
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
