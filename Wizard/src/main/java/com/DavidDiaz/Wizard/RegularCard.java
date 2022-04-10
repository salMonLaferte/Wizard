package com.DavidDiaz.Wizard;

public class RegularCard extends Card {
    
    int figure;
    int number;

    public RegularCard(int figure, int number){
        this.figure = figure;
        this.number = number;
    }

    @Override
    public String getImage() {
        String name = "card" + figure + ".png";
        return "resources/" + name;
    }
}
