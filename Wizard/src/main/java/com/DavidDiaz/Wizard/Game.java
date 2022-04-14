package com.DavidDiaz.Wizard;
/**
 * Class with the info of one Game
 */
class Game {
    private int numberOfPlayers;
    private Player[] players;
    private int rounds;

    public Game(int numberOfPlayers, String[] playersNames){
        this.numberOfPlayers = numberOfPlayers;
        players = new Player[numberOfPlayers];
        for(int i=0; i<numberOfPlayers; i++){
            players[i] = new Player( playersNames[i], i);
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

    /**
     * 
     * @return
     */
    public int getNumberOfRounds(){
        return rounds;
    }



}
