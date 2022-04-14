package com.DavidDiaz.Wizard;

import java.util.Random;

/**
 * Abstract representation of a round, manages everytrick and play turns
 */
class Round {
    private int winnerFigure;
    private int leaderFigure;
    private int shufflePlayer;
    private int numberOfRound;
    private int currentTrick;
    private int lastWinner;
    private Stack<Card> deck;

    public Round(int number){
        deck = new Stack<Card>();
        numberOfRound = number;
        currentTrick = 1;
    }

    /**
     * Prepare everything to start the round
     */
    public void roundStart(){
        shufflePlayer = (GameManager.lastPlayerWhoSuffled +1) % GameManager.currentGame.getNumberOfPlayers();
        GameManager.lastPlayerWhoSuffled = shufflePlayer;
        shuffleDeck();
        leaderFigure = 0;
        App.showMessageToUser( "Se barajeo el mazo ", "El jugador : " + GameManager.currentGame.getPlayer(shufflePlayer).getName() + " barajó el mazo.");
        distributeCards();
        App.drawEverything();
        setWinnerFigure();
        App.drawEverything();
        setGuesses();
    }

    /**
     * Distribute Cards for the players
     */
    void distributeCards(){
        for(int j=0; j< GameManager.currentGame.getNumberOfPlayers(); j++){
            GameManager.currentGame.getPlayer(j).clearHand();;
            for(int i=0; i<numberOfRound; i++){
                Card c = deck.top();
                deck.pop();
                GameManager.currentGame.getPlayer(j).addToHand(c);;
            }
        }
    }

    /**
     * Shuffles the deck
     */
    void shuffleDeck(){
        SimpleLinkedList<Card> aux = new SimpleLinkedList<>();
        //Put every card in a list
        for(int i=1; i<=4; i++){
            for(int j=1; j<=13; j++){
                Card c = new RegularCard(i, j);
                aux.add(c);
            }
        }
        for(int i=0; i<4; i++){
            aux.add(new WizardCard());
        }
        for(int i=0; i<4; i++){
            aux.add(new DumbCard());
        }
        //Randomly take one card of the list and put it on the deck until the list is empty
        Random rand = new Random();
        while(!aux.isEmpty()){
            int n = rand.nextInt(aux.getSize());
            Card c = aux.getAndDeleteAtIndex(n);
            deck.push(c);
        }
    }

    /**
     * Sets the winner figure
     */
    void setWinnerFigure(){
        if(deck.isEmpty()){
            winnerFigure = 0;
            App.showMessageToUser("Estableciendo el palo de triunfo", "No quedan cartas en el deck, por lo tanto NO hay palo de triunfo esta ronda.");
            return;
        }
        Card c = deck.top();
        if( c instanceof RegularCard){
            RegularCard rc = (RegularCard)c;
            winnerFigure = rc.getFigure();
            App.showMessageToUser("Estableciendo el palo de triunfo.", "La siguiente carta en el deck es: " + RegularCard.getFigureName(winnerFigure) 
            + " por lo tanto " + RegularCard.getFigureName(winnerFigure) + " es el palo de triunfo" );
            return;
        }
        if(c instanceof WizardCard){
            String playerName = GameManager.currentGame.getPlayer(shufflePlayer).getName();
            String format = "Introduce una de las siguientes letras";
            format += "\n P para elegir Peach \n M para elegir Mario \n T para elegir Toad \n B para elegir Bowser";
            App.showMessageToUser("Estableciendo el palo de triunfo.", "La siguiente carta en el deck es un mago.El jugador:  " + playerName + " debe elegir el palo de triunfo.");
            String input = App.askForUserInput("Establece el palo de triunfo" , format, "P");
            while( ! ( input.equals("P") || input.equals("T") || input.equals("M") || input.equals("B") ) ){
                App.showMessageToUser("Formato inválido", "Por favor introduce una de las letras especificadas");
                input = App.askForUserInput("Establece el palo de triunfo", format, "P");
            }
            winnerFigure = RegularCard.getFigureNumber(input);
            return;
        }
        if(c instanceof DumbCard){
            App.showMessageToUser("Estableciendo el palo de triunfo.", "La siguiente carta en el deck es un bufon. No hay palo de triunfo esta ronda");
            winnerFigure = 0;
            return;
        }
    }

    /**
     * Set the players prediction for the currentRound
     */
    void setGuesses(){
        for(int i=0; i<GameManager.currentGame.getNumberOfPlayers(); i++){
            GameManager.currentGame.getPlayer(i).setGuess();
        }
    }

    /**Manage the next trick of this round
     * 
     */
    void nextTrick(TrickData data){
        int starterPlayer;
        int numberOfPlayers = GameManager.currentGame.getNumberOfPlayers();
        String format = "Introduce la carta a jugar";
        String defaultValue = "";
        boolean leaderFigureIsSet = false;
        leaderFigure = 0;//At the beggining of the trick there's no leaderFigure
        App.drawEverything();
        
        //Set starter player for current trick;
        if(currentTrick == 1){
            starterPlayer = (shufflePlayer+1) % numberOfPlayers;
        }else
            starterPlayer = lastWinner;

        //Ask every player to play a valid card
        for(int i=0 ; i<numberOfPlayers; i++){
            Player currentPlayer = GameManager.currentGame.getPlayer((starterPlayer + i) % numberOfPlayers);
            String message = "Es turno del jugador " + currentPlayer.getName();            
            String playedCard = App.askForUserInput(message, format, defaultValue );
            while( !currentPlayer.validateCard(playedCard, leaderFigure)){
                playedCard = App.askForUserInput(message, format, defaultValue );
            }
            currentPlayer.playCard(playedCard);
            data.addPlayedCard(currentPlayer, playedCard);
            
            //If leaderFigure is not set and player played a regular card then set the leader figure
            if(!leaderFigureIsSet){
                Card c = Card.StrToCard(playedCard);
                if(c instanceof RegularCard){
                    RegularCard rc = (RegularCard)c;
                    leaderFigure = rc.getFigure();
                    data.setLeaderFigure(leaderFigure);
                    leaderFigureIsSet = true;
                    App.drawEverything();
                }
            }
        }
        currentTrick++;
    }

    public int getWinnerFigure(){
        return winnerFigure;
    }

    public int getLeaderFigure(){
        return leaderFigure;
    }

    public int getNumberOfRound(){
        return numberOfRound;
    }

    public int getCurrentTrick(){
        return currentTrick;
    }

    public void setLastWinner(int lw){
        lastWinner  = lw;
    }

}
