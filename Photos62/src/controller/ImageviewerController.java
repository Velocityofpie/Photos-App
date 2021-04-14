package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Album;
import model.DataSaving;
import model.Photo;
import model.User;
import javafx.scene.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    public void EditPhotoFunction(ActionEvent event) {
    }

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
}
