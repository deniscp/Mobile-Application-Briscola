package it.polimi.group06briscola.Model;

/**@author Timo Zandonella
 *
 */
public class Card {

    public Suite suite;
    public int rank;
    public int value;

    public Card(Suite suit, int rank, int value) {
        this.suite = suit;
        this.rank = rank;
        this.value = value;
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