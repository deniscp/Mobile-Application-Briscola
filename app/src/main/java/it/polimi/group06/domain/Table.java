package it.polimi.group06.domain;

import java.util.ArrayList;


/**
 * @author Denis on 28/10/17
 *         Table class storing domain objects used during the game, deck, briscola card and played cards
 */
public class Table {

    /**
     * ArrayList of Card representing the cards played by the players.
     * Cards are stored in the order in which they are played,
     * so the first element is the card played by the player who starts the round,
     * the second element is the card played by the player who immediately follows the first one and so on
     */
    private ArrayList<Card> playedCards;

    /**
     * The "Briscola" card set at the beginning of the game is kept here
     */
    private Card trump;

    /**
     * The deck initialized at the beginning of the game is kept here
     */
    private Deck deck;

    /**
     * Constructor of the Table
     * Initialize an empty array of played card
     * Set the trump and the deck initialized from the Game class
     */
    public Table(Card trump, Deck deck) {
        this.playedCards = new ArrayList<>();
        setTrump(trump);
        setDeck(deck);
    }

    public void setTrump(Card trump) {
        this.trump = trump;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Card getTrump() {
        return this.trump;
    }

    public Deck getDeck() {
        return this.deck;
    }

    /**
     * Retrieve played cards for visualizing, leaving them on the table
     */
    ArrayList<Card> getPlayedCards() {
        return this.playedCards;
    }

    /**
     * Replaces played cards with new ones
     */
    public void replacePlayedCards(ArrayList<Card> newPlayedCards) {
        this.playedCards = newPlayedCards;
    }

    /**
     * Collects played cards and remove them from the table
     */
    ArrayList<Card> collectPlayedCard() {
        ArrayList<Card> temp = new ArrayList<>(this.getPlayedCards());
        this.playedCards = new ArrayList<>();
        return temp;
    }

    /**
     * Takes the trump removing it from the table
     *
     * @return the trump on the table
     */
    Card takeTrump() {
        Card temp = this.getTrump();
        this.setTrump(null);
        return temp;
    }

    /**
     * Places a card on the table
     */
    void placeCard(Card playingCard) {
        if (this.playedCards.size() == 2)
            throw new IllegalArgumentException("All the players have already played their card in this turn");
        this.playedCards.add(playingCard);
    }

    @Override
    public String toString() {
        StringBuilder tableToString = new StringBuilder();

        tableToString.append(this.getDeck().toString());
        if (this.getTrump() != null) {
            tableToString.append(this.getTrump().toString());
        }
        tableToString.append(".");

        for (Card card : this.getPlayedCards()) {
            tableToString.append(card.toString());
        }
        tableToString.append(".");
        return tableToString.toString();
    }
}