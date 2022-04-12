package com.DavidDiaz.Wizard;

import java.util.Iterator;

class GameManager {
    static Round currentRound;
    static Game currentGame;
    static SimpleLinkedList<MyPair<Player, Card>> cardsPlayed;
    static int lastPlayerWhoSuffled = 0;

    static void StartAGame(int numberOfPlayers){
        currentGame = new Game(numberOfPlayers);
        currentRound = new Round(1);
        cardsPlayed = new SimpleLinkedList<MyPair<Player, Card>>();
        currentRound.roundStart();
    }

    static void StartNextRound(int numberOfRound){
        currentRound = new Round(numberOfRound);
        currentRound.roundStart();
    }

    static int getTrickWinner(){
        Iterator<MyPair<Player, Card>> it = cardsPlayed.begin();
        String winnerName = "";
        //Look for first Wizard
        while(it.hasNext()){
            MyPair<Player, Card> pair = it.next();
            if(pair.second instanceof WizardCard){
                winnerName = pair.first.getName();
                pair.first.RoundWin();
            }

        }
        cardsPlayed.clear();
        App.drawEverything();
        return 1;
    }

    static void playCard(Player p, Card c){
        MyPair<Player,Card> pair = new MyPair<Player,Card>(p, c);
        cardsPlayed.add(pair);
        App.drawEverything();
    }


}
