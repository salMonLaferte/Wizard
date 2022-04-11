package com.DavidDiaz.Wizard;

class GameManager {
    static Round currentRound;
    static Game currentGame;
    static SimpleLinkedList<Card> cardsPlayed;
    static int lastPlayerWhoSuffled = 0;

    static void StartAGame(int numberOfPlayers){
        currentGame = new Game(numberOfPlayers);
        currentRound = new Round(1);
        cardsPlayed = new SimpleLinkedList<Card>();
        currentRound.roundStart();
    }

    static void StartNextRound(int numberOfRound){
        currentRound = new Round(numberOfRound);
        currentRound.roundStart();
    }

    static boolean validateCard(Player p, Card card){
        cardsPlayed.add(card);
        if(cardsPlayed.getSize() == currentGame.numberOfPlayers){
            getTrickWinner();
        }
        return true;
    }

    static int getTrickWinner(){
        cardsPlayed.clear();
        return 1;
    }

}
