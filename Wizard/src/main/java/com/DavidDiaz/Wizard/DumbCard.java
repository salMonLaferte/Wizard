package com.DavidDiaz.Wizard;
/**
 * Representation of the bufon card
 */
class DumbCard extends Card{

    @Override
    public String getImage() {
        return "resources/cardDumb.png";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof DumbCard;
    }

    @Override
    public String toString() {
        return "S";
    }

    @Override
    public String getFigureStr() {
        return "S";
    }

    
}
