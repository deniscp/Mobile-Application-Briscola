package it.polimi.group06briscola.model;

/**
 * This class is a subclass of Player class .
 * @author Roza.
 */

public class Human extends Player {

    /**
     * Constructor of human class which calls the superclass constructor(Player) in order to initialize its objects .
     */
    public Human(int id, String username, int scores) {
        super(id, username, scores);
    }
}