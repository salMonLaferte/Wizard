package com.DavidDiaz.Wizard;

class GameManager {
    static Round currentRound;
    static Game currentGame;
    static SimpleLinkedList<Card> cardsPlayed;
    static int lastPlayerWhoSuffled = 0;

    static void StartAGame(int numberOfPlayers){
        currentGame = new Game(numberOfPlayers);
        currentRound = new Round(0);
        currentRound.roundStart();
    }

    static void playCard(Player p, Card card){
        cardsPlayed.add(card);
        if(cardsPlayed.getSize() == currentGame.numberOfPlayers){
            getTrickWinner();
        }
    }

    static void getTrickWinner(){
        cardsPlayed.clear();

    }

}
