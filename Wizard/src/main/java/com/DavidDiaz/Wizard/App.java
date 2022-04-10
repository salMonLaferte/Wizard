package com.DavidDiaz.Wizard;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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

        Game game = new Game(3);
        GameManager.currentGame = game;
        Round r = new Round(2);
        GameManager.currentRound = r;

        for(int i=0; i< GameManager.currentGame.numberOfPlayers; i++){
            
        }

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
    
}
