package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Album;
import model.Photo;
import model.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ImageviewerController {

    @FXML
    private ImageView imgView;

    private ArrayList<User> users;
    private User user;
    private Album selectedAlbum;
    private Photo selectedPhoto;

    public void start(User u, ArrayList<User> us, Album album, Photo p) throws FileNotFoundException {
        users = us;
        user = u;
        selectedAlbum = album;
        selectedPhoto = p;

        InputStream stream = new FileInputStream(selectedPhoto.getImgsrc());
        Image image = new Image(stream);
        imgView.setImage(image);

    }

    public void convertBacktoalbumButton(ActionEvent event) {
    }

    public void convertLogOutButton(ActionEvent event) {
    }
}
