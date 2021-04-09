package controller;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Photo;
import model.User;
import model.Album;
import photos.Listener;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import photos.Listener;

public class UserController {
    @FXML
    private Button AddAlbumAlbum, DeleteAlbumButton,EditAlbumButton;

    @FXML
    private Button AblumButton,LogoutButton, QuitButton;
    @FXML
    private ListView<Album> albums;
    private ArrayList<User> users;
    private User user;

    private List<Photo> selectedphoto = new ArrayList<>();
    private Image image;
    private Listener myListener;
    private Label Username;




    private void click(MouseEvent mouseEvent) {
        Listener.onClickListener();
    }

        public void setData(Album photo, Listener Listener) {

    }
    public void handleLogOutButton(ActionEvent event) {

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

    public void start(User user, ArrayList<User> users) {


    }



}
