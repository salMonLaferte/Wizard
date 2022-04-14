package com.DavidDiaz.Wizard;


import java.util.Iterator;
/**
 * Class to create an manage  a game, handles switching betwen rounds and tricks, keeps record of the history of a game
 */
class GameManager {
    static Round currentRound;
    static Game currentGame;
    static SimpleLinkedList<MyPair<Player, Card>> cardsPlayed;
    static SimpleLinkedList<RoundData> roundData;
    static RoundData currentRoundData;
    static int lastPlayerWhoSuffled = -1;

    /**
     * Set a new game with the specified number of players and starts the main loop
     * @param numberOfPlayers
     */
    static void StartAGame(int numberOfPlayers, String[] playersNames){
        currentGame = new Game(numberOfPlayers, playersNames);
        cardsPlayed = new SimpleLinkedList<MyPair<Player, Card>>();
        roundData = new SimpleLinkedList<RoundData>();
        mainLoop();
    }

    /**
     * Creates and sets a new round also creates a new roundData
     * @param numberOfRound
     */
    static void StartNextRound(int numberOfRound){
        App.showMessageToUser("Ronda: " + numberOfRound, "Comezará la ronda número: " + numberOfRound);
        currentRound = new Round(numberOfRound);
        currentRoundData = new RoundData(numberOfRound);
        currentRound.roundStart();
    }

    /**
     * Loop which control the whole game
     */
    static void mainLoop(){
        int roundNo = 1;
        while(roundNo <= currentGame.getNumberOfRounds()){//Juega el numero de rondas calculado
            StartNextRound(roundNo);//Comienza la ronda
            currentRoundData.setWinnerFigure(currentRound.getWinnerFigure());//Guarda en la data el palo de triunfo
            while(currentRound.getCurrentTrick() <= roundNo){//Ejecuta tantos trucos como rondas
                TrickData data = new TrickData(currentRound.getCurrentTrick());//datos del truco actual para el historial
                currentRound.nextTrick(data);//Ejecuta el siguiente truco
                Player winnerOfTrick = getTrickWinner(currentRound.getWinnerFigure(), currentRound.getLeaderFigure());//Obten el ganador del truco
                data.setWinner(winnerOfTrick);//Establece el ganador en los datos del truco actual
                resetPlayedCards();//Vacia la lista de cartas que están en juego
                App.drawEverything();//Dibuja los datos en pantalla
                currentRound.setLastWinner( winnerOfTrick.getPlayerId() ) ;//Actualiza el ultimo jugador que gano
                currentRoundData.addTrickData(data);//Agrega los datos del truco actual a los datos de la ronda actual
            }
            String scoreChange = increaseAndSavePlayerScores();
            App.showMessageToUser("Ronda: " + roundNo +  " finalizada. ", "Resultados \n" + scoreChange);//
            resetPlayerTrickWins();
            App.drawEverything();
            roundData.add(currentRoundData);//agrega los datos de la ronda actual al historial
            roundNo++;
        }
        App.terminar();
    }


    /**Returns the tick winner using the current cardsPlayed
     * 
     */
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
     * Shows who won the trick, updates window and increases player score of this round by one
     * @param p
     */
    static void giveVictoryToPlayer(Player p, String reason){
        p.RoundWin();
        App.showMessageToUser("Ganador: " + p.getName(), "El ganador del truco fue: " + p.getName() + " " + reason);
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
    static String increaseAndSavePlayerScores(){
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
            MyPair<Player, Integer> pair = new MyPair<Player,Integer>(p, p.getScore());
            currentRoundData.addScore(pair);
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

    /**
     * Return a String whith the history of the game
     * @return
     */
    public static String getGameData(){
        Iterator<RoundData> it = roundData.begin();
        String result = "";
        while(it.hasNext()){
            result += it.next().toString();
            result += "\n";
        }
        return result;
    }

    public static void resetPlayedCards(){
        cardsPlayed.clear();
    }
}
