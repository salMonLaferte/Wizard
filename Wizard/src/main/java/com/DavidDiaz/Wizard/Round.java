package com.DavidDiaz.Wizard;

class Round {
    int paloTriunfo;
    int shufflePlayer;
    int numberOfRound;
    Stack<Card> deck;

    public Round(int number){
        deck = new Stack<Card>();
        numberOfRound = number;
    }

    public void OnRoundStart(){
        shufflePlayer = 1;
        shuffleDeck();
        distributeCards();
        setPaloTriunfo();
        setGuesses();
    }

    /**
     * Distribute Cards for the players
     */
    void distributeCards(){
        for(int j=0; j< GameManager.currentGame.numberOfPlayers; j++){
            GameManager.currentGame.players[j].hand.clear();
            for(int i=0; i<numberOfRound; i++){
                Card c = deck.top();
                deck.pop();
                GameManager.currentGame.players[j].hand.add(c);
            }
        }
    }

    /**
     * Shuffles the deck
     */
    void shuffleDeck(){
        for(int i=1; i<=4; i++){
            for(int j=1; j<=13; j++){
                Card c = new RegularCard(i, j);
                deck.push(c);
            }
        }
    }

    /**
     * 
     */
    void setPaloTriunfo(){
        paloTriunfo = 1;
    }

    void setGuesses(){

    }

}
