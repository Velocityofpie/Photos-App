package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;
import javafx.scene.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

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
    private ListView<?> ListoftagsListview;
    @FXML
    private TextField TagTextfield;

    @FXML
    private TextField TagValueTextfield;

    @FXML
    private Label lblDate1;

    private ArrayList<User> users;
    private User user;
    private Album selectedAlbum;
    private Photo selectedPhoto;
     //list of users
    private ArrayList<Photo> photos;
    ListView<Photo> photos2;

    public void start(User u, ArrayList<User> us, Album album, Photo p) throws FileNotFoundException {
        users = us;
        user = u;
        selectedAlbum = album;
        selectedPhoto = p;
        photos = album.getPhotos();

        update(selectedPhoto.getImgsrc());

    }

    public void update(String s) throws FileNotFoundException {
        InputStream stream = new FileInputStream(s);
        Image image = new Image(stream);
        imgView.setImage(image);
        lblDate.setText("Date: " + selectedPhoto.getDate());
    }

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

    public void addPhotoFunction(ActionEvent event) {
    }

    public void deletePhotoFromAlbum(ActionEvent event) {
    }

    public void EditPhotoFunction(ActionEvent event) {
    }

}
