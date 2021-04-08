package controller;
import model.User;
import model.Album;
import photos.Listener;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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

public class UserController {
    @FXML private VBox chosenFruitCard;

    @FXML private Label fruitNameLable;

    @FXML private Label fruitPriceLabel;

    @FXML private ImageView fruitImg;

    @FXML private ScrollPane scroll;

    @FXML private GridPane grid;

    private void click(MouseEvent mouseEvent) {
        Listener.onClickListener();
    }

        public void setData(Album photo, Listener Listener) {

    }

    public void start(User user, ArrayList<User> users) {



    }

}
