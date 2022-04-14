package com.DavidDiaz.Wizard;

/**
 * Class to keep the data from a Trick
 */
class TrickData {
    private int leaderFigure;
    private int numberOfTrick;
    private SimpleLinkedList<MyPair<Player,String>> playedCards;
    private Player winner;

    public TrickData(int numberOfTrick){
        this.playedCards = new SimpleLinkedList<>();
        this.numberOfTrick = numberOfTrick;
    }
    
    public int getLeaderFigure(){
        return leaderFigure;
    }

    public Player getWinner(){
        return winner;
    }

    public SimpleLinkedList<MyPair<Player,String>> getPlayedCards(){
        return playedCards;
    }

    public void addPlayedCard(Player p, String card){
        MyPair<Player, String> pair = new MyPair<Player,String>(p, card);
        playedCards.add(pair);
    }

    public void setWinner(Player p){
        winner = p;
    }

    public void setLeaderFigure(int leaderFigure){
        this.leaderFigure = leaderFigure;
    }

    @Override
    public String toString(){
        String result = "Truco " + numberOfTrick + "\n";
        result += "Palo lider: " + RegularCard.getFigureName(leaderFigure) + "\n";
        result += "Cartas jugadas en orden:\n";
        result += playedCards.toString();
        result += "Ganador del truco: " + winner.getName() ;
        return result;
    }

}
