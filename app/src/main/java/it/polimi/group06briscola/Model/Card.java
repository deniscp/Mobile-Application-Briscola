package it.polimi.group06briscola.Model;

/**@author Timo Zandonella
 *
 */
public class Card {

    private Suit suite;
    private int rank;
    private int points;
    private int value;

    public Suit getSuite() {
        return suite;
    }

    public int getRank() {
        return rank;
    }

    public int getPoints() {
        return points;
    }

    public Card(Suit suit, int rank) {
        this.suite = suit;
        this.rank = rank;
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