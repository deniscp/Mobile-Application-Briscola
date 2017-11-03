package it.polimi.group06briscola.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * @author Timo Zandonella
 */
public class DeckOfCards {

    private ArrayList<Card> playingdeck;


    /**
     * Constructor for a Deck of Cards;
     * @param shuffled shuffled is true, when the deck should also be shuffled
     */
    public DeckOfCards(boolean shuffled) {

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
     * Constructor of Deck of Cards, returns an empty deck of Cards
     */
    public DeckOfCards(){
        this.playingdeck = new ArrayList<Card>();
    }

    /**
     * Shuffles the current deck of cards
     */
    public void shuffle(){
        Collections.shuffle(this.playingdeck);
    }

    /** Remaining cards in current deck
     *
     * @return number of cards left in the deck
     */
    public int remaining(){
        return this.playingdeck.size();
    }

    /**
     * A card gets drawn from the deck if there are still cards in the deck
     *
     * @return Last card of the deck
     */
    public Card takeCard() {
        if (playingdeck.size() == 0)
            throw new IllegalArgumentException("Impossible to take a card: deck is empty");

        return playingdeck.remove(0);
    }

    /**
     * Add a new card on bottom of the deck
     * @param card to be added in the deck
     */
    public void addCard(Card card)
    {
        this.playingdeck.add(card);
    }

    /**
     * Push a card on top of the deck
     * Useful for later implementation of the "Game undo" operation
     * @param card to be pushed in the deck
     */
    public void pushCard(Card card)
    {
        this.playingdeck.add(0,card);
    }

    @Override
    public String toString(){
        Iterator<Card> deckIterator = this.playingdeck.iterator();
        StringBuilder deckToString = new StringBuilder();

        while (deckIterator.hasNext()){
            deckToString.append(deckIterator.next().toString());
        }
        return deckToString.toString();
    }

    public static void main(String[] argv){

        DeckOfCards deck = new DeckOfCards(false);
        DeckOfCards sdeck = new DeckOfCards(true);

        System.out.println(deck.playingdeck.toString());

        System.out.println(deck);

        System.out.println(sdeck);

        while (sdeck.remaining()>0){
            System.out.println("Prendo la carta: " + sdeck.takeCard());
            System.out.println(sdeck.playingdeck.toString());
        }

        DeckOfCards deck2 = new DeckOfCards();
        System.out.println(deck2);
        deck2.addCard(new Card(3,Suit.Batons));
        deck2.addCard(new Card(10,Suit.Batons));
        deck2.addCard(new Card(5,Suit.Cups));
        System.out.println(deck2);
    }
}