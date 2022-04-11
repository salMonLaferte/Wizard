package com.DavidDiaz.Wizard;



import java.util.Iterator;
import java.util.Optional;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class App extends Application {

    public static void main(String args[]){
        launch();
    }

    /*EventHandler clickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Object source = event.getSource();
            if(source instanceof ImageView){
                ImageView imgview = (ImageView)source;
                Parent n = imgview.getParent();
            }
        }
        
    };*/

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Set Window
        Group root = new Group();
        Scene scene = new Scene(root, Color.BLACK);
        Stage mainWindow = new Stage();
        Image icon = new Image("resources/boo.png");
        mainWindow.setTitle("Wizard by David Diaz");
        mainWindow.setScene(scene);
        mainWindow.getIcons().add(icon);
        mainWindow.setWidth(1600);
        mainWindow.setHeight(900);

        //Set Game
        Game game = new Game(6);
        GameManager.currentGame = game;
        Round r = new Round(10);
        GameManager.currentRound = r;
        GameManager.currentRound.roundStart();
        //Draw
        drawEverything(root);
        mainWindow.show();
    }

    /**
     * Draw a card in the position x, y and add it to the specified root
     * @param card
     * @param root
     * @param x
     * @param y
     */
    public void drawCard(Card card, Group root, float x, float y, float scale){
        Group imgGroup = new Group();
        Image img = new Image(card.getImage());
        ImageView imgView = new ImageView(img);
        imgGroup.getChildren().add(imgView);
        //Set the number of the card if is regular or set to B or W if is a special card
        String number;
        if(card instanceof DumbCard || card instanceof WizardCard){
            if(card instanceof DumbCard)
                number = "B";
            else
                number = "W";
        }else{
            RegularCard c = (RegularCard)card;
            number = "" + c.number;
        }
        //Set a rectangle to indicate number of card
        Rectangle r = new Rectangle(40, 40, Color.BLACK);
        imgGroup.getChildren().add(r);
        r.setX(img.getWidth() -40);
        r.setY(img.getHeight()-40);
        
        //Set text with the number of the card
        Text text = new Text( number );
        text.setFill(Color.WHITE);
        imgGroup.getChildren().add(text);
        text.setX(img.getWidth() -40);
        text.setY(img.getHeight()-5 );
        text.setFont(Font.font(40));
        
        //Translate the card to the position and add it to the root
        imgGroup.setTranslateX(x);
        imgGroup.setTranslateY(y);
        root.getChildren().add(imgGroup);

        imgGroup.setScaleX(scale);
        imgGroup.setScaleY(scale);
    }

    /**
     * Draws everything in the window
     * @param root
     */
    public void drawEverything(Group root){

        for(int i=0; i<GameManager.currentGame.numberOfPlayers; i++){
            Iterator<Card> it = GameManager.currentGame.players[i].hand.begin();
            int k=0;
            System.out.println(GameManager.currentGame.players[i].hand.getSize());
            while(it.hasNext()){
                drawCard(it.next(), root, k*(100*.7f), i*(150*.7f), .7f);
                k++;
            }
        }
    }

    public static String askForUserInput(String question,String formatDescription, String defaultValue){
        TextInputDialog textInputDialog = new TextInputDialog(defaultValue);
        textInputDialog.setHeaderText(question);
        textInputDialog.setContentText(formatDescription);
        Optional<String> result = textInputDialog.showAndWait();
        if(result.isPresent()){
            return result.get();
        }
        else return "";
    }
    
}
