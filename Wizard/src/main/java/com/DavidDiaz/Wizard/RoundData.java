package com.DavidDiaz.Wizard;

class RoundData {
    private int numberOfRound;
    private SimpleLinkedList<TrickData> tricks;

    public RoundData(int numberOfRound, SimpleLinkedList<TrickData> tricks){
        this.numberOfRound = numberOfRound;
        this.tricks = tricks;
    }

    public int getNumberOfRound(){
        return numberOfRound;
    }

    @Override
    public String toString(){
        String result = "Ronda: " + numberOfRound + "\n";
        result+= tricks.toString();
        return result;
    }

}
