package controller;
import java.io.*;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import javafx.event.ActionEvent;
import java.io.ObjectInputStream;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;




public class LoginController {

    @FXML private Button loginButton,signupButton ;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordTextField;


    ArrayList<User> users;
    Boolean validUser = false;

    public void start(Stage stage) {

    }

    public void handleLoginButton (ActionEvent event) throws IOException {
//        testing inputs
//        System.out.println(loginButton.getText());
//        System.out.println(signupButton.getText());
//        System.out.println(usernameTextField.getText());
//        System.out.println(passwordTextField.getText());
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        try {

            FileInputStream fis = new FileInputStream("Photos62/data/data.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            users = (ArrayList<User>) ois.readObject();
            ois.close();
            fis.close();

            User user = null;
            for (User currentUser : users) {
                if (currentUser.getUsername().equals(username)) {
                    user = currentUser;
                }
            }


            if (username.equals("admin") || user != null   ) {
                FXMLLoader loader;
                Parent parent;
                if ( (username.equals("admin")) ) {
                    loader = new FXMLLoader(getClass().getResource("/view/Admin.fxml"));
                    parent = (Parent) loader.load();
                    AdminController controller = loader.<AdminController>getController();
                    Scene scene = new Scene(parent);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    controller.start(users);
                    stage.setScene(scene);
                    stage.show();
                }else {
                    loader = new FXMLLoader(getClass().getResource("/view/Interface.fxml"));
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
