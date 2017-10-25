package it.polimi.group06briscola.Model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Timo Zandonella
 */
public class DeckOfCards {

    static ArrayList<Card> sorteddeck;
    static ArrayList<Card> shuffleddeck;

    /**
     *
     * @return sorted Deck of Cards
     */
    private static ArrayList<Card> createSortedDeck(){

        sorteddeck = new ArrayList<Card>();

        final int[] rank = {1,2,3,4,5,6,7,8,9,10};
        final int[] values = {11,10,4,3,2,0,0,0,0,0};
        final Suite[] suite = new Suite[] {Suite.Batons,Suite.Swords,Suite.Cups, Suite.Golds};

        int i = 0, j = 0;
        while (i<suite.length) {
            while (j<rank.length) {
                sorteddeck.add(new Card(suite[i], rank[j], values[j]));
                j++;
            }
            i++;
            j=0;
        }
        return sorteddeck;
    }

    /**
     * @param Input the sorted deck created in the createSortedDeck() class
     * @return Returns the playing deck (a shuffled sorted deck)
     */
    private static ArrayList<Card> shuffle (ArrayList<Card> sorteddeck) {
        shuffleddeck = new ArrayList<Card>();
        Collections.shuffle(sorteddeck);
        shuffleddeck = sorteddeck;
        return shuffleddeck;
    }


}
