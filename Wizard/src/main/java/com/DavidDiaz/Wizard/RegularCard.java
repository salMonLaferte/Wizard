package com.DavidDiaz.Wizard;

class RegularCard extends Card {
    
    protected int figure;
    protected int number;

    public RegularCard(int figure, int number){
        this.figure = figure;
        this.number = number;
    }

    @Override
    public String getImage() {
        String name = "card" + figure + ".png";
        return "resources/" + name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof RegularCard){
            RegularCard other = (RegularCard)obj;
            return this.figure == other.figure && this.number == other.number;
        }
        return false;
    }

    @Override
    public String toString() {
        String result = getFigureStr();
        result += number;
        return result;
    }

    @Override
    public String getFigureStr() {        
        switch(figure){
            case 1:
            return "P";
            case 2:
            return "T";
            case 3:
            return "M";
            case 4:
            return "B";
        }
        return "";
    }
    
}
