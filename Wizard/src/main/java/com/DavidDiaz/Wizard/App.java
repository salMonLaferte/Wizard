package com.DavidDiaz.Wizard;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class App extends Application {
    public static void main(String args[]){
        launch();
        System.out.println("Hello world");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, Color.BLACK);
        Stage mainWindow = new Stage();
        Image icon = new Image("resources/boo.png");
        mainWindow.setTitle("Wizard by David Diaz");
        mainWindow.setScene(scene);
        mainWindow.getIcons().add(icon);
        drawCard(new RegularCard(3,14), root);
        mainWindow.show();
    }

    public void drawCard(Card card, Group root){
        Image img = new Image(card.getImage());
        ImageView imgView = new ImageView(img);
        root.getChildren().add(imgView);
        if(card instanceof DumbCard){
            return;
        }
        if(card instanceof WizardCard){
            return;
        }
    }
    
}
