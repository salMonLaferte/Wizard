package com.DavidDiaz.Wizard;

import java.util.Iterator;

class Player {
    private String name;
    private int score;
    private int currentGuess;
    private int currentRoundWins;
    private SimpleLinkedList<Card> hand;
    private int playerId;
    
    public Player( String name, int id ){
        this.name = name;
        score = 0;
        currentGuess = -1;
        hand = new SimpleLinkedList<>();
        playerId = id;
    }
    
    /**
     * Set prediction for current player
     */
    public void setGuess(){
        int a = -1;
        String question = "Introduce una prediccion para:  " + name;
        String format = "Introduce un numero del " + 0 + " al " + GameManager.currentRound.numberOfRound;
        String defaultValue = "1";
        while( a < 0 || a > GameManager.currentRound.numberOfRound){
            String input = App.askForUserInput(question, format, defaultValue);
            boolean invalidFormat = false;
            try{
                a = Integer.parseInt(input);
            }catch(Exception e){
                invalidFormat = true;
            }
            if( invalidFormat || a < 0 || a > GameManager.currentRound.numberOfRound){
                App.showMessageToUser("Introduce un valor válido", "Por favor, Introduce un numero del " + 0 + " al " + GameManager.currentRound.numberOfRound);
            }
        }
        currentGuess = a;
        App.drawEverything();
    }

    /**
     * Validates if player can play the card 
     */
    public  boolean validateCard( String cardStr, int leaderFigure){
        //Validates that cardStr is a valid card
        Card c = Card.StrToCard(cardStr);
        if( c == null ){
            App.showMessageToUser("No", "Formato erroneo");
            return false;
        }
            
        //Validates that player has the card in his hand
        if(!hand.contains(c)){
            App.showMessageToUser("No", "El jugador no tiene esa carta en su mano");
            return false;
        }
        
        //If card is a Wizard or a DumbCard then player can play it
        if(c instanceof WizardCard || c instanceof DumbCard){
            return true;
        }

        //if leaderFigure is invalid(no leader figure yet) return true
        if(leaderFigure<= 0 || leaderFigure >4)
            return true;
        
        //If player has a card of the leaderFigure it hast to play one of those cards
        String leaderFigStr = RegularCard.getFigureStr(leaderFigure);
        if( !c.getFigureStr().equals(leaderFigStr) && handContainsFigure(leaderFigStr)){
            App.showMessageToUser("No", "El jugador tiene una carta del palo lider y debe jugarlo (o jugar una carta especial)");
            return false;
        }

        return true;

    }

    /**
     * Check if the hand of the player has a card with the specified figure
     * @return
     */
    public boolean handContainsFigure(String figure){
        Iterator<Card> it = hand.begin();
        while(it.hasNext()){
            Card c = it.next();
            if(c.getFigureStr().equals(figure)){
                return true;
            }
        }
        return false;
    }

    /**
     * Plays a Card, dont call this method until cardStr has been validated
     * @param cardStr
     */
    public void playCard(String cardStr){
        Card c = Card.StrToCard(cardStr);
        hand.delete(c);
        GameManager.playCard(this, c);
    }

    /**Increase roundsWin by 1 
    * 
    */
    public void RoundWin(){
        currentRoundWins++;
    }

    /**Returns name of the player 
     * 
    */
    public String getName(){
        return name;
    }

    /**
     * Clears player hand
     */
    public void clearHand(){
        hand.clear();
    }

    /**
     * Adds card to hand
     * @param c
     */
    public void addToHand(Card c){
        hand.add(c);
    }

    /**
     * Returns an iterator to the begin of the hand
     * @return
     */
    public Iterator<Card> handBegin(){
        return hand.begin();
    }

    /**
     * Returns current guess
     * @return
     */
    public int getCurrentGuess(){
        return currentGuess;
    }

    /**
     * Return player index on the game array
     * @return
     */
    public int getPlayerId(){
        return playerId;
    }

    /**
     * Return currentRoundWins for currentRound
     * @return
     */
    public int getCurrentRoundWins(){
        return currentRoundWins;
    }

    /**
     * Returns playerCurrentScore
     * @return
     */
    public int getScore(){
        return score;
    }

    /**
     * Set the number of trick wins
     * @param r
     */
    public void setRoundWins(int r){
        currentRoundWins =r;
    }

    public void increaseScore(int n){
        score +=n;
    }

    public void resetGuess(){
        currentGuess = -1;
    }

}
