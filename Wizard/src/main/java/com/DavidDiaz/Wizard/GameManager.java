package com.DavidDiaz.Wizard;


import java.util.Iterator;

class GameManager {
    static Round currentRound;
    static Game currentGame;
    static SimpleLinkedList<MyPair<Player, Card>> cardsPlayed;
    static int lastPlayerWhoSuffled = -1;

    /**
     * Set a new game with the specified number of players and starts the main loop
     * @param numberOfPlayers
     */
    static void StartAGame(int numberOfPlayers){
        currentGame = new Game(numberOfPlayers);
        cardsPlayed = new SimpleLinkedList<MyPair<Player, Card>>();
        mainLoop();
    }

    static void StartNextRound(int numberOfRound){
        App.showMessageToUser("Ronda: " + numberOfRound, "Comezará la ronda número: " + numberOfRound);
        currentRound = new Round(numberOfRound);
        currentRound.roundStart();
    }

    /**
     * Loop which control the whole game
     */
    static void mainLoop(){
        int roundNo = 1;
        while(roundNo <= currentGame.getNumberOfRounds()){
            StartNextRound(roundNo);
            while(currentRound.currentTrick <= roundNo){
                currentRound.nextTrick();
                Player winnerOfTrick = getTrickWinner(currentRound.winnerFigure, currentRound.leaderFigure);
                currentRound.lastWinner = winnerOfTrick.getPlayerId();
            }
            String scoreChange = increasePlayerScores();
            App.showMessageToUser("Ronda: " + roundNo +  " finalizada. ", "Resultados \n" + scoreChange);
            resetPlayerTrickWins();
            App.drawEverything();
            roundNo++;
        }
    }


    static Player getTrickWinner(int winnerFigure, int leaderFigure){
        Iterator<MyPair<Player, Card>> it = cardsPlayed.begin();

        //Look for first Wizard
        while(it.hasNext()){
            MyPair<Player, Card> pair = it.next();
            if(pair.second instanceof WizardCard){
                giveVictoryToPlayer(pair.first, " debido a que jugó un mago primero.");
                return pair.first;
            }
        }

        Player playerWhoPlayedMax = null;
        //Look for highest winnerFigure
        if(winnerFigure != 0){
            playerWhoPlayedMax = lookForHighestOfFigure(winnerFigure);
            if(playerWhoPlayedMax != null){
                giveVictoryToPlayer(playerWhoPlayedMax, " debido a que jugó el palo de triunfo más alto. ");
                return playerWhoPlayedMax;
            }
        }
       
        //Look for highest leaderFigure
        if(leaderFigure !=0){
            playerWhoPlayedMax = lookForHighestOfFigure(leaderFigure);
            if(playerWhoPlayedMax != null){
                giveVictoryToPlayer(playerWhoPlayedMax, " debido a que jugó el palo lider más alto. ");
                return playerWhoPlayedMax;
            }
        }
        

        //Look for player who played first dumbcard
        it = cardsPlayed.begin();
        while(it.hasNext()){
            MyPair<Player, Card> pair = it.next();
            if(pair.second instanceof DumbCard){
                giveVictoryToPlayer(pair.first, " debido a que jugó un buffón primero. ");
                return pair.first;
            }
        }
        return null;
    }

    /**
     * Search and returns the playerd who played the highest card of the specified figure
     * @param figure
     * @return
     */
    static Player lookForHighestOfFigure(int figure){
        Iterator< MyPair<Player, Card> > it = cardsPlayed.begin();
        int max = 0;
        Player playerWhoPlayedMax = null;
        while(it.hasNext()){
            MyPair<Player, Card> pair = it.next();
            if(pair.second instanceof RegularCard){
                RegularCard rc = (RegularCard)pair.second;
                if( rc.getFigure() == figure && rc.getNumber() > max){
                    max = rc.getNumber();
                    playerWhoPlayedMax = pair.first;
                }
            }
        }
        return playerWhoPlayedMax;
    }

    /**
     * Clears the list of cards played, shows who won the trick, updates window and increases player score of this round by one
     * @param p
     */
    static void giveVictoryToPlayer(Player p, String reason){
        p.RoundWin();
        App.showMessageToUser("Ganador: " + p.getName(), "El ganador del truco fue: " + p.getName() + " " + reason);
        cardsPlayed.clear();
        App.drawEverything();
    }

    /**
     * Add the card and the player who played it to the list
     */
    static void playCard(Player p, Card c){
        MyPair<Player,Card> pair = new MyPair<Player,Card>(p, c);
        cardsPlayed.add(pair);
        App.drawEverything();
    }

    /**
     * Increase players scores, only call this function at the end of a round
     */
    static String increasePlayerScores(){
        String details = "";
        for(int i=0; i<currentGame.getNumberOfPlayers(); i++){
            Player p = currentGame.getPlayer(i);
            details += p.getName() + ":  " + p.getScore() + "  -->  ";
            if(p.getCurrentRoundWins() == p.getCurrentGuess()){
                p.increaseScore(20 + 10 * p.getCurrentRoundWins());
            }
            else{
                p.increaseScore(-10 * Math.abs(p.getCurrentGuess() - p.getCurrentRoundWins()) );
            }
            details += p.getScore() + "\n";
        }
        return details;
    }

     /**
     * Reset the trick wins of the players
     */
    static void resetPlayerTrickWins(){
        for(int i=0; i<currentGame.getNumberOfPlayers(); i++){
            currentGame.getPlayer(i).setRoundWins(0);
            currentGame.getPlayer(i).resetGuess();
        }
    }

}
