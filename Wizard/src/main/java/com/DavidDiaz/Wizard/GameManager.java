package com.DavidDiaz.Wizard;

class GameManager {
    static Round currentRound;
    static Game currentGame;
    int playerPlaying;

    static void StartAGame(int numberOfPlayers){
        currentGame = new Game(numberOfPlayers);
        currentRound = new Round(0);
        currentRound.OnRoundStart();
    }

    



}
