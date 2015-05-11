package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        MainScreen screen = new MainScreen(false);

        Scene scene = new Scene(screen, 430, 270);

        primaryStage.getIcons().add(new Image("file:zip.png"));

        primaryStage.setTitle("Zip Packer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
