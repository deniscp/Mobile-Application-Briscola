package it.polimi.group06;

import org.junit.Test;

import static org.junit.Assert.*;

import it.polimi.group06.domain.Game;
import it.polimi.group06.moveTest.MoveTest;

/**
 * Class that tests the moveTest method with various configurations.
 * See comments below for more details about each configuration.
 */
public class MoveTestTest {

    @Test
    public void moveTest() throws Exception {
        String inputConf;
        String inputMoves;
        String outputConf;


        /* Some tests for the moveTest method to test the domain as a whole
         * Author: Roza
         */

        /* PLAYER0 PLAYS THE CARD #0(HC)*/
        inputConf = "0C5GKB7B6C..HCHB1B.HG7C6B.JS6G7G4C3C7SJBHS2S3S4S1GKC5C.4B1S2G3BJG5BJCKG2B1CKS3G5S4G6S2C";
        inputMoves = "0";
        outputConf = "1C5GKB7B6C.HC.HB1B.HG7C6B.JS6G7G4C3C7SJBHS2S3S4S1GKC5C.4B1S2G3BJG5BJCKG2B1CKS3G5S4G6S2C";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER1 PLAYS THE CARD #1(7C)*/
        inputConf = "1C5GKB7B6C.HC.HB1B.HG7C6B.JS6G7G4C3C7SJBHS2S3S4S1GKC5C.4B1S2G3BJG5BJCKG2B1CKS3G5S4G6S2C";
        inputMoves = "1";
        outputConf = "0C7B6C..HB1B5G.HG6BKB.JS6G7G4C3C7SJBHS2S3S4S1GKC5CHC7C.4B1S2G3BJG5BJCKG2B1CKS3G5S4G6S2C";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* COMBINATION OF THE LAST 10 MOVEMENTS
        PLAYER0 PLAYS THE CARD #0 (HC)
        PLAYER1 PLAYS THE CARD #1(7C)*
        WINNER? PLAYER0 (TRUMP,VS TRUMP Then THE HIGHER VALUE WINS)
        PLAYER 0 TAKES (5G) FROM THE DECK
        PLAYER 1 TAKES (KB) FROM THE DECK
        Player 0 PLAYS CARD #0 (HB)
        Player 1 PLAYS CARD #1 (6B)
        WINNER? PLAYER0 (SAME SUITS THEN HIGHER VALUE WILL WIN)
        HERE IS THE LAST CARD WHICH IS THE TRUMP AND GOES TO THE SECOND PLAYER
        PLAYER 0 TAKES (7B) FROM THE DECK
        PLAYER 1 TAKES (6C) FROM THE DECK
        PLAYER0 PLAYS THE CARD #1 (5G)
        PLAYER1 PLAYS THE CARD #0 (HG)
        WINNER? PLAYER1 (SAME SUITS THEN HIGHER VALUE WILL WIN)
        PLAYER1 PLAYS THE CARD #0 (KB)
        PLAYER0 PLAYS THE CARD #1 (7B)
        WINNER? PLAYER1 (SAME SUITS THEN HIGHER VALUE WILL WIN)
        PLAYER1 PLAYS THE CARD #0 (6C)
        PLAYER0 PLAYS THE CARD #0 (1B)
        WINNER? PLAYER1 (TRUMP,VS ANOTHER SUIT THEN TRUMP WINS)		HERE THE FINAL WINNER ALSO SHOULD BE UPDATED
        PLAYER1 WON THE GAME WITH THE OVERALL SCORE 72*/
        inputConf = "0C5GKB7B6C..HCHB1B.HG7C6B.JS6G7G4C3C7SJBHS2S3S4S1GKC5C.4B1S2G3BJG5BJCKG2B1CKS3G5S4G6S2C";
        inputMoves = "0101100100";
        outputConf = "WINNER172";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* COMBINATION OF THE LAST 4 MOVEMENTS
        PLAYER1 PLAYS THE CARD #1 (JC)
        PLAYER0 PLAYS THE CARD #1 (3G)
        WINNER? PLAYER0 (TRUMP,VS ANOTHER SUIT THEN TRUMP WINS)
        Player 0 PLAYS CARD #0 (6C)
        Player 0 PLAYS CARD #0 (1B)
        WINNER? PLAYER0( NO TRUMP, DIFFERENT SUITS THEN FIRST PLAYER WILL WIN)
		WINNER? PLAYER1 HERE THE FINAL WINNER ALSO SHOULD BE UPDATED
        PLAYER0 WON THE GAME WITH THE OVERALL SCORE 71*/
        inputConf = "1G..6C3G.1BJC.JS6G7G4C3C7SJBHS2S3S4S1GKC5CHC7CHB6B.4B1S2G3BJG5BKG2B1CKS5S4G6S2C5GHGKB7B";
        inputMoves = "1100";
        outputConf = "WINNER071";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));



        /* Some tests for the moveTest method
         * Author: Denis
         */

        inputConf = "0SHC4B6CKS3S2SHS4C2GJS..1SKB3B.6S2BKC.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S.5C1C2C3C7G1BJG6G";
        inputMoves = "0000000000000000000000000000000000";
        outputConf = "ERROR: <attempt to make a move while game is over>";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        inputConf = "0BJBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKS7B..1B3B5B.2B4B6B..";   //New game with ordered deck
        inputMoves = "0000000000000000000000000000000000000000";
        outputConf = "WINNER061";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* Commented after we default to shuffle the deck in the standard constructor */
//        inputConf = new Game().toConfiguration();                                                //New game with ordered deck
//        inputMoves = "0000000000000000000000000000000000000000";
//        outputConf = "WINNER061";
//        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        inputConf = "0B..2B3B5B.1B4B6B..JBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKS7B";
        inputMoves = "00";
        outputConf = "1B..3B5B.4B6B..JBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKS7B2B1B";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        inputConf = "0B....JBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7G.JGHGKG1S2S3S4S5S6S7SJSHSKS7B1B3B5B2B4B6B";       // Game over DRAW
        inputMoves = "0";
        outputConf = "DRAW";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        inputConf = "0B....JBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKS7B1B3B5B2B4B6B.";       // Game over p0-120
        inputMoves = "0";
        outputConf = "WINNER0120";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        inputConf = "0B5S4G6S2C5GKB7B6CHCHB1GKC5C4B1BHG7C6BJS6G7G4C3C7SJBHS2S3S4S1S2G3BJG5B..JCKG2B.1CKS3G..";
        inputMoves = "0011000000000000000000000000000000000000";
        outputConf = "WINNER183";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        inputConf = "0B5S4G6S2C5GKB7B6CHCHB1GKC5C4B1BHG7C6BJS6G7G4C3C7SJBHS2S3S4S1S2G3BJG5B..JCKG2B.1CKS3G..";
        inputMoves = "00110";
        outputConf = "1B5GKB7B6CHCHB1GKC5C4B1BHG7C6BJS6G7G4C3C7SJBHS2S3S4S1S2G3BJG5B.KG.4G6S.KS5S2C.3G2B.JC1C";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));



        /*
         * Author: Timo
         */

        inputConf = "1S3B5B6BJBHBKB1S2S3S4S6S7SJSHSKS4C5C6C7CHCKC1G2G3G4G5G7GJG..2B2C1C.3CHGKG.5S7BJC6G.1B4B";
        inputMoves = "10";
        outputConf = "1S6BJBHBKB1S2S3S4S6S7SJSHSKS4C5C6C7CHCKC1G2G3G4G5G7GJG..2C1C5B.3CKG3B.5S7BJC6G.1B4BHG2B";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        inputConf = "1B1B3B4B6BJBHBKB1S3S4S5S6SJSHSKS1C3C4C5C6C7CJCHCKC1G2G3G7G4G5G6GJGHGKG.5B.7B7S.2S2B2C..";
        inputMoves = "1";
        outputConf = "0B4B6BJBHBKB1S3S4S5S6SJSHSKS1C3C4C5C6C7CJCHCKC1G2G3G7G4G5G6GJGHGKG..7B7S1B.2S2C3B.5B2B.";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));



        /*complete test for one entire game
         * Author: Mahsa Shekari
         */

        /* PLAYER 0 PLAYS THE CARD #1(JC)  JC SHOULD BE ELIMINATED FROM THE HAND OF PLAYER0 AND BE ADDED TO THE SURFACE AND THE ORDER OF THE HAND OF PLAYER 0 SHOULD BE ORGANISED */
        inputConf = "0S5C1CJB3G7G1B1S6B4S5BHGJG7C7B2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS..KGJC5S.1G4G5G..";
        inputMoves = "1";
        outputConf = "1S5C1CJB3G7G1B1S6B4S5BHGJG7C7B2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS.JC.KG5S.1G4G5G..";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER 1 PLAYS THE CARD #1 (4G)  4G SHOULD BE ELIMINATED FROM THE HAND OF PLAYER1 AND BE ADDED TO THE SURFACE AND THE ORDER OF THE HAND OF PLAYER 1 SHOULD BE ORGANISED
         NOTE: STILL PLAYER SHOULD NOT BE CHANGED
         NOW WE NEED TO DETERMINE WHO IS THE WINNER. AS FAR AS, THE PLAYED CARDS ARE NOT TRUMP AND NOT THE SAME SUIT THEN PLAYER 0(FIRST PLAYER) WILL COLLECT AND THE SURFACE CARDS WILL BE ELIMINATED FROM THE SURFACE AND BE ADDED TO THE PILE0 AND THE CURRENT PLAYER BECOME PLAYER0
         NOW THE PLAYER0 NEEDS TO TAKE ONE CARD FROM THE DECK (5C)AND ADD IT TO THE HAND POSITION #2  PLAYER SHOULD NOT CHANGE STILL
         NOW THE PLAYER1 NEEDS TO TAKE ONE CARD FROM THE DECK (1C)AND ADD IT TO THE HAND POSITION #2	*/
        inputConf = "1S5C1CJB3G7G1B1S6B4S5BHGJG7C7B2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS.JC.KG5S.1G4G5G..";
        inputMoves = "1";
        outputConf = "0SJB3G7G1B1S6B4S5BHGJG7C7B2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS..KG5S5C.1G5G1C.JC4G.";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER0 PLAYS THE CARD #0(KG)*/
        inputConf = "0SJB3G7G1B1S6B4S5BHGJG7C7B2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS..KG5S5C.1G5G1C.JC4G.";
        inputMoves = "0";
        outputConf = "1SJB3G7G1B1S6B4S5BHGJG7C7B2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS.KG.5S5C.1G5G1C.JC4G.";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER1 PLAYS THE CARD #1(5G)
        WINNER? PLAYER0(NO TRUMP, SAME SUIT THEN HIGHER VALUE WILL WIN)
        PLAYER0 TAKES JB FROM THE DECK
        PLAYER1 TAKES 3G FROM THE DECK*/
        inputConf = "1SJB3G7G1B1S6B4S5BHGJG7C7B2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS.KG.5S5C.1G5G1C.JC4G.";
        inputMoves = "1";
        outputConf = "0S7G1B1S6B4S5BHGJG7C7B2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS..5S5CJB.1G1C3G.JC4GKG5G.";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER0 PLAYS THE CARD #1 (5C)*/
        inputConf = "0S7G1B1S6B4S5BHGJG7C7B2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS..5S5CJB.1G1C3G.JC4GKG5G.";
        inputMoves = "1";
        outputConf = "1S7G1B1S6B4S5BHGJG7C7B2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS.5C.5SJB.1G1C3G.JC4GKG5G.";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER1 PLAYS THE CARD #1(1C)
        WINNER? PLAYER1 (NO TRUMP, SAME SUIT THE HIGHER VALUE WILL WIN)
        PLAYER 1 TAKES (7G) FROM THE DECK AND ADD IT TO THE HAND
        PLAYER 0 TAKES (1B) FROM THE DECK AND ADD IT TO THE HAND*/
        inputConf = "1S7G1B1S6B4S5BHGJG7C7B2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS.5C.5SJB.1G1C3G.JC4GKG5G.";
        inputMoves = "1";
        outputConf = "1S1S6B4S5BHGJG7C7B2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS..5SJB1B.1G3G7G.JC4GKG5G.5C1C";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER1 PLAYS THE CARD #0 (1G)*/
        inputConf = "1S1S6B4S5BHGJG7C7B2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS..5SJB1B.1G3G7G.JC4GKG5G.5C1C";
        inputMoves = "0";
        outputConf = "0S1S6B4S5BHGJG7C7B2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS.1G.5SJB1B.3G7G.JC4GKG5G.5C1C";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER0 PLAYS THE CARD #0 (5S)
        WINNER? PLAYER0 (TRUMP AND ANOTHER CARD"EVEN WITH HIGHER VALUE" THEN THE TRUMP WILL WIN)
        PLAYER 0 TAKES (1S) FROM THE DECK AND ...
        PLAYER 1 TAKES (6B) FROM THE DECK AND ...*/
        inputConf = "0S1S6B4S5BHGJG7C7B2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS.1G.5SJB1B.3G7G.JC4GKG5G.5C1C";
        inputMoves = "0";
        outputConf = "0S4S5BHGJG7C7B2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS..JB1B1S.3G7G6B.JC4GKG5G1G5S.5C1C";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER0 PLAYS THE CARD #0 (JB)*/
        inputConf = "0S4S5BHGJG7C7B2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS..JB1B1S.3G7G6B.JC4GKG5G1G5S.5C1C";
        inputMoves = "0";
        outputConf = "1S4S5BHGJG7C7B2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS.JB.1B1S.3G7G6B.JC4GKG5G1G5S.5C1C";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER1 PLAYS THE CARD #0 (3G)
        WINNER? PLAYER0 (NO TRUMP, DIFFERENT SUITS. FIRST PLAYER WILL WIN)
        PLAYER 0 TAKES (4S) FROM THE DECK AND ...
        PLAYER 1 TAKES (5B) FROM THE DECK AND ...*/
        inputConf = "1S4S5BHGJG7C7B2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS.JB.1B1S.3G7G6B.JC4GKG5G1G5S.5C1C";
        inputMoves = "0";
        outputConf = "0SHGJG7C7B2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS..1B1S4S.7G6B5B.JC4GKG5G1G5SJB3G.5C1C";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER0 PLAYS THE CARD #2 (4S)*/
        inputConf = "0SHGJG7C7B2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS..1B1S4S.7G6B5B.JC4GKG5G1G5SJB3G.5C1C";
        inputMoves = "2";
        outputConf = "1SHGJG7C7B2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS.4S.1B1S.7G6B5B.JC4GKG5G1G5SJB3G.5C1C";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER1 PLAYS THE CARD #1 (6B)
        WINNER? PLAYER0 (TRUMP VS ANOTHER)
        PLAYER O TAKES (HG) FROM THE DECK AND ...
        PLAYER1 TAKES (JG) FROM THE DECK AND...*/
        inputConf = "1SHGJG7C7B2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS.4S.1B1S.7G6B5B.JC4GKG5G1G5SJB3G.5C1C";
        inputMoves = "1";
        outputConf = "0S7C7B2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS..1B1SHG.7G5BJG.JC4GKG5G1G5SJB3G4S6B.5C1C";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER0 PLAYS THE CARD #2 (HG)*/
        inputConf = "0S7C7B2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS..1B1SHG.7G5BJG.JC4GKG5G1G5SJB3G4S6B.5C1C";
        inputMoves = "2";
        outputConf = "1S7C7B2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS.HG.1B1S.7G5BJG.JC4GKG5G1G5SJB3G4S6B.5C1C";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER1 PLAYS THE CARD #1 (5B)
        WINNER? PLAYER0 (NO TRUMP, DIFFERENT SUITS THEN FIRST PLAYER WILL WIN)
        PLAYER 0 TAKES (7C) FROM THE DECK AND ...
        PLAYER 1 TAKES (7B) FROM THE DECK AND ...*/
        inputConf = "1S7C7B2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS.HG.1B1S.7G5BJG.JC4GKG5G1G5SJB3G4S6B.5C1C";
        inputMoves = "1";
        outputConf = "0S2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS..1B1S7C.7GJG7B.JC4GKG5G1G5SJB3G4S6BHG5B.5C1C";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER0 PLAYS THE CARD #2 (7C)*/
        inputConf = "0S2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS..1B1S7C.7GJG7B.JC4GKG5G1G5SJB3G4S6BHG5B.5C1C";
        inputMoves = "2";
        outputConf = "1S2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS.7C.1B1S.7GJG7B.JC4GKG5G1G5SJB3G4S6BHG5B.5C1C";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER1 PLAYS THE CARD #2 (7B)
        WINNER? PLAYER0 (NO TRUMP, DIFFERENT SUITS EVEN WITH THE SAME VALUES THEN FIRST PLAYER WILL WIN)
        PLAYER 0 TAKES (2C) FROM THE DECK AND ...
        PLAYER 1 TAKES (3C) FROM THE DECK AND ...*/
        inputConf = "1S2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS.7C.1B1S.7GJG7B.JC4GKG5G1G5SJB3G4S6BHG5B.5C1C";
        inputMoves = "2";
        outputConf = "0S6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS..1B1S2C.7GJG3C.JC4GKG5G1G5SJB3G4S6BHG5B7C7B.5C1C";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /*PLAYER0 PLAYS THE CARD #2 (2C)*/
        inputConf = "0S6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS..1B1S2C.7GJG3C.JC4GKG5G1G5SJB3G4S6BHG5B7C7B.5C1C";
        inputMoves = "2";
        outputConf = "1S6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS.2C.1B1S.7GJG3C.JC4GKG5G1G5SJB3G4S6BHG5B7C7B.5C1C";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));


        /*PLAYER1 PLAYS THE CARD #2 (3C)
        WINNER? PLAYER1 (NO TRUMP, SAME SUITS THEN HIGHER VALUE WILL WIN)
        PLAYER 1 TAKES (6S) FROM THE DECK AND ...
        PLAYER 1 TAKES (7S) FROM THE DECK AND ...*/
        inputConf = "1S6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS.2C.1B1S.7GJG3C.JC4GKG5G1G5SJB3G4S6BHG5B7C7B.5C1C";
        inputMoves = "2";
        outputConf = "1S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS..1B1S7S.7GJG6S.JC4GKG5G1G5SJB3G4S6BHG5B7C7B.5C1C2C3C";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER1 PLAYS THE CARD #0 (7G)*/
        inputConf = "1S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS..1B1S7S.7GJG6S.JC4GKG5G1G5SJB3G4S6BHG5B7C7B.5C1C2C3C";
        inputMoves = "0";
        outputConf = "0S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS.7G.1B1S7S.JG6S.JC4GKG5G1G5SJB3G4S6BHG5B7C7B.5C1C2C3C";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER0 PLAYS THE CARD #0 (1B)
        WINNER? PLAYER1 (NO TRUMP, DIFFERENT SUITS THEN FIRST PLAYER WILL WIN)
        PLAYER 1 TAKES (2B) FROM THE DECK AND ...
        PLAYER 0 TAKES (6G) FROM THE DECK AND ...*/
        inputConf = "0S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS.7G.1B1S7S.JG6S.JC4GKG5G1G5SJB3G4S6BHG5B7C7B.5C1C2C3C";
        inputMoves = "0";
        outputConf = "1SHBKB3BKCHC4B6CKS3S2SHS4C2GJS..1S7S6G.JG6S2B.JC4GKG5G1G5SJB3G4S6BHG5B7C7B.5C1C2C3C7G1B";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

         /* PLAYER1 PLAYS THE CARD #0 (JG)*/
        inputConf = "1SHBKB3BKCHC4B6CKS3S2SHS4C2GJS..1S7S6G.JG6S2B.JC4GKG5G1G5SJB3G4S6BHG5B7C7B.5C1C2C3C7G1B";
        inputMoves = "0";
        outputConf = "0SHBKB3BKCHC4B6CKS3S2SHS4C2GJS.JG.1S7S6G.6S2B.JC4GKG5G1G5SJB3G4S6BHG5B7C7B.5C1C2C3C7G1B";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER0 PLAYS THE CARD #2 (6G)
        WINNER? PLAYER1 (NO TRUMP, SAME SUITS THEN HIGHER VALUE WILL WIN)
        PLAYER 1 TAKES (HB) FROM THE DECK AND ...
        PLAYER 0 TAKES (KB) FROM THE DECK AND ...*/
        inputConf = "0SHBKB3BKCHC4B6CKS3S2SHS4C2GJS.JG.1S7S6G.6S2B.JC4GKG5G1G5SJB3G4S6BHG5B7C7B.5C1C2C3C7G1B";
        inputMoves = "2";
        outputConf = "1S3BKCHC4B6CKS3S2SHS4C2GJS..1S7SKB.6S2BHB.JC4GKG5G1G5SJB3G4S6BHG5B7C7B.5C1C2C3C7G1BJG6G";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER1 PLAYS THE CARD #2 (HB)*/
        inputConf = "1S3BKCHC4B6CKS3S2SHS4C2GJS..1S7SKB.6S2BHB.JC4GKG5G1G5SJB3G4S6BHG5B7C7B.5C1C2C3C7G1BJG6G";
        inputMoves = "2";
        outputConf = "0S3BKCHC4B6CKS3S2SHS4C2GJS.HB.1S7SKB.6S2B.JC4GKG5G1G5SJB3G4S6BHG5B7C7B.5C1C2C3C7G1BJG6G";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER0 PLAYS THE CARD #1 (7S)
        WINNER? PLAYER0 (TRUMP,VS ANOTHER SUIT THEN TRUMP WINS)
        PLAYER 0 TAKES (3B) FROM THE DECK AND ...
        PLAYER 1 TAKES (KC) FROM THE DECK AND ...*/
        inputConf = "0S3BKCHC4B6CKS3S2SHS4C2GJS.HB.1S7SKB.6S2B.JC4GKG5G1G5SJB3G4S6BHG5B7C7B.5C1C2C3C7G1BJG6G";
        inputMoves = "1";
        outputConf = "0SHC4B6CKS3S2SHS4C2GJS..1SKB3B.6S2BKC.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S.5C1C2C3C7G1BJG6G";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER0 PLAYS THE CARD #2 (3B)*/
        inputConf = "0SHC4B6CKS3S2SHS4C2GJS..1SKB3B.6S2BKC.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S.5C1C2C3C7G1BJG6G";
        inputMoves = "2";
        outputConf = "1SHC4B6CKS3S2SHS4C2GJS.3B.1SKB.6S2BKC.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S.5C1C2C3C7G1BJG6G";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER1 PLAYS THE CARD #0 (6S)
        WINNER? PLAYER1 (TRUMP,VS ANOTHER SUIT THEN TRUMP WINS)
        PLAYER 1 TAKES (HC) FROM THE DECK AND ...
        //PLAYER 0 TAKES (4B) FROM THE DECK AND ...	 */
        inputConf = "1SHC4B6CKS3S2SHS4C2GJS.3B.1SKB.6S2BKC.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S.5C1C2C3C7G1BJG6G";
        inputMoves = "0";
        outputConf = "1S6CKS3S2SHS4C2GJS..1SKB4B.2BKCHC.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S.5C1C2C3C7G1BJG6G3B6S";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER1 PLAYS THE CARD #1 (KC)*/
        inputConf = "1S6CKS3S2SHS4C2GJS..1SKB4B.2BKCHC.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S.5C1C2C3C7G1BJG6G3B6S";
        inputMoves = "1";
        outputConf = "0S6CKS3S2SHS4C2GJS.KC.1SKB4B.2BHC.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S.5C1C2C3C7G1BJG6G3B6S";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER0 PLAYS THE CARD #1 (KB)
        WINNER? PLAYER1 (NO TRUMP,DIFFERENT SUITS "EVEN SAME VALUES" THEN FIRST PLAYER WINS)
        PLAYER 1 TAKES (6C) FROM THE DECK AND ...
        PLAYER 0 TAKES (KS) FROM THE DECK AND ...*/
        inputConf = "0S6CKS3S2SHS4C2GJS.KC.1SKB4B.2BHC.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S.5C1C2C3C7G1BJG6G3B6S";
        inputMoves = "1";
        outputConf = "1S3S2SHS4C2GJS..1S4BKS.2BHC6C.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S.5C1C2C3C7G1BJG6G3B6SKCKB";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER1 PLAYS THE CARD #0 (2B)*/
        inputConf = "1S3S2SHS4C2GJS..1S4BKS.2BHC6C.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S.5C1C2C3C7G1BJG6G3B6SKCKB";
        inputMoves = "0";
        outputConf = "0S3S2SHS4C2GJS.2B.1S4BKS.HC6C.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S.5C1C2C3C7G1BJG6G3B6SKCKB";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER0 PLAYS THE CARD #0 (1S)
        WINNER? PLAYER0 (TRUMP,VS ANOTHER SUIT THEN TRUMP WINS)
        PLAYER 0 TAKES (3S) FROM THE DECK AND ...
        PLAYER 1 TAKES (2S) FROM THE DECK AND ...*/
        inputConf = "0S3S2SHS4C2GJS.2B.1S4BKS.HC6C.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S.5C1C2C3C7G1BJG6G3B6SKCKB";
        inputMoves = "0";
        outputConf = "0SHS4C2GJS..4BKS3S.HC6C2S.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S2B1S.5C1C2C3C7G1BJG6G3B6SKCKB";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER0 PLAYS THE CARD #0 (4B)*/
        inputConf = "0SHS4C2GJS..4BKS3S.HC6C2S.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S2B1S.5C1C2C3C7G1BJG6G3B6SKCKB";
        inputMoves = "0";
        outputConf = "1SHS4C2GJS.4B.KS3S.HC6C2S.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S2B1S.5C1C2C3C7G1BJG6G3B6SKCKB";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER1 PLAYS THE CARD #1 (6C
        WINNER? PLAYER0 (NO TRUMP,DIFFERENT SUITS THEN FIRST PLAYER WINS)
        PLAYER 0 TAKES (HS) FROM THE DECK AND ...
        PLAYER 1 TAKES (4C) FROM THE DECK AND ...*/
        inputConf = "1SHS4C2GJS.4B.KS3S.HC6C2S.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S2B1S.5C1C2C3C7G1BJG6G3B6SKCKB";
        inputMoves = "1";
        outputConf = "0S2GJS..KS3SHS.HC2S4C.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S2B1S4B6C.5C1C2C3C7G1BJG6G3B6SKCKB";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER0 PLAYS THE CARD #2 (HS)*/
        inputConf = "0S2GJS..KS3SHS.HC2S4C.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S2B1S4B6C.5C1C2C3C7G1BJG6G3B6SKCKB";
        inputMoves = "2";
        outputConf = "1S2GJS.HS.KS3S.HC2S4C.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S2B1S4B6C.5C1C2C3C7G1BJG6G3B6SKCKB";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER1 PLAYS THE CARD #2 (4C)
        WINNER? PLAYER0 (TRUMP,VS ANOTHER SUIT THEN TRUMP WINS)
        PLAYER 0 TAKES (2G) FROM THE DECK AND ...
        PLAYER 1 TAKES (JS) FROM THE DECK AND ...
        HERE IS THE LAST CARD WHICH IS THE TRUMP AND GOES TO THE SECOND PLAYER */
        inputConf = "1S2GJS.HS.KS3S.HC2S4C.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S2B1S4B6C.5C1C2C3C7G1BJG6G3B6SKCKB";
        inputMoves = "2";
        outputConf = "0S..KS3S2G.HC2SJS.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S2B1S4B6CHS4C.5C1C2C3C7G1BJG6G3B6SKCKB";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER0 PLAYS THE CARD #2 (2G)*/
        inputConf = "0S..KS3S2G.HC2SJS.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S2B1S4B6CHS4C.5C1C2C3C7G1BJG6G3B6SKCKB";
        inputMoves = "2";
        outputConf = "1S.2G.KS3S.HC2SJS.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S2B1S4B6CHS4C.5C1C2C3C7G1BJG6G3B6SKCKB";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER1 PLAYS THE CARD #2 (JS)
        WINNER? PLAYER1 (TRUMP,VS ANOTHER SUIT THEN TRUMP WINS)*/
        inputConf = "1S.2G.KS3S.HC2SJS.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S2B1S4B6CHS4C.5C1C2C3C7G1BJG6G3B6SKCKB";
        inputMoves = "2";
        outputConf = "1S..KS3S.HC2S.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S2B1S4B6CHS4C.5C1C2C3C7G1BJG6G3B6SKCKB2GJS";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER1 PLAYS THE CARD #1 (2S) */
        inputConf = "1S..KS3S.HC2S.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S2B1S4B6CHS4C.5C1C2C3C7G1BJG6G3B6SKCKB2GJS";
        inputMoves = "1";
        outputConf = "0S.2S.KS3S.HC.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S2B1S4B6CHS4C.5C1C2C3C7G1BJG6G3B6SKCKB2GJS";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* 	PLAYER0 PLAYS THE CARD #0 (KS)
        WINNER? PLAYER0 (TRUMP,VS TRUMP THE HIGHER VALUE WINS) */
        inputConf = "0S.2S.KS3S.HC.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S2B1S4B6CHS4C.5C1C2C3C7G1BJG6G3B6SKCKB2GJS";
        inputMoves = "0";
        outputConf = "0S..3S.HC.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S2B1S4B6CHS4C2SKS.5C1C2C3C7G1BJG6G3B6SKCKB2GJS";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER0 PLAYS THE CARD #0 (3S)*/
        inputConf = "0S..3S.HC.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S2B1S4B6CHS4C2SKS.5C1C2C3C7G1BJG6G3B6SKCKB2GJS";
        inputMoves = "0";
        outputConf = "1S.3S..HC.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S2B1S4B6CHS4C2SKS.5C1C2C3C7G1BJG6G3B6SKCKB2GJS";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* PLAYER1 PLAYS THE CARD #0 (HC)
        WINNER? PLAYER0 (TRUMP,VS ANOTHER SUIT THEN TRUMP WINS)		HERE THE FINAL WINNER ALSO SHOULD BE UPDATED
        PLAYER0 WON THE GAME WITH THE OVERALL SCORE 66*/
        inputConf = "1S.3S..HC.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S2B1S4B6CHS4C2SKS.5C1C2C3C7G1BJG6G3B6SKCKB2GJS";
        inputMoves = "0";
        outputConf = "WINNER066";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* INVALID MOVEMENT BECAUSE OF NOT HAVING THE CARD#2*/
        inputConf = "1S..KS3S.HC2S.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S2B1S4B6CHS4C.5C1C2C3C7G1BJG6G3B6SKCKB2GJS";
        inputMoves = "2";
        outputConf = "ERROR: <Selected Card is not available>";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* INVALID MOVEMENT BECAUSE OF NOT HAVING CARD#1*/
        inputConf = "0S..3S.HC.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S2B1S4B6CHS4C2SKS.5C1C2C3C7G1BJG6G3B6SKCKB2GJS";
        inputMoves = "1";
        outputConf = "ERROR: <Selected Card is not available>";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* DRAW CONFIGURATION*/
        inputConf = "0S....JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S2B1S4B6C5C4C2SKS3S2C.HS1CHC3C7G1BJG6G3B6SKCKB2GJS";
        inputMoves = "";
        outputConf = "DRAW";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* A complete game test from the begining till the end with full list of movements */
        inputConf = "0S5C1CJB3G7G1B1S6B4S5BHGJG7C7B2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS..KGJC5S.1G4G5G..";
        inputMoves = "1101110000212122220002212011000122221000";
        outputConf = "WINNER066";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* COMBINATION OF 6 MOVEMENTS
         PLAYER1 PLAYS THE CARD #0 (JG)
        PLAYER0 PLAYS THE CARD #2 (6G)
        WINNER? PLAYER1 (NO TRUMP, SAME SUITS THEN HIGHER VALUE WILL WIN)
        PLAYER 1 TAKES (HB) FROM THE DECK AND ...
        PLAYER 0 TAKES (KB) FROM THE DECK AND ...
        PLAYER1 PLAYS THE CARD #2 (HB)
        PLAYER0 PLAYS THE CARD #2 (3B)
        PLAYER1 PLAYS THE CARD #0 (6S)
        WINNER? PLAYER1 (TRUMP,VS ANOTHER SUIT THEN TRUMP WINS)
        PLAYER 1 TAKES (HC) FROM THE DECK AND ...
        PLAYER 0 TAKES (4B) FROM THE DECK AND ...	 */
        inputConf = "1SHBKB3BKCHC4B6CKS3S2SHS4C2GJS..1S7S6G.JG6S2B.JC4GKG5G1G5SJB3G4S6BHG5B7C7B.5C1C2C3C7G1B";
        inputMoves = "022120";
        outputConf = "1S6CKS3S2SHS4C2GJS..1SKB4B.2BKCHC.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S.5C1C2C3C7G1BJG6G3B6S";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* COMBINATION OF 4 MOVEMENTS
        PLAYER0 PLAYS THE CARD #2 (7C)
        PLAYER1 PLAYS THE CARD #2 (7B)
        WINNER? PLAYER0 (NO TRUMP, DIFFERENT SUITS EVEN WITH THE SAME VALUES THEN FIRST PLAYER WILL WIN)
        PLAYER 0 TAKES (2C) FROM THE DECK AND ...
        PLAYER 1 TAKES (3C) FROM THE DECK AND ...
        PLAYER0 PLAYS THE CARD #2 (2C)
        PLAYER1 PLAYS THE CARD #2 (3C)
        WINNER? PLAYER1 (NO TRUMP, SAME SUITS THEN HIGHER VALUE WILL WIN)
        PLAYER 1 TAKES (6S) FROM THE DECK AND ...
        PLAYER 1 TAKES (7S) FROM THE DECK AND ...*/
        inputConf = "0S2C3C6S7S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS..1B1S7C.7GJG7B.JC4GKG5G1G5SJB3G4S6BHG5B.5C1C";
        inputMoves = "2222";
        outputConf = "1S2B6GHBKB3BKCHC4B6CKS3S2SHS4C2GJS..1B1S7S.7GJG6S.JC4GKG5G1G5SJB3G4S6BHG5B7C7B.5C1C2C3C";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));

        /* COMBINATION OF THE LAST 7 MOVEMENTS
        PLAYER1 PLAYS THE CARD #2 (4C)
        WINNER? PLAYER0 (TRUMP,VS ANOTHER SUIT THEN TRUMP WINS)
        PLAYER 0 TAKES (2G) FROM THE DECK AND ...
        PLAYER 1 TAKES (JS) FROM THE DECK AND ...
        HERE IS THE LAST CARD WHICH IS THE TRUMP AND GOES TO THE SECOND PLAYER
        PLAYER0 PLAYS THE CARD #2 (2G)
        PLAYER1 PLAYS THE CARD #2 (JS)
        WINNER? PLAYER1 (TRUMP,VS ANOTHER SUIT THEN TRUMP WINS)
        PLAYER1 PLAYS THE CARD #1 (2S)
        PLAYER0 PLAYS THE CARD #0 (KS)
        WINNER? PLAYER0 (TRUMP,VS TRUMP THE HIGHER VALUE WINS)
        PLAYER0 PLAYS THE CARD #0 (3S)
        PLAYER1 PLAYS THE CARD #0 (HC)
        WINNER? PLAYER0 (TRUMP,VS ANOTHER SUIT THEN TRUMP WINS)		HERE THE FINAL WINNER ALSO SHOULD BE UPDATED
        PLAYER0 WON THE GAME WITH THE OVERALL SCORE 66*/
        inputConf = "1S2GJS.HS.KS3S.HC2S4C.JC4GKG5G1G5SJB3G4S6BHG5B7C7BHB7S2B1S4B6C.5C1C2C3C7G1BJG6G3B6SKCKB";
        inputMoves = "2221000";
        outputConf = "WINNER066";
        assertEquals(outputConf, MoveTest.moveTest(inputConf, inputMoves));
    }
}