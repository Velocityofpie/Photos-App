package controller;

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
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;

/**
 * @author Joshua Hernandez
 * @author John Lavin
 */
public class AdminController {

    @FXML
    Button btnAdd, btnEdit, btnDelete;
    @FXML
    TextField txtUsername;

    @FXML
    ListView<User> lvUsers; //list of users
    private ObservableList<User> obsList; //observable list that stores strings for everything in listview
    private ArrayList<User> alUser;

    /**
     * start method to initialize the list of users and create listview
     * @param users
     */
    public void start(ArrayList<User> users) {

        alUser = users;
        obsList = FXCollections.observableArrayList(users);
        lvUsers.setItems(obsList);

        // set listener for the items
        lvUsers.getSelectionModel().selectedIndexProperty().addListener((obsList, oldVal, newVal) -> showItem());
    }


    /**
     * Method to put the values of the selected item into the textfields
     */
    private void showItem() {

        //get the song from the obsList
        int index = lvUsers.getSelectionModel().getSelectedIndex();
        User s = obsList.get(index);

        //put necessary values in the text field
        txtUsername.setText(s.getUsername());

    }

    /**
     * Called when users presses LogOut Button
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
     * Method is called when user presses the add/edit/delete buttons and updates the listview accordingly
     * @param e
     * @throws IOException
     */
    public void convert (ActionEvent e) throws IOException {

        Button b = (Button) e.getSource();
        if (b == btnAdd) {
            String addUsername = txtUsername.getText();
            User addThisUser = new User(addUsername, "");
            add(addThisUser);
        } else if (b == btnDelete) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Need Confirmation");
            alert.setContentText("Are you ok with this?");
            int index = lvUsers.getSelectionModel().getSelectedIndex();

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                delete(index);
            } else {
                // ... user chose CANCEL or closed the dialog
            }
        } else if (b == btnEdit) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Need Confirmation");
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                edit();
            } else {
                // ... user chose CANCEL or closed the dialog
            }
        }
    }

    /**
     *
     * @param u list of users
     * @param i integer representing a particular state
     * @throws IOException
     */
    public void storeUsers (ArrayList<User> u, int i) throws IOException {
        FileOutputStream file = new FileOutputStream("Photos62/data/data.txt");
        ObjectOutputStream output = new ObjectOutputStream(file);
        if (i == 0) {
            User a = new User("admin", "admin");
            output.writeObject(a);
            output.writeObject(new User("stock", "stock"));
        } else {
            for (User curr: u) {
                output.writeObject(curr);
            }
        }



        output.close();
        file.close();
    }


    /**
     * Method to add a user to the list of users, given that the username is not taken
     * @param u user
     * @throws IOException
     */
    private void add(User u) throws IOException {

        String name = txtUsername.getText();

        int c = checkDuplicateName(u);
        if (c <0) {
            return;
        }

        obsList.add(u);
        alUser.add(u);
        lvUsers.setItems(obsList);

        //store data
        storeUsers(alUser, 1);
    }

    /**
     * Method to edit the name of user
     * @throws IOException
     */
    private void edit() throws IOException {
        int index = lvUsers.getSelectionModel().getSelectedIndex();
        User selectedUser = lvUsers.getSelectionModel().getSelectedItem();
        User editedUser = new User(txtUsername.getText(), "");
        //findspot
        //if it returns -1 then that user already exists
        int i = checkDuplicateName(editedUser);
        if (i < 0) {
            duplicateUserAlert();
            return;
        }

        delete(index);
        add(editedUser);

    }

    /**
     * Method to delete a user from the list at a given index
     * @param index integer location
     * @throws IOException
     */
    private void delete(int index) throws IOException {

        obsList.remove(index);
        alUser.remove(index);

        int n = obsList.size();

        //if we deleted the last item, make sure the new last item is selected
        //if not, make sure the next item is selected, which is now at the removed index anyways
        if (index == n) {
            lvUsers.getSelectionModel().select(index-1);

        } else if (obsList.isEmpty()) {
            //don't select anything since the list is empty. wait for a new add to select
        } else {
            lvUsers.getSelectionModel().select(index);
        }
        //store data
        storeUsers(alUser, 1);
    }

    /**
     * helper method to check list of users for a duplicate name
     * @param u user to be checked
     * @return integer -1 if duplicate, 1 if no duplicate
     */
    private int checkDuplicateName(User u) {

        String name = u.getUsername();
        for (int i = 0; i<obsList.size(); i++) {
            User ui = obsList.get(i);
            if (name.equals(ui.getUsername())) {
                return -1;
            }
        }

        return 1;
    }

    /**
     * helper method to create an information alert box
     */
    private void duplicateUserAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(
                "That user is already in the library.");

        String content = "Please change the name of the user";

        alert.setContentText(content);
        alert.showAndWait();
    }
}
