package com.DavidDiaz.Wizard;

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
    
    /**
     * Set prediction for current player
     */
    public void setGuess(){
        int a = -1;
        String question = "Introduce una prediccion para:  " + name;
        String format = "Introduce un numero del " + 0 + " al " + GameManager.currentGame.numberOfPlayers;
        String defaultValue = "1";
        while( a < 0 || a > GameManager.currentRound.numberOfRound){
            String input = App.askForUserInput(question, format, defaultValue);
            a = Integer.parseInt(input);
            if( a < 0 || a > GameManager.currentRound.numberOfRound){
                System.out.println("Invalid Guess for " + name);
            }
        }
        currentGuess = a;
    }

}
