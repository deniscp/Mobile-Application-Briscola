package it.polimi.group06briscola.Model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by denis on 28/10/17.
 */

/* Static methods implementing the rules of the game Briscola
 */
public class Rules {


    /* Returns the position (in case of just two players, 0 or 1) of the winner of the current turn
     * given the played cards at the end of a turn and given the current briscola
     */
    static int returnTurnWinner (Card[] playedCard, Card trump){

        if(playedCard[0]==null || playedCard[1]==null){
            throw new IllegalArgumentException("Not all players have played their card yet");
        }

        if(playedCard[0].getSuite() == playedCard[1].getSuite())
            if( playedCard[0].getRank() < playedCard[1].getRank() )
                return 0;
            else
                return 1;

        else
            if(playedCard[0].getSuite().equals(trump.getSuite()))
                return 0;
        else
            if(playedCard[1].getSuite().equals(trump.getSuite()))
                return 1;
        else
            return 0;
    }

    /* Ordered Italian Deck of Cards to be used as a String */
    static final String[] sortedDeck={"1B", "2B", "3B", "4B", "5B", "6B", "7B", "JB", "HB", "KB", "1S", "2S", "3S", "4S", "5S", "6S", "7S", "JS", "HS", "KS", "1C", "2C", "3C", "4C", "5C", "6C", "7C", "JC", "HC", "KC", "1G", "2G", "3G", "4G", "5G", "6G", "7G", "JG", "HG", "KG"};


    /* Compute the score of a give pile or deck in the form of an ArrayList<Card>
     */
    static int computeScore(ArrayList<Card> playerPile) {
        int score = 0;

        Iterator<Card> pileIterator = playerPile.iterator();

        while (pileIterator.hasNext()) {
            score += pileIterator.next().getPoint();
        }

        return score;
    }

}
