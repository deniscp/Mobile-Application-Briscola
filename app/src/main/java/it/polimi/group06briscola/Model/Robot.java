package it.polimi.group06briscola.Model;

/**
 * This class inherited from the Player class and it is used when a robot plays against a Human player .
 * A robot hold hand card and based on the rules and AI strategy play card.
 * @author Roza .
 */

public class Robot {

    /**
     * This is a method for robot to play the card in turn based on the logic which has defined in AI Class.
     */
    AI strategy = new  AI();


    /**
     * Constructor of the class to create a robot as a new object in every game .
     * Possibility to initialize the proper AI methods which will be used by each Robot.
     */
    public Robot() {
    }
}
