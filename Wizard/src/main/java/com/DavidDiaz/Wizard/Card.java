package com.DavidDiaz.Wizard;

abstract class Card {
    /**
     * Get route of the image for this card
     * @return
     */
    
     public abstract String getImage();
    /**
     * Returns a string representation of the card
     */
    public abstract String toString();
    
    /**
     * Returns a string representation of the figure for this card
     * @return
     */
    public abstract String getFigureStr();
    
    /**
     * Given a string returns a card instance if string is not a valid card returns null
     * @return
     */
    public static Card StrToCard(String s){
        
        if(s.length() == 0 )
            return null;
        String figure = s.substring(0, 1);
        if( !( figure.equals("T") || figure.equals("P") || figure.equals("M") || figure.equals("B") || figure.equals("W") || figure.equals( "S") ) ){
            return null;
        }
        if(s.length() == 1){
            if(figure.equals( "W" ) ){
                return new WizardCard();
            }
            if(figure.equals( "S") ){
                return new DumbCard();
            }
            return null;
        }
        String number = s.substring(1, s.length());
        int n;
        try {
            n = Integer.parseInt(number);
        } catch (Exception e) {
            return null;
        }
        if( 0 < n && n <= 13)
            return new RegularCard(RegularCard.getFigureNumber(figure), n);
        else
            return null;
    }
}
