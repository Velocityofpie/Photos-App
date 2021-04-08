package controller;
import java.io.*;
import java.io.FileInputStream;
import javafx.event.ActionEvent;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
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

        try {
            writeUsers(null, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeUsers (User u, int i) throws IOException {
        FileOutputStream file = new FileOutputStream("Photos62/data/data.txt");
        ObjectOutputStream output = new ObjectOutputStream(file);
        if (i == 0) {
            User a = new User("admin", "admin");
            output.writeObject(a);
        } else {
            output.writeObject(u);
        }



        output.close();
        file.close();
    }

    public User readUsers() throws IOException, ClassNotFoundException {

        FileInputStream fis = new FileInputStream("Photos62/data/data.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);

        User w = (User) ois.readObject();

        return w;
    }


    public void handleLoginButton (ActionEvent event) throws IOException {
//        testing inputs
//        System.out.println(loginButton.getText());
//        System.out.println(signupButton.getText());
//        System.out.println(usernameTextField.getText());
//        System.out.println(passwordTextField.getText());
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        User x = null;
        try {
            x = readUsers();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(x);


        try {



            /*
            boolean cont = true;
            while (cont) {
                User u = (User) ois.readObject();
                if (u != null) {
                    users.add(u);
                } else {
                    cont = false;
                }
            }

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

             */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
