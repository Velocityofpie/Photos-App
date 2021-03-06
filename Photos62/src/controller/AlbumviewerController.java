package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
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
import java.util.Calendar;
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
/**
 * @author Joshua Hernandez
 * @author John Lavin
 */
public class AlbumviewerController {
    @FXML
    private GridPane gridPane;

    @FXML
    private Button AddphotoButton, DeletePhotoButton, EditphotoButton;

    @FXML
    private Button BacktoalbumButton, LogoutButton;

    @FXML
    private ImageView AlbumImage, CoverPhoto;

    @FXML
    private Text DateText, NumberofphotoText, NewestphotoText, OldestPhotoText, AlbumNameText, UsernameText;

    @FXML
    private ImageView SelectedImage;

    @FXML
    private Text PhotodateText;

    @FXML
    private Button MovetoButton, CopytoButton, RenameButton;

    @FXML
    private Label CaptionLabel, TagLabel;

    @FXML
    private TextField SearchDateTextField, SearchTagTextField;
    private ArrayList<AnchorPane> photoSquares = new ArrayList<AnchorPane>();

    @FXML
    private ListView<String> photolistview;
    private ObservableList<String> names;

    @FXML
    private ListView<Photo> lvPhotos;
    private ObservableList<Photo> obsListPhotos;

    private ObservableList<Photo> obsList;

    private Photo photo;
    private String album;
    ArrayList<User> users;
    ListView<Photo> photos;
    private ObservableList<String> obsTags;
    private ObservableList<String> obsValues;
    private ListView<Tag> tags;
    private Album selectedAlbum;
    private Photo selectedPhoto;
    private User user;

    /**
     * start Gets the user corresponding photos and album data to display it
     * @param users , user,photos,a
     * @throws FileNotFoundException
     */

    public void start(ArrayList<User> users, User user, ListView<Photo> photos, Album a) throws FileNotFoundException {
        this.users = users;
        this.photos = photos;
        this.user = user;
        this.selectedAlbum = a;
        //InputStream stream = new FileInputStream("Photos62/data/stockuser/Stock1.png");
        //System.out.println(selectedAlbum.getNewestPhoto().getImgsrc());

        lvPhotos.setItems(FXCollections.observableArrayList(selectedAlbum.getPhotos()));
        // set listener for the items
        lvPhotos.getSelectionModel().selectedIndexProperty().addListener((obsList, oldVal, newVal) -> showItem());

        /*
        try {
            populatePhotogridPane();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        update( 0);


    }

    /**
     * Method to put the names of the photos in the listview
     */
    private void showItem() {

        //get the song from the obsList
        int index = lvPhotos.getSelectionModel().getSelectedIndex();
        selectedPhoto = lvPhotos.getSelectionModel().getSelectedItem();

        //update
        try {
            update(1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the album for any changes
     * @param i
     * @throws FileNotFoundException
     */

    public void update(int i) throws FileNotFoundException {

        //populate list view of photos
        //populatePhotosListView();

        AlbumNameText.setText(selectedAlbum.getName());
        UsernameText.setText(user.getUsername());

        if (i == 0) {
            selectedPhoto = selectedAlbum.getPhotos().get(i);
        } else {
            try {
                lvPhotos.setItems(FXCollections.observableArrayList(selectedAlbum.getPhotos()));
            } catch (Exception e) {

            }
        }
        InputStream stream = new FileInputStream(selectedPhoto.getImgsrc());
        Image image = new Image(stream);
        SelectedImage.setImage(image);
        //Setting the image view parameters

        SelectedImage.setFitWidth(210);
        SelectedImage.setPreserveRatio(true);
        if (i == 0) {
            PhotodateText.setText(PhotodateText.getText() + selectedPhoto.getDate());
            CaptionLabel.setText(CaptionLabel.getText() + selectedPhoto.getCaption());
            ArrayList<Tag> selectedTags = selectedPhoto.getTags();
            String t = TagLabel.getText();
            for (Tag curr : selectedTags) {
                t = t + " " + curr.getTag();
            }
        } else {
            PhotodateText.setText("" + selectedPhoto.getDate());
            CaptionLabel.setText("Caption: " + selectedPhoto.getCaption());
            ArrayList<Tag> selectedTags = selectedPhoto.getTags();
            String t = "Tags:";
            for (Tag curr : selectedTags) {
                t = t + " " + curr.getTag();
            }
        }

        //  Photo selectedPhoto = photos.getSelectionModel().getSelectedItem();

        //cover photo for album
        InputStream stream2 = new FileInputStream(selectedAlbum.getNewestPhoto().getImgsrc());
        Image cover = new Image(stream2);
        AlbumImage.setImage(cover);
        AlbumImage.setFitWidth(210);
        AlbumImage.setPreserveRatio(true);

        if (i == 0) {
            String recent = NewestphotoText.getText() + selectedAlbum.getLatestDate();
            NewestphotoText.setText(recent);
            String oldest = OldestPhotoText.getText() + selectedAlbum.getEarliestDate();
            OldestPhotoText.setText(oldest);
        } else {
            String recent = "Newest Photo: " + selectedAlbum.getLatestDate();
            NewestphotoText.setText(recent);
            String oldest = "Oldest Photo: " + selectedAlbum.getEarliestDate();
            OldestPhotoText.setText(oldest);
        }
        String num = "Number of photos: " + selectedAlbum.getPhotoCount();
        NumberofphotoText.setText(num);





    }

    /**
     * Called when users presses "LogOut" Button
     * Switches the scene back to the login screen
     * @param event
     */

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

    /**
     * Called when users presses "back to album" Button
     * Switches the scene back to the User controller
     * @param event
     */


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

    /**
     * Called when users presses "copy to" Button
     * Calls a dialog that will prompt the user to enter the new location of the photo
     * @param event
     */

    public void convertCopyTo(ActionEvent event) {

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

    /**
     * Called when users presses "Delete Photo" Button
     * Delete the photo fromt the album data.txt
     * @param event
     */

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
            update(1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when users presses "edit photo" button
     * Changes the name of the photo
     * @param event
     */

    public void EditPhotoFunction(ActionEvent event) {
        openImageViewer(event);
    }

    /**
     * loads the Fxml file for imageviewer
     *
     * @param event
     */

    public void openImageViewer(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Imageviewer2.fxml"));
            Parent parent = (Parent) loader.load();
            ImageviewerController controller = loader.<ImageviewerController>getController();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ScrollPane scroll = new ScrollPane(parent);
            Scene scene = new Scene(scroll);
            controller.start(user, users, selectedAlbum, selectedPhoto);
            stage.setScene(scene);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Called when users presses "add photo" button
     * adds photo in the album
     * @param event
     */

    public void addPhotoFunction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.png*", "*.jpg", "*.jpeg", "*.gif", "*.bmp"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile == null) {
            return;
        }
        //create new photo object
        Photo p = new Photo(selectedFile.getName(), Calendar.getInstance(),selectedFile.getAbsolutePath());
        //loop through photos in album to make sure that photo isn't already there
        ArrayList<Photo> pics = selectedAlbum.getPhotos();
        for (Photo curr: pics) {
            if (p.equals(curr)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(
                        "That photo source is already in the library.");

                String content = "Please select another photo";

                alert.setContentText(content);
                alert.showAndWait();
                return;
            }
        }
        selectedAlbum.addPhoto(p);
        selectedPhoto = p;

        try {
            update(1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        DataSaving.saveData(users);
    }

    /**
     * Method to switch the scene to search through photos in the album
     * @param event
     */
    public void switchtoSearch(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SearchedPhotos.fxml"));
            Parent parent = (Parent) loader.load();
            SearchPhotoController controller = loader.<SearchPhotoController>getController();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ScrollPane scroll = new ScrollPane(parent);
            Scene scene = new Scene(scroll);
            controller.start(users, user, photos, selectedAlbum);
            stage.setScene(scene);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
