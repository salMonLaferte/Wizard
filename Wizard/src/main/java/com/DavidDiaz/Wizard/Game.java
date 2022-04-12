package com.DavidDiaz.Wizard;

class Game {
    private int numberOfPlayers;
    private Player[] players;
    private int rounds;

    public Game(int numberOfPlayers){
        this.numberOfPlayers = numberOfPlayers;
        players = new Player[numberOfPlayers];
        for(int i=0; i<numberOfPlayers; i++){
            players[i] = new Player( "Jugador  " + (i+1), i);
        }
        rounds = 60/numberOfPlayers;
    }

    /**
     * Returns number of players of the game
    */
    public int getNumberOfPlayers(){
        return numberOfPlayers;
    }

    /**
     * Return players array
     * @return
     */
    public Player getPlayer(int index){
        return players[index];
    }



}
