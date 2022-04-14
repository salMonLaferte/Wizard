package com.DavidDiaz.Wizard;
/**
 * Abstract representation of a inexistent card
 */
class NullCard extends Card{

    @Override
    public String getImage() {
        return "resources/nullCard.png";
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public String getFigureStr() {
        return "";
    }
    
}
