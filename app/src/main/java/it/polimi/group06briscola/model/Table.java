package it.polimi.group06briscola.model;


/**
 * Created by denis on 28/10/17.
 */


import java.util.ArrayList;
import java.util.Iterator;

/** Table class storing domain objects used during the game
 */
public class Table {

    /** Array of Card on the table that will hold the played cards of the players.
     * The first Player to play will put the card in first position, then the second Player to play and so on
     */
    private ArrayList<Card> playedCard;

    /** The "Briscola" set at the beginning of the game is kept here
     */
    private Card trump;

    /** The deck shuffled at the beginning of the game is kept here
     */
    private DeckOfCards deck;

    /** Constructor of the Table
     * Initialize an empty array of played card
     * Set the trump and the deck initialized from the Game class
     */
    public Table(Card trump, DeckOfCards deck) {
        this.playedCard = new ArrayList<Card>();
        setTrump(trump);
        setDeck(deck);
    }

    public ArrayList<Card> getPlayedCard() { return this.playedCard; }

    /**
     *  Collects played cards and remove them from the table
     */
    public ArrayList<Card> collectPlayedCard() {
        ArrayList<Card> temp = new ArrayList<>(this.getPlayedCard());
        this.playedCard = new ArrayList<Card>();
        return temp;
    }
    public Card getTrump() { return this.trump; }

    public DeckOfCards getDeck() { return this.deck; }

    /**
     *  Sets the n-th played card corresponding to the n-th player position
     */
    public void setPlayedCard(Card playedCard) {
        this.playedCard.add(playedCard);
    }

    public void setTrump(Card trump) { this.trump = trump;  }

    public void setDeck(DeckOfCards deck) {
        this.deck = deck;
    }

    @Override
    public String toString() {
        StringBuilder tableToString = new StringBuilder();

        tableToString.append(this.getDeck().toString());
        tableToString.append(this.getTrump().toString());
        tableToString.append(".");

        for (Iterator i=this.getPlayedCard().iterator(); i.hasNext();){
            tableToString.append(i.next().toString());
        }
        tableToString.append(".");
        return tableToString.toString();
    }
}