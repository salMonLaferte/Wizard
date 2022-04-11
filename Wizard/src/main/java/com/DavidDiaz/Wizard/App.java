package com.DavidDiaz.Wizard;



import java.util.Iterator;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        Group root = new Group();
        Scene scene = new Scene(root, Color.BLACK);
        Stage mainWindow = new Stage();
        Image icon = new Image("resources/boo.png");
        mainWindow.setTitle("Wizard by David Diaz");
        mainWindow.setScene(scene);
        mainWindow.getIcons().add(icon);
        /*Stack<Card> deck = new Stack<>();
        for(int i=0; i< 13; i++){
            deck.push( new RegularCard(1, i) );
        }
        int i=0;
        while(!deck.isEmpty()){
            drawCard(deck.top(), root, i*100, 0);
            deck.pop();
            i++;
        }*/

        Game game = new Game(6);
        GameManager.currentGame = game;
        Round r = new Round(6);
        GameManager.currentRound = r;
        GameManager.currentRound.OnRoundStart();
        

        /*for(int i=0; i< GameManager.currentGame.numberOfPlayers; i++){

        }*/
        drawEverything(root);
        mainWindow.show();
    }

    
    public void drawCard(Card card, Group root, int x, int y){
        Group imgGroup = new Group();
        Image img = new Image(card.getImage());
        ImageView imgView = new ImageView(img);
        imgGroup.getChildren().add(imgView);
        if(card instanceof DumbCard || card instanceof WizardCard){
            return;
        }
        Rectangle r = new Rectangle(40, 40, Color.BLACK);
        imgGroup.getChildren().add(r);
        r.setX(img.getWidth() -40);
        r.setY(img.getHeight()-40);
        RegularCard c = (RegularCard)card;
        String number = "" + c.number;
        Text text = new Text( number );
        text.setFill(Color.WHITE);
        imgGroup.getChildren().add(text);
        text.setX(img.getWidth() -40);
        text.setY(img.getHeight()-5 );
        text.setFont(Font.font(40));
        imgGroup.setTranslateX(x);
        imgGroup.setTranslateY(y);
        root.getChildren().add(imgGroup);
    }

    public void drawEverything(Group root){
        for(int i=0; i<GameManager.currentGame.numberOfPlayers; i++){
            Iterator<Card> it = GameManager.currentGame.players[i].hand.begin();
            int k=0;
            System.out.println(GameManager.currentGame.players[i].hand.getSize());
            while(it.hasNext()){
                drawCard(it.next(), root, k*100, i*150);
                k++;
            }
        }
    }
    
}
