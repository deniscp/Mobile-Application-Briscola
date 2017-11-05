package it.polimi.group06briscola.model;

import java.util.ArrayList;

/**
 * This class defines and holds the basic information of a player in the game.
 * @author Roza.
 */

abstract class Player {
    /**
     * Username of the player.
     */
    private String username;


    /**
     * ID of the Player (from 0 to 1) representing his position on the table.
     */
    private int id;


    /**
     * Total points of the player at the end of each game.
     */
    private int score=0;


    /**
     * Stores the three Cards the Player holds.
     */
    private ArrayList<Card> hand = new ArrayList<Card>();


    /**
     * List of cards collected by the player during previous rounds
     */
    private ArrayList<Card> playerPile;



    /**
     *Constructor of the class to initialize the new objects of Player Types(Human, Robot) .
     */
    public Player( int id , String username ) {

        this.id=id;
        this.username=username;
        this.score=0;
        this.playerPile = new ArrayList<Card>();
        this.hand = new ArrayList<Card>();
    }


    /**
     * Returns the ID (position) of the player
     */
    public int getId()
    {
        return id;
    }

    /**
     * Returns the username of the player
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Returns the score of the player
     */
    public int getPlayerPoints(){
        return score;
    }

    /**
     * Returns the hand of the player
     */
    public ArrayList<Card> getHand() { return hand;}

    /**
     * Returns the pile of the player
     */
    public ArrayList<Card> getPlayerPile() { return playerPile; }

    /** Plays a player's card, removing it from his hand
     * @param position the position of the card that has been selected by a player to be played.
     * @return card selected from hand of player.
     */
    Card throwCard(int position) {
        if( position<0 || position >= hand.size())
            throw new IllegalArgumentException("Selected Card is not available");
        return hand.remove(position);
    }

    /** Adds a card to player's hand
     * @param card card to be replaced.
     */
    public void takeCardInHand(Card card) {
        if (this.hand.size()>3)
            throw new IllegalArgumentException("Cannot take more than 3 cards");
        this.hand.add(card);
    }
}