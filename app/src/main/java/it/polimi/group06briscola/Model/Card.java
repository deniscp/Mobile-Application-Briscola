package it.polimi.group06briscola.Model;

/**@author Timo Zandonella
 *
 */
public class Card {

    private Suit suite;
    private int rank;
    private int point;
    private int value;

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

    public Card(Suit suit, int value) {
        this.suite = suit;
        this.value = value;
        this.setRank(value);
        this.setPoints(value);
    }

    public int setRank(int value){
        switch(value){
            case 1: return 1;
            case 2: return 10;
            case 3: return 2;
            case 4: return 9;
            case 5: return 8;
            case 6: return 7;
            case 7: return 6;
            case 8: return 5;
            case 9: return 4;
            case 10: return 3;
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