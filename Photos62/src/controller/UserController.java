package controller;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Photo;
import model.Tag;
import model.User;
import model.Album;
import photos.Listener;
import javafx.scene.input.MouseEvent;
import model.photoloader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    private ListView<Album> AlbumListview;

    ListView<Photo> photos;
    private ArrayList<User> users;
    private User user;
    private Album selectedAlbum;

    private ListView<Photo> SelectedPhoto;
    private Image image;
    private Listener myListener;
    private Label Username;
    private ListView<Tag> tags;


    private void click(MouseEvent mouseEvent) {
        Listener.onClickListener();
    }

        public void setData(Album photo, Listener Listener) {

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

    }

    public void start( User user) {
        System.out.println("UserController opened");
        this.user = user;

        AlbumListview.setItems(FXCollections.observableArrayList(user.getAlbums()));
        Album selectedAlbum = AlbumListview.getSelectionModel().getSelectedItem();

        /*
        photos.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>() {
            @Override
            public ListCell<Photo> call(ListView<Photo> photoList) {
                return new photoloader();
            }
        });

        photos.setItems(FXCollections.observableArrayList(selectedAlbum.getPhotos()));
        photos.getSelectionModel().select(0);


         */

        ArrayList<String> albumnames = new ArrayList<String>();
        albumnames.add(0, " ");
        ArrayList<Album> allalbums = user.getAlbums();
        for (Album curralbum : allalbums) {
            albumnames.add(curralbum.getName());
        }





    }



}
