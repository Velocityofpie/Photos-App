package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import model.Album;
import model.Photo;
import model.User;

import java.util.ArrayList;

public class SearchPhotoController {

    private ArrayList<User> users;
    private User user;
    private ListView<Photo> photoListView;
    private Album selectedAlbum;

    public void start(ArrayList<User> users, User user, ListView<Photo> photos, Album a) {
        this.users = users;
        this.user = user;
        this.selectedAlbum = a;

    }

    public void addPhotoFunction(ActionEvent event) {
    }

    public void deletePhotoFromAlbum(ActionEvent event) {
    }

    public void EditPhotoFunction(ActionEvent event) {
    }

    public void convertBacktoalbumButton(ActionEvent event) {
    }

    public void convertLogOutButton(ActionEvent event) {
    }
}
