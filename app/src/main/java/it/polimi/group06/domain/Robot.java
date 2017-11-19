package it.polimi.group06.domain;

/**
 * This class inherited from the Player class and it is used when a robot plays against a Human player .
 * A robot hold hand card and based on the rules and AI strategy play card.
 *
 * @author Roza .
 */

public class Robot extends Player {

    /**
     * This is a method for robot to play the card in turn based on the logic which has been defined in AI Class.
     */
    AI strategy;

    /**
     * Constructor of Robot Player which calls the superclass constructor (Player) in order to initialize its objects,
     * and sets the appropriate strategy of the AI class method
     */
    public Robot(int id, String username) {
        super(id, username);
        this.strategy = new AI();
    }
}