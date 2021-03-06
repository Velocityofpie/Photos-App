
package controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.util.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;

import java.util.ArrayList;
import javafx.scene.control.ScrollPane;

/**
 * @author Joshua Hernandez
 * @author John Lavin
 */
public class UserController {
    @FXML
    private Button AddAlbumButton, DeleteAlbumButton,EditAlbumButton;

    @FXML
    private Button AblumButton,LogoutButton, QuitButton;

    @FXML
    private ListView<Album> AlbumListview;
    private ObservableList<Album> obsList;

    ListView<Photo> photos;
    private ArrayList<User> users;
    private User user;
    private Album selectedAlbum;

    //@FXML private ListView<Photo> SelectedPhoto;

    private Image image;
    private Label Username;
    private ListView<Tag> tags;




    /**
     * Method to switch scene back to login screen when logout button is pressed
     * @param event
     */
    public void convertLogOutButton(ActionEvent event) {

        //save the data
        DataSaving.saveData(users);

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

    /**
     * Method to open a selected album in a new scene
     * @param event
     */
    public void convertAlbum(ActionEvent event) {

        //get selected album
        selectedAlbum = AlbumListview.getSelectionModel().getSelectedItem();
        int index = AlbumListview.getSelectionModel().getSelectedIndex();
        if (selectedAlbum == null) {
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AlbumSelected.fxml"));
            Parent parent = (Parent) loader.load();
            AlbumviewerController controller = loader.<AlbumviewerController>getController();
            ScrollPane scroll = new ScrollPane(parent);
            Scene scene = new Scene(scroll);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            controller.start(users,user, photos, selectedAlbum);
            stage.setScene(scene);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    /**
     * Method called when user is switched to this scene's controller
     * @param user currently selected
     * @param list arraylist of all users
     */
    public void start( User user, ArrayList<User> list) {
        this.user = user;
        this.users = list;


        //save the data
        DataSaving.saveData(users);

        obsList = FXCollections.observableArrayList(user.getAlbums());
        AlbumListview.setItems(obsList);
        selectedAlbum = AlbumListview.getSelectionModel().getSelectedItem();

        /*
        photos.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>() {
            @Override
            public ListCell<Photo> call(ListView<Photo> photoList) {
                return new photoloader();
            }
        });

        photos.setItems(FXCollections.observableArrayList(selectedAlbum.getPhotos()));
        photos.getSelectionModel().select(0);


         */

        ArrayList<String> albumnames = new ArrayList<String>();
        albumnames.add(0, " ");
        ArrayList<Album> allalbums = user.getAlbums();
        for (Album curralbum : allalbums) {
            albumnames.add(curralbum.getName());
        }





    }

    /**
     * Method to delete or edit an album when the delete/edit album button is pressed
     * @param actionEvent
     */
    public void convert(ActionEvent actionEvent) {
        Button b = (Button) actionEvent.getSource();
        if (b == DeleteAlbumButton) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Need Confirmation");
            alert.setContentText("Are you ok with this?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Album deleteThis = AlbumListview.getSelectionModel().getSelectedItem();
                user.removeAlbum(deleteThis);

                //update listview
                AlbumListview.setItems(FXCollections.observableArrayList(user.getAlbums()));
            } else {
                // ... user chose CANCEL or closed the dialog
            }

        } else if (b == EditAlbumButton) {
            Album item = AlbumListview.getSelectionModel().getSelectedItem();
            int index = AlbumListview.getSelectionModel().getSelectedIndex();
            if (item == null) {
                return;
            }
            //pop up asking for new name
            TextInputDialog dialog = new TextInputDialog(item.getName());
            dialog.initOwner((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
            dialog.setTitle("Edit selected album");
            dialog.setHeaderText("Change the name of the album");
            dialog.setContentText("Enter name: ");

            boolean cont = true;
            while (cont) {
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    String n = result.get();
                    if (user.albumNameExists(n)) {
                        dialog.setContentText("Enter name: ");
                        dialog.setHeaderText("That album name is taken");
                    } else if (n.length() == 0) {
                        dialog.setContentText("Enter name: ");
                        dialog.setHeaderText("Album name cannot be blank");
                    } else {
                        item.setName(n);
                        obsList.set(index, item);
                        AlbumListview.setItems(obsList);
                        cont = false;
                    }
                } else {
                    cont = false;
                }
            }
        }
        //save the data
        DataSaving.saveData(users);
    }

    /**
     * Method to switch scene when the user clicks the add album button
     * @param e
     */
    public void create(ActionEvent e) {
        //switch to new album scene
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Newalbum.fxml"));
            Parent parent = (Parent) loader.load();
            NewAlbumController controller = loader.<NewAlbumController>getController();
            Scene scene2 = new Scene(parent);
            Stage stage2 = (Stage) ((Node) e.getSource()).getScene().getWindow();
            controller.start(user, users);
            stage2.setScene(scene2);
            stage2.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
