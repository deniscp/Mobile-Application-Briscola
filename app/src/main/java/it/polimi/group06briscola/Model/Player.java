package it.polimi.group06briscola.Model;

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
    private Card[] hand = new Card[3];


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
        playerPile = new ArrayList<Card>();
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
     * @param position the position of the card that has been selected by a player to play.
     * @return card selected from hand of player.
     */
    Card selectCard(int position) {
        if( position<0 || position>2 || hand[position]==null)
            throw new IllegalArgumentException("Selected Card is not available");
        return hand[position];
    }

    /**
     * @param position the position of the card to be replaced
     * @param card card to be replaced.
     */
    public void setNthCardInHand(Card card, int position) {
        if (position<0 || position>2)
            throw new IllegalArgumentException("Selected Card cannot be replaced");
        this.hand[position] = card;
    }
}
