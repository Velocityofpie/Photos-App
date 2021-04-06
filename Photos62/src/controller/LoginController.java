package controller;
import java.io.*;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
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
import javafx.stage.Stage;
import model.User;


public class LoginController {

    @FXML private Button loginButton , signupButton ;
    @FXML private TextField usernameTextField, passwordTextField;


    ArrayList<User> users;
    private final String path = "/data/data.txt";
    Boolean validUser = false;

    public void start(Stage stage) {

    }


    public void LoginButton(ActionEvent event) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

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
            for (User currentUser : users) {
                if (currentUser.getUsername().equals(username)) {
                    user = currentUser;

                }
            }


            if (username.equals("admin") || user != null   ) {
                FXMLLoader loader;
                Parent parent;
                if ( (username.equals("admin")) || ( password.equals("admin")) ) {
                    loader = new FXMLLoader(getClass().getResource("/view/Admin.fxml"));
                    parent = (Parent) loader.load();
                    AdminController controller = loader.<AdminController>getController();
                    Scene scene = new Scene(parent);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    controller.start(users);
                    stage.setScene(scene);
                    stage.show();
                }else {
                    loader = new FXMLLoader(getClass().getResource("/view/interface.fxml"));
                    parent = (Parent) loader.load();
                    UserController controller = loader.<UserController>getController();
                    Scene scene = new Scene(parent);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    controller.start(user, users);
                    stage.setScene(scene);
                    stage.show();
                }

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
