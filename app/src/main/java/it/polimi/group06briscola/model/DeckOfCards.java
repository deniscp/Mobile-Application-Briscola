package it.polimi.group06briscola.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Timo Zandonella
 */
public class DeckOfCards {

    ArrayList<Card> playingdeck;

    /**
     * Constructor for a Deck of Cards: shuffled is true, when the deck should also be shuffled;
     * the String deckString should be empty, when a complete deck is created. Otherwise a String of a deck can be plaaced here.
     * @param shuffled
     * @param deckString
     */
    DeckOfCards(boolean shuffled, String deckString) {

        playingdeck = new ArrayList<Card>();

        if (deckString.length()!=0) {
            int[] rank = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            int[] values = {11, 10, 4, 3, 2, 0, 0, 0, 0, 0};
            Suit[] suit = new Suit[]{Suit.Batons, Suit.Swords, Suit.Cups, Suit.Golds};
            int i = 0, j = 0;
            while (i < suit.length) {
                while (j < rank.length) {
                    playingdeck.add(new Card(rank[j], suit[i]));
                    j++;
                }
                i++;
                j = 0;
            }
            if (shuffled) {
                Collections.shuffle(playingdeck);
            }
        } else {
            Card card = null;
            int size = deckString.length();
            for (int i = 0; i < size; i += 2) {
                card = card.parseToCard(deckString.substring(i, i + 1));
                playingdeck.add(card);
            }
        }
    }

    /**
     * A card gets drawn from the deck if there are still card in the deck
     *
     * @return Last card of the deck
     */
    public Card takeLastCard() {
        if (playingdeck.size() != 0) {
            Card returnCard;
            returnCard = playingdeck.get(playingdeck.size() - 1);
            playingdeck.remove(playingdeck.size() - 1);
            return returnCard;
        } else {
            throw new IllegalArgumentException();
        }
    }

}