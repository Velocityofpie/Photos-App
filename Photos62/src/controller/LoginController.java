package controller;
import java.io.*;
import java.util.Calendar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import helper.User;
import helper.Album;

public class LoginController {

    @FXML private Button loginButton;
    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;


    private final String path = "data/data.txt";
    Boolean validUser = false;

    public void start(Stage stage) {

    }


    public void LoginButton(ActionEvent event) throws IOException {
        String username = usernameTextField.getText();

        // Check for valid file. If file doesn't exist, create it and add admin
        // and stock users
        File data = new File(path);



        // File exists, proceed to read it
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            users = (ArrayList<User>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();

            User user = null;



            if (username.equals("admin") || user != null) {
                int sss;
            }
            else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText("User not found.");
                alert.setContentText("This user does not exist.");

                alert.showAndWait();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
