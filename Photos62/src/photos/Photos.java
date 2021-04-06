package photos;

import controller.AdminController;
import controller.LoginController;
import javafx.event.ActionEvent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;


public class Photos extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/Login.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Photos");
            primaryStage.show();
            LoginController controller = loader.getController();
            controller.start(primaryStage);



        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        launch(args);
    }
}
