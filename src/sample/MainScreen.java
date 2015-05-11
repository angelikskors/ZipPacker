package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;


import java.io.File;


public class MainScreen extends VBox {
    private VBox ui;
    final private  String CENTER="CENTER";
   // final private  String RIGHT="RIGHT";
    private TextArea textArea;
    private HBox image;
    private VBox uiBox;
    private boolean isOnProcess;
    NameScreen little;

    MainScreen() {
         uiBox = createUI();


    }
    MainScreen(boolean isOnProcess) {
        this();
         this.isOnProcess = isOnProcess;

        if(isOnProcess){
            addTextField();
            addImage(" ");

        }else{addImage(CENTER);}

    }

    private void addTextField() {
        textArea = new TextArea();
        textArea.setEditable(false);
       textArea.setFont(new Font("Times New Roman", 20));
      textArea.setPrefSize(300, 830);
        textArea.getStyleClass().add("text-area");

        image.getChildren().add(textArea);


    }

    private VBox createUI() {
        File style = new File("style.css");
        getStylesheets().add(style.toURI().toString());
        getStyleClass().add("background");
        ui = new VBox();
        ui.setPadding(new Insets(10, 10, 10, 10));
        getChildren().add(ui);
        HBox buttons = new HBox();
        buttons.setSpacing(10);
        ui.getChildren().add(buttons);
        Button bPack = new Button();
        bPack.setText("Pack");
        bPack.getStyleClass().add("button");
        buttons.getChildren().add(bPack);
        bPack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addTextField();
                createNameStage(textArea);
   }
        });
        Button unPack = new Button();
        unPack.setText("Unpack");
        buttons.getChildren().add(unPack);
        image =new HBox();
        image.setPadding(new Insets(10,10,10,10));
        image.setSpacing(10);
        ui.getChildren().add(image);

        return ui;
    }

    private void createNameStage(TextArea textArea) {
         little=new NameScreen(textArea);
        Stage newStage=new Stage();
        newStage.setTitle("INPUT");
        Scene scene = new Scene(little, 360, 140);
        newStage.setScene(scene);
        newStage.show();
    }

    private void addImage(String position){

        Image image1 = new Image("file:rar.png");
        ImageView imageView = new ImageView();
        imageView.setImage(image1);
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);

        if(position.equals(CENTER)){

           image.setAlignment(Pos.CENTER);
        }
        image.getChildren().add(imageView);


    }
    private void giveNameZip(){
        Stage newStage=new Stage();

    }

}
