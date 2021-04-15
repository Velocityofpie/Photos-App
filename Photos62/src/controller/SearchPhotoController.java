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
import model.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

/**
 * @author Joshua Hernandez
 * @author John Lavin
 */
public class SearchPhotoController {

    @FXML
    private Button SearchButton1;
    @FXML
    private TextField txtValue;
    @FXML
    private ChoiceBox dropdownTag;
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
    private ArrayList<Tag> tags = new ArrayList<Tag>();

    /**
     * Start method called to launch this scene
     * @param users arraylist of users
     * @param user user currently logged in
     * @param photos listview of photo objects from the album
     * @param a the currently selected album
     */
    public void start(ArrayList<User> users, User user, ListView<Photo> photos, Album a) {
        this.users = users;
        this.user = user;
        this.selectedAlbum = a;
        this.photoListView = photos;

        //make a list of all unique tags and add them to the dropdown
        //populate the tags list
        for (Photo curr: selectedAlbum.getPhotos()) {
            ArrayList<Tag> t = curr.getTags();
            for (int i = 0; i < t.size(); i++) {
                if (tags.contains(t.get(i))) {
                    continue;
                } else {
                    tags.add(t.get(i));
                }
            }
        }
        //add them to the drop down
        for (Tag curr: tags) {
            dropdownTag.getItems().add(curr.getTagName());
        }
    }

    /**
     * Method called when addPhotoButton is pressed
     * @param event
     */
    public void addPhotoFunction(ActionEvent event) {
    }

    /**
     * method called when deletePhotoButton is called
     * @param event
     */
    public void deletePhotoFromAlbum(ActionEvent event) {
    }

    /**
     * Method called when editphotobutton is called
     * @param event
     */
    public void EditPhotoFunction(ActionEvent event) {
    }

    /**
     * Method to switch scene back to albumviewer
     * @param event
     */
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

    /**
     * Method to switch scene back to the login
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
        DataSaving.saveData(users);
    }

    /**
     * Method to search for photos in a specified date range
     * @param event
     */
    public void searchDate(ActionEvent event) {
        //reset in range
        inRange.clear();

        //get beginning date and ending date
        LocalDate begRange = StartDate.getValue();
        LocalDate endRange = EndDate.getValue();

        if (begRange == null || endRange == null) {
            return;
        }

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

    /**
     * Method to create a new album from the photos in the listview
     * @param event
     */
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

    /**
     * Method to find photos with the satisfying tag name and values
     * @param event
     */
    public void searchTag(ActionEvent event) {
        //clear in range
        inRange.clear();

        String str = (String) dropdownTag.getValue();
        String targetVal = txtValue.getText();

        if (str.equals("") || targetVal.equals("")) {
            return;
        }

        //loop through all the photos in the album and see if tag values match
        ArrayList<Photo> pho = selectedAlbum.getPhotos();
        for (Photo curr: pho) {
            ArrayList<Tag> tgs = curr.getTags();
            for (Tag t: tgs) {
                if (t.getTagName().equals(str) && t.getTagValue().equals(targetVal)) {
                    inRange.add(curr);
                    break;
                }
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
}
