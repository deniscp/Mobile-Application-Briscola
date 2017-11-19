package it.polimi.group06.domain;

/**
 * This class is a subclass of Player class .
 * @author Roza.
 */

public class Human extends Player {

    /**
     * Constructor of Human Player which calls the superclass constructor (Player) in order to initialize its objects.
     * @param id is the unique identifier of the player in the game.
     * @param username is the nickname of the player which may be shown in the game.
     */
    public Human(int id, String username) {
        super(id, username);
    }
}