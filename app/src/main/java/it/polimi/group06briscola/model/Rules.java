package it.polimi.group06briscola.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by denis on 28/10/17.
 */

/**
 *  Static methods implementing the rules of the game Briscola
 */
public class Rules {


    /**
     *  Returns the position (in case of just two players, 0 or 1) of the winner of the current round
     * given the played cards at the end of a round, the current briscola and the starting player
     */
    static int roundWinner(ArrayList<Card> playedCard, Suit trump, int starting){

        if(playedCard.get(0)==null || playedCard.get(1)==null)
            throw new IllegalArgumentException("Not all players have played their card yet");


        if(playedCard.get(0).getSuit() == playedCard.get(1).getSuit())
            if( playedCard.get(0).getRank() < playedCard.get(1).getRank() )
                return (0 + starting) % 2;
            else
                return (1 + starting) % 2;

        else
            if(playedCard.get(0).getSuit().equals(trump))
                return (0 + starting) % 2;
        else
            if(playedCard.get(1).getSuit().equals(trump))
                return (1 + starting) % 2;
        else
            return (0 + starting) % 2;
    }

    /**
     *  Ordered Italian Deck of Cards to be used as a String
     *
     */
    static final String[] sortedDeck={"1B", "2B", "3B", "4B", "5B", "6B", "7B", "JB", "HB", "KB", "1S", "2S", "3S", "4S", "5S", "6S", "7S", "JS", "HS", "KS", "1C", "2C", "3C", "4C", "5C", "6C", "7C", "JC", "HC", "KC", "1G", "2G", "3G", "4G", "5G", "6G", "7G", "JG", "HG", "KG"};


    /**
     *  Compute the score of a give pile or deck in the form of an ArrayList<Card>
     */
    static int computeScore(ArrayList<Card> playerPile) {
        int score = 0;

        Iterator<Card> pileIterator = playerPile.iterator();

        while (pileIterator.hasNext())
            score += pileIterator.next().getPoint();

        return score;
    }
}