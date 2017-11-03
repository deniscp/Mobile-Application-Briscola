package it.polimi.group06briscola.model;

/**@author Timo Zandonella
 *
 */
public class Card {

    private Suit suit;
    private int rank;
    private int point;
    private int value;

    /**
     * Constructor for a Card only with the value and the suit of a card
     * @param value
     * @param suit
     */
    public Card(int value, Suit suit) {
        this.value = value;
        this.suit = suit;
        this.setRank(value);
        this.setPoints(value);
    }

    public Suit getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }

    public int getPoint() {
        return point;
    }

    public int getValue() {
        return value;
    }

    public int setRank(int value){
        switch(value){
            case 1: return 1; //A
            case 2: return 10;
            case 3: return 2; //3
            case 4: return 9;
            case 5: return 8;
            case 6: return 7;
            case 7: return 6;
            case 8: return 5; //J
            case 9: return 4; //H
            case 10: return 3; //K
            default: throw new IllegalArgumentException();
        }
    }

    public int setPoints(int value){
        switch(value){
            case 1: return 11;
            case 2: return 0;
            case 3: return 10;
            case 4: return 0;
            case 5: return 0;
            case 6: return 0;
            case 7: return 0;
            case 8: return 2;
            case 9: return 3;
            case 10: return 4;
            default: throw new IllegalArgumentException();
        }
    }



    @Override
    public String toString() {
        switch(value) {
            case 1: return "1" + suit;
            case 2: return "2" + suit;
            case 3: return "3" + suit;
            case 4: return "4" + suit;
            case 5: return "5" + suit;
            case 6: return "6" + suit;
            case 7: return "7" + suit;
            case 8: return "J" + suit;
            case 9: return "H" + suit;
            case 10: return "K" + suit;

            default: throw new IllegalArgumentException();
        }
    }
}