package com.DavidDiaz.Wizard;

import java.util.Iterator;

class TrickData {
    private int leaderFigure;
    private int numberOfTrick;
    private SimpleLinkedList<MyPair<Player,Card>> playedCards;
    private Player winner;

    public TrickData(int leaderFigure, SimpleLinkedList<MyPair<Player,Card>> playedCards, Player winner, int numberOfTrick){
        this.leaderFigure = leaderFigure;
        this.playedCards = playedCards;
        this.winner = winner;
        this.numberOfTrick = numberOfTrick;
    }
    
    public int getLeaderFigure(){
        return leaderFigure;
    }

    public Player getWinner(){
        return winner;
    }

    public SimpleLinkedList<MyPair<Player,Card>> getPlayedCards(){
        return playedCards;
    }

    @Override
    public String toString(){
        String result = "Truco " + numberOfTrick;
        result += "Palo lider: " + RegularCard.getFigureName(leaderFigure) + "\n";
        Iterator<MyPair<Player, Card>> it = playedCards.begin();
        while(it.hasNext()){
            MyPair<Player, Card> playerCard = it.next();
            result += playerCard.first.getName() + " jugó: " + playerCard.second.toString() + "\n";
        }
        result += "Ganador del truco: " + winner.getName() + "\n";
        return result;
    }

}
