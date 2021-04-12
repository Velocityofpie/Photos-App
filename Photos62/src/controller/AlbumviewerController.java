package controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
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
import model.User;

public class AlbumviewerController {
    @FXML private Button AddphotoButton, DeletePhotoButton,EditphotoButton;

    @FXML
    private Button BacktoalbumButton,LogoutButton;

    @FXML
    private ImageView AlbumImage;

    @FXML
    private Text DateText,NumberofphotoText,NewestphotoText;

    @FXML
    private Text OldestPhotoText;

    @FXML
    private Button RenameButton;

    @FXML
    private ImageView SelectedImage;

    @FXML
    private Text PhotodateText;

    @FXML
    private Button MovetoButton;

    @FXML
    private Button CopytoButton;

    @FXML
    private Label CaptionLabel;

    @FXML
    private Label TagLabel;

    @FXML
    private TextField SearchDateTextField;

    @FXML
    private TextField SearchTagTextField;


}
