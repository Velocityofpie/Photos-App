package model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.ListCell;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Photo;

public class Imageloader extends ListCell<Photo> {
    AnchorPane anchorPane = new AnchorPane();
    StackPane stackPane = new StackPane();
    ImageView imageView = new ImageView();

    public Imageloader() {
        super();
        imageView.setFitWidth(60.0);
        imageView.setFitHeight(60.0);
        imageView.setPreserveRatio(true);
        StackPane.setAlignment(imageView, Pos.CENTER);
        stackPane.getChildren().add(imageView);
        stackPane.setPrefHeight(55.0);
        stackPane.setPrefWidth(45.0);
        setGraphic(anchorPane);
    }
}
