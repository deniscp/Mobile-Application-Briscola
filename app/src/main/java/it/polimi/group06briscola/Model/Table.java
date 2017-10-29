package it.polimi.group06briscola.Model;


/**
 * Created by denis on 28/10/17.
 */



/** Table class storing domain objects used during the game
 */
public class Table {

    /** Array of Card on the table that will hold the played cards of the players.
     * Each Player will have his slot assigned on the table, corresponding on the position where he sits
     */
    private Card[] playedCard;

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
        this.playedCard = new Card[2];
        setTrump(trump);
        setDeck(deck);
    }

    public Card[] getPlayedCard() { return this.playedCard; }

    /** Collects played cards and remove them from the table
     */
    public Card[] collectPlayedCard() {
        Card[] temp = getPlayedCard();
        int i;
        for (i=0;i<playedCard.length;i++){
            setNthPlayedCard(i,null);
        }
        return temp;
    }
    public Card getTrump() { return this.trump; }

    public DeckOfCards getDeck() { return this.deck; }

    /** Sets the n-th played card corresponding to the n-th player position
     */
    public void setNthPlayedCard(int pos, Card playedCard) {
        this.playedCard[pos] = playedCard;
    }

    public void setTrump(Card trump) { this.trump = trump;  }

    public void setDeck(DeckOfCards deck) {
        this.deck = deck;
    }
}