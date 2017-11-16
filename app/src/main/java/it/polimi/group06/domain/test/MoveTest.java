package it.polimi.group06.domain.test;

import it.polimi.group06briscola.model.Game;

/**
 * Created by denis on 02/11/17.
 */

public class MoveTest {
    public static String moveTest(String configuration, String moveSequence) {

        /* Initialize a new game passing the input configuration to the special constructor
         */
        Game briscola = new Game(configuration); //Initial configuration must always be a valid one
        String nextConfiguration;               //next visible configuration
        boolean stat = true;                    //print the configurations

        try {
            if (!briscola.gameIsOver())
                briscola.playerPlaysCard(briscola.getCurrentPlayer(), Integer.parseInt(moveSequence.substring(0, 1)));

            if (briscola.roundIsOver())
                briscola.newRound();

            if (briscola.gameIsOver()) {
            /*Compute the winner and print the score*/
                int winner = briscola.returnWinner();
                if (winner == -1)
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


    public static void main(String[] argv){
        String conf0 = "0BJBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKS7B..1B3B5B.2B4B6B..";   //New game with ordered deck
        String conf1 = "0B..2B3B5B.1B4B6B..JBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKS7B";
        String conf2 = "0BJBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKSKS7B..1B3B5B.2B4B6B..";   // 41 cards! (2 KS in the deck)
        String conf3 = "0B..2B3B5B.1B4B6B..JBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKS7B7BKS"; // 42 cards!
        String conf4 = "0B..5B.6B.4B3B.JBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKS7B7BKS2B1B"; // 42 cards!
        String conf5 = "1B.5B..6B.4B3B.JBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKS7B7BKS2B1B"; // 42 cards!
        String conf6 = "0B....JBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKS7B1B3B5B2B4B6B.";       // Game over p0-120
        String conf7 = "0B....JBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7G.JGHGKG1S2S3S4S5S6S7SJSHSKS7B1B3B5B2B4B6B";       // Game over DRAW
        String conf8 = "0B5S4G6S2C5GKB7B6CHCHB1GKC5C4B1BHG7C6BJS6G7G4C3C7SJBHS2S3S4S1S2G3BJG5B..JCKG2B.1CKS3G..";
        String conf9 = "0SHC4B6CKS3S2SHS4C2GJS..1SKB3B.6S2BKC.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S.5C1C2C3C7G1BJG6G";

        String moves = "000000000000000000000000000000000011010";
        Game game= new Game();
//        System.out.println( MoveTest.moveTest(moves , game.toConfiguration()) );
//        System.out.println( MoveTest.moveTest(moves , conf7 ) );

        String test = MoveTest.moveTest(conf8, "0011000000000000000000000000000000000000");
        System.out.println("   - " +test);
//        if (! test.equals("1B5GKB7B6CHCHB1GKC5C4B1BHG7C6BJS6G7G4C3C7SJBHS2S3S4S1S2G3BJG5B.KG.4G6S.KS5S2C.3G2B.JC1C") )
//            throw new AssertionError();
//        System.out.println("All the Tests passed");
    }
}