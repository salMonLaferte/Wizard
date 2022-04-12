package com.DavidDiaz.Wizard;



import java.util.Iterator;
import java.util.Optional;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Separator;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class App extends Application {

    static Scene scene;
    static Stage mainWindow;
    static int cardWidth = 100;
    static int cardHeight = 150;
    static float  cardScale =.7f;
    
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
        scene = new Scene(root, Color.BLACK);
        mainWindow = new Stage();
        Image icon = new Image("resources/boo.png");
        mainWindow.setTitle("Wizard by David Diaz");
        mainWindow.setScene(scene);
        mainWindow.getIcons().add(icon);
        mainWindow.setWidth(1600);
        mainWindow.setHeight(900);
        mainWindow.show();

        //Set Game
        GameManager.StartAGame(6);

        //Draw
        drawEverything();

    }

    /**
     * Draw a card in the position x, y and add it to the specified root
     * @param card
     * @param root
     * @param x
     * @param y
     */
    public static void drawCard(Card card, Group root,  float x, float y, float scale){
        Group imgGroup = new Group();
        Image img = new Image(card.getImage());
        ImageView imgView = new ImageView(img);
        imgGroup.getChildren().add(imgView);
        //Set the number of the card if is regular or set to B or W if is a special card
        String number;
        if(card instanceof DumbCard || card instanceof WizardCard){
            if(card instanceof DumbCard)
                number = "S";
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
     */
    public static void drawEverything(){
        
        Group root = new Group();

        //Draw players data
        for(int i=0; i<GameManager.currentGame.numberOfPlayers; i++){
            Iterator<Card> it = GameManager.currentGame.players[i].hand.begin();
            int k=0;
            //Draw playe info
            Text text = new Text("Cartas de " + GameManager.currentGame.players[i].name);
            root.getChildren().add(text);
            text.setTranslateX(10);
            text.setTranslateY(50 + i*(cardHeight*cardScale));
            text.setFont(Font.font(15));
            text.setFill(Color.WHITE);
            
            //Draw player cards
            while(it.hasNext()){
                drawCard(it.next(),root, 150 + k*(cardWidth*cardScale), i*(cardHeight*cardScale), cardScale);
                k++;
            }
        }

        //Draw playedCards
        Group playedCards = new Group();
        Iterator<MyPair<Player, Card>> it = GameManager.cardsPlayed.begin();
        int k=0;
        int separation = 30;
        while(it.hasNext()){
            //Draw who played the card
            MyPair<Player,Card> data = it.next();
            drawCard(data.second, playedCards, k*(cardWidth*cardScale + separation) , 0 , cardScale);
            Text playerName = new Text(data.first.name);
            //Draw the card
            playedCards.getChildren().add(playerName);
            playerName.setTranslateX(k*(cardWidth*cardScale + separation) );
            playerName.setFont(Font.font(15));
            playerName.setFill(Color.WHITE);
            k++;
        }

        //Add played Cards to the root and translate
        root.getChildren().add(playedCards);
        playedCards.setTranslateX(3*(cardWidth*cardScale));
        playedCards.setTranslateY((GameManager.currentGame.numberOfPlayers+1)*(cardHeight*cardScale));

        //Update Window
        scene = new Scene(root, Color.BLACK);
        mainWindow.setScene(scene);
        mainWindow.show();
        
    }

    /**
     * Ask for user input
     * @param question
     * @param formatDescription
     * @param defaultValue
     * @return
     */
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

    /**
     * Show relevant info to the user
     * @param title
     * @param message
     */
    public static void showMessageToUser(String title, String message){
        Alert alert = new Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}
