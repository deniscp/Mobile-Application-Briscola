package it.polimi.group06briscola.Model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Timo Zandonella
 */
public class DeckOfCards {

    ArrayList<Card> playingdeck;

    DeckOfCards(boolean shuffled){

       playingdeck = new ArrayList<Card>();

        int[] rank = {1,2,3,4,5,6,7,8,9,10};
        int[] values = {11,10,4,3,2,0,0,0,0,0};
        Suit[] suit = new Suit[] {Suit.Batons, Suit.Swords, Suit.Cups, Suit.Golds};

        int i = 0, j = 0;
        while (i<suit.length) {
            while (j<rank.length) {
                playingdeck.add(new Card(suit[i], rank[j]));
                j++;
            }
            i++;
            j=0;
        }
        if (shuffled){
            Collections.shuffle(playingdeck);
        }
    }

    /**
     * A card gets drawn from the deck if there are still card in the deck
     * @return Last card of the deck
     */
    public Card takeLastCard(){
        if(playingdeck.size()!=0) {
            Card returnCard;
            returnCard = playingdeck.get(playingdeck.size() - 1);
            playingdeck.remove(playingdeck.size() - 1);
            return returnCard;
        }else{
            throw new IllegalArgumentException();
        }
    }
}
