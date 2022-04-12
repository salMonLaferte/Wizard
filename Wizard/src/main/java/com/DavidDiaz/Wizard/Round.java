package com.DavidDiaz.Wizard;

import java.util.Random;

class Round {
    int paloTriunfo;
    int leaderFigure;
    int shufflePlayer;
    int numberOfRound;
    int currentTrick;
    int lastWinner;
    Stack<Card> deck;

    public Round(int number){
        deck = new Stack<Card>();
        numberOfRound = number;
    }

    //Round Initialization
    public void roundStart(){
        shufflePlayer = (GameManager.lastPlayerWhoSuffled +1) % GameManager.currentGame.getNumberOfPlayers();
        GameManager.lastPlayerWhoSuffled = shufflePlayer;
        shuffleDeck();
        distributeCards();
        setGuesses();
        setPaloTriunfo();
        leaderFigure = 0;
        currentTrick = 1;
        App.drawEverything();
        nextTrick();
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
            deck.push(new WizardCard());
        }
        for(int i=0; i<4; i++){
            deck.push(new DumbCard());
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
     * 
     */
    void setPaloTriunfo(){
        paloTriunfo = 1;
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
    void nextTrick(){
        int starterPlayer;
        int numberOfPlayers = GameManager.currentGame.getNumberOfPlayers();
        String format = "Introduce la carta a jugar";
        String defaultValue = "";
        boolean leaderFigureIsSet = false;
        leaderFigure = 0;//At the beggining of the round there's no leaderFigure

        //Set starter player for current trick;
        if(currentTrick == 1){
            starterPlayer = (shufflePlayer + 1) % numberOfPlayers;
        }else
            starterPlayer = lastWinner;

        //Ask every player to play a valid card
        for(int i=0 ; i<numberOfPlayers; i++){
            Player currentPlayer = GameManager.currentGame.getPlayer((starterPlayer + i) % numberOfPlayers);
            String message = "Es turno del jugador " + currentPlayer.getName();            
            String playedCard = App.askForUserInput(message, format, defaultValue );
            while( !currentPlayer.validateCard(playedCard, leaderFigure)){
                App.showMessageToUser("Error", "La carta que introdujiste no es válida");
                playedCard = App.askForUserInput(message, format, defaultValue );
            }
            currentPlayer.playCard(playedCard);
            
            //If leaderFigure is not set and player played a regular card then set the leader figure
            if(!leaderFigureIsSet){
                Card c = Card.StrToCard(playedCard);
                if(c instanceof RegularCard){
                    RegularCard rc = (RegularCard)c;
                    leaderFigure = rc.getFigure();
                    leaderFigureIsSet = true;
                }
            }

        }
        //if currentTrick is the last one start a new round
        if(currentTrick == numberOfRound){
            GameManager.getTrickWinner( paloTriunfo, leaderFigure);
            GameManager.StartNextRound(numberOfRound+1);
        }
        else{
            lastWinner = GameManager.getTrickWinner( paloTriunfo, leaderFigure).getPlayerId();
            currentTrick++;
            nextTrick();
        }
    }

}
