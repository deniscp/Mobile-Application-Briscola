package it.polimi.group06briscola.Model;

import java.util.ArrayList;

/**
 * This class defines and holds the basic information of a player in the game.
 * *@author Roza.
 */

abstract class Player {
    /**
     * Username of the player.
     */
    private String username;


    /**
     * ID of the Player from 0 to 1.
     */
    private int id;


    /**
     *Indicates to the total points of the player.
     */
    private int scores=0;


    /**
     * Indicates the tree Cards which the Player has held.
     */
    public Card[] hand = new Card[3];


    /**
     * Indicates to a list of cards stored in pile of the player.
     */
    protected ArrayList<Card> playerPile= new ArrayList<Card>();


    /**
     * @param position shows the position of a card that has been selected by a player to play.
     * @return card position in hand of player.
     */
    int selectCard(int position){
        int positionOfCardInHand;
        return positionOfCardInHand =position;
    }

    /**
     *Constructor of the class to initialize the new objects of Player Types(Human, Robot) .
     */
    public Player( int id , String username , int scores ) {

        this.id=id;
        this.username=username;
        this.scores=scores;
    }


    /**
     * Return the values of constructor's @param (id, username and points).
     */
    public int getId()
    {
        return id;
    }

    public String getUsername()
    {
        return username;
    }

    public int getPlayerPoints(){
        return scores;
    }


}
