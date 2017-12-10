package it.polimi.group06.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * This Class holds the deck of cards with 40 or less cards in it. It also holds methods to shuffle a deck and return the current deck size.
 *
 * @author Timo Zandonella
 */
public class Deck {

    private ArrayList<Card> playingdeck;

    /**
     * Constructor for a full deck of cards. It uses the card class to create object of cards.
     *
     * @param shuffled is true, when the deck should be shuffled
     */
    public Deck(boolean shuffled) {

        this.playingdeck = new ArrayList<Card>();
        int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Suit[] suit = new Suit[]{Suit.Batons, Suit.Cups, Suit.Golds, Suit.Swords};
        int i, j;
        for (i = 0; i < suit.length; i++)
            for (j = 0; j < values.length; j++)
                playingdeck.add(new Card(values[j], suit[i]));
        if (shuffled)
            shuffle();
    }

    /**
     * Replaces deck with new one
     */
    public void replaceDeck(ArrayList<Card> newDeck) {
        this.playingdeck = newDeck;
    }

    /**
     * Shuffle the current deck of cards
     */
    public void shuffle() {
        Collections.shuffle(this.playingdeck);
    }

    /**
     * Remaining cards in current deck
     *
     * @return number of cards left in the deck
     */
    int remaining() {
        return this.playingdeck.size();
    }

    /**
     * A card gets drawn from the deck if there are still cards in the deck
     *
     * @return the card from the top of the deck
     */
    public Card drawCard() {
        if (playingdeck.size() == 0)
            throw new IllegalArgumentException("Impossible to take a card: deck is empty");
        return playingdeck.remove(0);
    }

    /**
     * Add a new card on bottom of the deck
     *
     * @param card to be added in the deck
     */
    public void addCard(Card card) {
        this.playingdeck.add(card);
    }

    /**
     * Push a card on top of the deck
     * Useful for later implementation of the "Game undo" operation
     *
     * @param card to be pushed in the deck
     */
    public void pushCard(Card card) {
        this.playingdeck.add(0, card);
    }

    @Override
    public String toString() {
        StringBuilder deckToString = new StringBuilder();

        for (Card card : this.playingdeck) {
            deckToString.append(card.toString());
        }
        return deckToString.toString();
    }
}