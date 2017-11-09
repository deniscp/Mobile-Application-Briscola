package it.polimi.group06briscola.model.test;

import it.polimi.group06briscola.model.Game;
import it.polimi.group06briscola.model.Rules;

/**
 * Created by denis on 02/11/17.
 */

public class MoveTest {
    public static String moveTest(String moveSequence, String configuration) {

        /* Initialize a new game passing the input configuration to the special constructor
         */
        Game briscola = new Game(configuration); //Initial configuration must always be a valid one
        String nextConfiguration;               //next visible configuration


        try {
            if( ! briscola.gameIsOver() )
                briscola.playerPlaysCard( briscola.getCurrentPlayer(), Integer.parseInt(moveSequence.substring(0, 1)));

            if(briscola.roundIsOver())
                briscola.newRound();

            if (briscola.gameIsOver()) {
            /*Compute the winner and print the score*/
                int winner = briscola.returnWinner();
                if (winner == -1)
                    nextConfiguration = "DRAW";
                else
                    nextConfiguration = "WINNER" + winner + briscola.getPlayers()[winner].getPlayerPoints();

                System.out.println(briscola.getRound() + "   " + briscola.toConfiguration());
            }
            else
                nextConfiguration = briscola.toConfiguration();

        } catch (IllegalArgumentException e) {
            nextConfiguration = "ERROR: <" + e.getMessage() + ">";
        }


        if (moveSequence.length() > 1) {
            if (nextConfiguration.startsWith("WINNER") || nextConfiguration.startsWith("DRAW") )
                nextConfiguration = "ERROR: <attempt to make a move while game is over>";
            else if (! nextConfiguration.startsWith("ERROR")){
                System.out.println(briscola.getRound() + " - " + nextConfiguration);
                nextConfiguration = moveTest(moveSequence.substring(1), nextConfiguration);
            }
        }

        return nextConfiguration;
    }


    public static void main(String[] argv){
        String conf0 = "0BJBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKS7B..1B3B5B.2B4B6B..";   //New game with ordered deck
        String conf1 = "0B..2B3B5B.1B4B6B..JBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKS7B";
        String conf2 = "0BJBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKSKS7B..1B3B5B.2B4B6B..";   // 41 cards! (2 KS in the deck)
        String conf3 = "0B..2B3B5B.1B4B6B..JBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKS7B7BKS"; // 42 cards!
        String conf4 = "0B..5B.6B.4B3B.JBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKS7B7BKS2B1B"; // 42 cards!
        String conf5 = "1B.5B..6B.4B3B.JBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKS7B7BKS2B1B"; // 42 cards!
        String conf6 = "0B....JBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKS7B1B3B5B2B4B6B.";       // Game over p0-120
        String conf7 = "0B....JBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7G.JGHGKG1S2S3S4S5S6S7SJSHSKS7B1B3B5B2B4B6B";       // Game over DRAW


        String moves = "0000000000000000000000000000000000110100";
        Game gioco= new Game();
//        System.out.println( MoveTest.moveTest(moves , gioco.toConfiguration()) );
        System.out.println( MoveTest.moveTest(moves , conf7 ) );
    }
}