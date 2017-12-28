package it.polimi.group06.moveTest;

import it.polimi.group06.domain.Game;

import static it.polimi.group06.domain.Constants.DRAW;

/**
 * Class containing only two static methods: moveTest and initializeFromConf
 *
 * @author denis
 */

public class MoveTest {

    public static String moveTest(String configuration, String moveSequence) {

        /* Initialize a new game passing the input configuration to the special constructor
         */
        Game briscola = Game.initializeFromConf(configuration); //Initial configuration must always be a valid one
        String nextConfiguration;               //next visible configuration
        boolean stat = true;                    //print the computed configurations

        try {
            if (!briscola.gameIsOver())
                briscola.playerPlaysCard(briscola.getCurrentPlayerPosition(), Integer.parseInt(moveSequence.substring(0, 1)));

            if (briscola.roundIsOver())
                briscola.newRound();

            if (briscola.gameIsOver()) {
            /*Compute the winner and print the score*/
                int winner = briscola.returnWinner();
                if (winner == DRAW)
                    nextConfiguration = "DRAW";
                else
                    nextConfiguration = "WINNER" + winner + briscola.getPlayers()[winner].getPlayerPoints();
            } else
                nextConfiguration = briscola.toConfiguration();

        } catch (IllegalArgumentException e) {
            nextConfiguration = "ERROR: <" + e.getMessage() + ">";
        }


        if (moveSequence.length() > 1) {
            if (nextConfiguration.startsWith("WINNER") || nextConfiguration.startsWith("DRAW"))
                nextConfiguration = "ERROR: <attempt to make a move while game is over>";
            else if (!nextConfiguration.startsWith("ERROR")) {
                if (stat == true)
                    System.err.println(briscola.getRound() + " - " + nextConfiguration);
                nextConfiguration = moveTest(nextConfiguration, moveSequence.substring(1));
            }
        }
        if (stat == true && moveSequence.length() < 2)
            System.err.println(briscola.getRound() + " - " + nextConfiguration);
        return nextConfiguration;
    }
}