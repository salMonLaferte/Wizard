package com.DavidDiaz.Wizard;

class WizardCard extends Card{

    @Override
    public String getImage() {
        return "resources/cardWizard.png";
    }

        @Override
    public boolean equals(Object obj) {
        return obj instanceof WizardCard;
    }

    @Override
    public String toString() {
        return "W";
    }

    @Override
    public String getFigureStr() {
        return "W";
    }
    
}
