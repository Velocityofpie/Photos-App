package controller;
import java.awt.*;
import java.io.*;
import java.io.FileInputStream;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Album;
import model.ImagesSerial;
import model.Photo;
import model.User;
import javafx.scene.control.ScrollPane;


public class LoginController {

    @FXML private Button loginButton,signupButton ;
    @FXML private TextField usernameTextField;
    private ListView<Album> albums;

    ArrayList<User> users;
    Boolean validUser = false;


    public void start(Stage stage) {
/*
        try {
            writeUsers(null, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }


 */

    }

    private void populate() {
        User a = new User("admin", "admin");
        User b = new User("stock", "stock");
        try {
            users.add(a);
            users.add(b);
        } catch (Exception e) {

        }

    }

    public void writeUsers (ArrayList<User> u, int i) throws IOException {
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

    public ArrayList<User> readUsers() throws IOException, ClassNotFoundException {

        ArrayList<User> out = new ArrayList<User>();
        File file = new File("Photos62/data/data.txt");
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);

        boolean cont = true;
        while (cont) {
            try {
                User curr = (User) ois.readObject();
                if (curr != null) {
                    out.add(curr);
                } else {
                    cont = false;
                }

            } catch (Exception e) {
                break;
            }
        }


        ois.close();
        fis.close();
        return out;
    }


    public void handleLoginButton (ActionEvent event) throws IOException {
//        testing inputs
//        System.out.println(loginButton.getText());
//        System.out.println(signupButton.getText());
//        System.out.println(usernameTextField.getText());
//        System.out.println(passwordTextField.getText());
        String username = usernameTextField.getText();


        try {
            users = readUsers();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for (User curr: users) {
            System.out.println(curr);
        }



        try {

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
                    ScrollPane scroll = new ScrollPane(parent);
                    Scene scene = new Scene(scroll);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    controller.start(users);
                    stage.setScene(scene);
                    stage.show();
                }else {

                    if (username.equals("stock")) {
                        if (!(user.albumNameExists("stock"))) {
                            user.addAlbum("stock");
                            System.out.println("added stock");
                            //populate the stock album
                            Photo p1 = new Photo("p1", Calendar.getInstance(), "Photos62/data/stockuser/Stock1.png");
                            Album stockAlbum = user.getAlbumByName("stock");
                            //int si = user.getAlbumIndexByAlbum(stockAlbum);
                            stockAlbum.addPhoto(p1);

                        }
                    }

                    loader = new FXMLLoader(getClass().getResource("/view/UserInterface.fxml"));
                    parent = (Parent) loader.load();
                    UserController controller = loader.<UserController>getController();
                    ScrollPane scroll = new ScrollPane(parent);

                    Scene scene = new Scene(scroll);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    controller.start(user);
                    stage.setScene(scene);
                    stage.show();
                }

            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
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
