package it.polimi.group06.moveTest;

import it.polimi.group06.domain.Game;

import static it.polimi.group06.domain.Constants.DRAW;
import static it.polimi.group06.domain.Constants.NUMBEROFPLAYERS;

/**
 * Created by denis on 02/11/17.
 */

public class MoveTest {

    public static String moveTest(String configuration, String moveSequence) {

        /* Initialize a new game passing the input configuration to the special constructor
         */
        Game briscola = initializeFromConf(configuration); //Initial configuration must always be a valid one
        String nextConfiguration;               //next visible configuration
        boolean stat = true;                    //print the computed configurations

        try {
            if (!briscola.gameIsOver())
                briscola.playerPlaysCard(briscola.getCurrentPlayer(), Integer.parseInt(moveSequence.substring(0, 1)));

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
                if (stat == true) System.err.println(briscola.getRound() + " - " + nextConfiguration);
                nextConfiguration = moveTest(nextConfiguration, moveSequence.substring(1));
            }
        }
        if (stat == true && moveSequence.length()<2) System.err.println(briscola.getRound() + " - " + nextConfiguration);
        return nextConfiguration;
    }

    /** Static method that creates a Game object using the standard constructor of Game class
     * and selectively replacing fields according to the passed string configuration
     * and parsed by Parser
     * @see Parser
     * @param conf the passed configuration file
     * @return the Game initialized according to String conf parameter
     */
    static Game initializeFromConf(String conf) {
        Game gameFromConf = new Game();
        Parser parser = new Parser(conf);


        /* Replace hands of players
         */
        for (int i=0; i<NUMBEROFPLAYERS; i++)
            gameFromConf.getPlayers()[i].replaceHand(parser.hands()[i]);

        /* Replace pile of players
         */
        for (int i=0; i<NUMBEROFPLAYERS; i++)
            gameFromConf.getPlayers()[i].replacePlayerPile(parser.piles()[i]);


        /* Set the table replacing the briscola and the deck
         */
        gameFromConf.getTable().setTrump(parser.briscola());
        gameFromConf.getTable().getDeck().replaceDeck(parser.deck());

        /* Set the played card on surface
         */
        gameFromConf.getTable().replacePlayedCards(parser.surface());


        /*
         * Set the briscola suit for easier future accesses
         */
        gameFromConf.setBriscolaSuit(parser.trumpSuit());


        /* 20 rounds in a 2-player briscola game
         */
        gameFromConf.setRound(parser.round());

        /* The player who started the turn
         */
        gameFromConf.setStartingPlayer(parser.startingPlayer());

        /* At the beginning of the game the current player is the starting player
         */
        gameFromConf.setCurrentPlayer(parser.currentPlayer());


        return gameFromConf;
    }
}