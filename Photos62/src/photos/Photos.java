package photos;

import controller.AdminController;
import controller.LoginController;
import javafx.event.ActionEvent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.AnchorPane;

/**
 * @author Joshua Hernandez
 * @author John Lavin
 */
public class Photos extends Application {

    /**
     * start function to set the stage and scene for the application to run
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/Login.fxml"));


            AnchorPane root = (AnchorPane) loader.load();
            primaryStage.setTitle("Photos");
            LoginController controller = loader.getController();
            controller.start(primaryStage);
            primaryStage.setResizable(false);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Main method to launch the application
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
