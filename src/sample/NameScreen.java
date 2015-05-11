package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;


public class NameScreen extends VBox {
    private TextField name;
    private String nameInput;
    private TextArea textArea;


    NameScreen(TextArea textArea, String label, Boolean zip) {
        this.textArea = textArea;
        File style = new File("style.css");
        getStylesheets().add(style.toURI().toString());
        getStyleClass().add("background");

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(10);
        getChildren().add(vBox);
        HBox hBox = new HBox();
        vBox.getChildren().add(hBox);
        Label newLabel = new Label();
        newLabel.getStyleClass().add("label");
        newLabel.setText(label);
        hBox.getChildren().add(newLabel);
        HBox text = new HBox();
        vBox.getChildren().add(text);
        name = new TextField();
        name.setEditable(true);
        name.getStyleClass().add("text-field");
        text.getChildren().add(name);
        HBox button = new HBox();
        vBox.getChildren().add(button);
        Button ok = new Button();
        ok.setText("OK");
        ok.getStyleClass().add("button-ok");
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


                if (zip) {
                    try {
                        new FileConvFromZip().unpackDir(returnName(), textArea);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    new FileConvToZip().packingDir(returnName(), textArea);
                }
            }


        });

        button.getChildren().add(ok);

    }

    public String returnName() {
        if (name.getText() != null) {
            getScene().getWindow().hide();
            return name.getText();
        } else return null;

    }

    public String getNameInput() {
        return nameInput;
    }

    public void setNameInput(String nameInput) {
        this.nameInput = name.getText();
    }


}
