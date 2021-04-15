package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Album;
import model.DataSaving;
import model.Photo;
import model.User;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

public class SearchPhotoController {

    @FXML
    private Button createNewAlbumButton;
    @FXML
    private ListView lvPhotos;
    @FXML
    private DatePicker StartDate, EndDate;
    @FXML
    private Button BacktoalbumButton, btnBackToAlbum;
    @FXML
    private Button LogoutButton;

    private ArrayList<User> users;
    private User user;
    private ListView<Photo> photoListView;
    private Album selectedAlbum;

    private ArrayList<Photo> inRange = new ArrayList<>();

    public void start(ArrayList<User> users, User user, ListView<Photo> photos, Album a) {
        this.users = users;
        this.user = user;
        this.selectedAlbum = a;
        this.photoListView = photos;

    }

    public void addPhotoFunction(ActionEvent event) {
    }

    public void deletePhotoFromAlbum(ActionEvent event) {
    }

    public void EditPhotoFunction(ActionEvent event) {
    }

    public void convertBacktoalbumButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AlbumSelected.fxml"));
            Parent parent = (Parent) loader.load();
            AlbumviewerController controller = loader.<AlbumviewerController>getController();
            ScrollPane scroll = new ScrollPane(parent);
            Scene scene = new Scene(scroll);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            controller.start(users,user, photoListView, selectedAlbum);
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
        DataSaving.saveData(users);
    }

    public void searchDate(ActionEvent event) {
        //reset in range
        inRange.clear();

        //get beginning date and ending date
        LocalDate begRange = StartDate.getValue();
        LocalDate endRange = EndDate.getValue();

        inRange = new ArrayList<>();
        //loop through all photos in the album and add photos that are in the range
        for (Photo curr : selectedAlbum.getPhotos()) {
            LocalDate localDate = curr.getLocalDate();
            if (localDate.isAfter(begRange) && localDate.isBefore(endRange)) {
                //add to list of results
                inRange.add(curr);
            }
        }

        if (inRange.size() > 0) {
            //update the list view
            lvPhotos.setItems(FXCollections.observableArrayList(inRange));
        } else {
            lvPhotos.setItems(null);
            //System.out.println("no matching results");
        }

    }

    public void createNewAlbumFunction(ActionEvent event) {

        if (inRange.size() == 0) {
            return;
        }

        //pop up asking for new album name
        TextInputDialog dialog = new TextInputDialog();
        dialog.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());
        dialog.setTitle("Create new album");
        dialog.setHeaderText("Input the new album name");
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
                    //create a new album with the name given
                    Album nAlbum = new Album(n);
                    user.addAlbum(nAlbum);

                    for (int i=0; i < inRange.size(); i++) {
                        Photo p = (Photo) lvPhotos.getItems().get(i);
                        nAlbum.addPhoto(p);
                    }

                    DataSaving.saveData(users);

                    //switch back to usercontroller scene
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



                    cont = false;
                }
            } else {
                cont = false;
            }
        }


    }
}
