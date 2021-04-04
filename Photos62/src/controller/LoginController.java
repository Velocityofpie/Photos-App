package controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameTextField;

    private final String path = "data/data.dat";
    Boolean validUser = false;

    public void start(Stage stage) {

    }

    public void handleLoginButton(ActionEvent event) {

        //String username = usernameField.getText();

        // Check for valid file. If file doesn't exist, create it and add admin
        // and stock users
        File data = new File(path);



        // File exists, proceed to read it
        try {

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
