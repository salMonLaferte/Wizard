package com.DavidDiaz.Wizard;

import javafx.event.ActionEvent;

class Player {
    protected String name;
    protected int score;
    protected int currentGuess;
    protected SimpleLinkedList<Card> hand;
    
    public Player( String name ){
        this.name = name;
        score = 0;
        currentGuess = 0;
        hand = new SimpleLinkedList<>();
    }
    
    public void playCard(ActionEvent event){
        
    }

    public boolean setGuess(int currentGuess){
        return false;
    }

    public String askForInput(String Input){
        return "";
    }
}
