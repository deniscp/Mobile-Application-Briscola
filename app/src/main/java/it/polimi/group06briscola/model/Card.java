package it.polimi.group06briscola.model;

/**@author Timo Zandonella
 *
 */
public class Card {

    private Suit suite;
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
        this.suite = suit;
        this.setRank(value);
        this.setPoints(value);
    }

    public Suit getSuite() {
        return suite;
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

    /**
     * Takes a String, return an object card from a String
     * @param stringcard
     * @return
     */
    public Card parseToCard(String stringcard){
        String value = stringcard.substring(0,0);
            switch (value){
                case "K": value = "10";
                case "H": value = "9";
                case "J": value = "8";
            }
        int valueInt = Integer.parseInt(value);
        Suit suit = Suit.valueOf(stringcard.substring(1,1));
        return new Card(valueInt, suit);
    }

    @Override
    public String toString() {
        switch(rank) {
            case 1: return "1" + suite;
            case 2: return "3" + suite;
            case 3: return "K" + suite;
            case 4: return "H" + suite;
            case 5: return "J" + suite;
            case 6: return "7" + suite;
            case 7: return "6" + suite;
            case 8: return "5" + suite;
            case 9: return "4" + suite;
            case 10: return "2" + suite;

            default: throw new IllegalArgumentException();
        }
    }
}