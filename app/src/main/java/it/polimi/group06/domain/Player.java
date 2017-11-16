package it.polimi.group06.domain;

import java.util.ArrayList;

/**
 * This class defines and holds the basic information of a player such as id, username, score, hand and pile of the player in the game.
 * @author Roza.
 */

abstract public class Player {
    /**
     * Nickname of the player.
     */
    private String username;


    /**
     * Unique identifier of the Player ( starting from 0 ) representing his position on the table.
     */
    private int id;


    /**
     * Total points of the player at the end of each game.
     */
    private int score=0;


    /**
     * Stores the Cards that the Player holds (up to 3 ).
     */
    private ArrayList<Card> hand = new ArrayList<>();


    /**
     * List of cards collected by the player during previous rounds
     */
    private ArrayList<Card> playerPile;



    /**
     *Constructor of the class to initialize the new objects of Player Types(Human, Robot).
     * @param id is the unique identifier of the player in the game.
     * @param  username is the nickname of the player which may be shown in the game.
     */
    public Player( int id , String username ) {

        this.id=id;
        this.username=username;
        this.score=0;
        this.playerPile = new ArrayList<Card>();
        this.hand = new ArrayList<Card>();
    }


    /**
     * @return the unique identifier which is also the position of the player
     */
    public int getId()
    {
        return id;
    }

    /**
     * @return the username of the player
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * @return the score of the player
     */
    public int getPlayerPoints(){
        return score;
    }

    /**
     * Sets the score of the player
     */
    public void setPlayerPoints(int score) { this.score = score; }

    /**
     * @return the hand of the player
     */
    public ArrayList<Card> getHand() { return hand;}

    /**
     * @return the pile of the player
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

    /** Adds a card to player's hand, in the last position
     * @param card the card to be added.
     */
    public void takeCardInHand(Card card) {
        if (this.hand.size()>3)
            throw new IllegalArgumentException("Cannot take more than 3 cards");
        this.hand.add(card);
    }
}