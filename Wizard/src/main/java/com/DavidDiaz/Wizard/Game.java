package com.DavidDiaz.Wizard;

class Game {
    int numberOfPlayers;
    Player[] players;
    int rounds;

    public Game(int numberOfPlayers){
        this.numberOfPlayers = numberOfPlayers;
        players = new Player[numberOfPlayers];
        for(int i=0; i<numberOfPlayers; i++){
            players[i] = new Player( "Jugador  " + (i+1));
        }
        rounds = 60/numberOfPlayers;
    }

    public static void Start(){

    }

}
