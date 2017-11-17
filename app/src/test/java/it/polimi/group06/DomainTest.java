package it.polimi.group06;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import it.polimi.group06.domain.Card;
import it.polimi.group06.domain.DeckOfCards;
import it.polimi.group06.domain.Suit;
import it.polimi.group06.domain.Rules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by Timo Zandonella on 17.11.2017.
 */

public class DomainTest {

    //Test the creation of an unsorted deck of cards and the toString() method
    @Test
    public void DeckOfCardsTest() throws Exception{
        String sorteddeck = "1B2B3B4B5B6B7BJBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKS";
        assertEquals(sorteddeck, new DeckOfCards(false).toString());
    }

    //Test the creation of a card, the point and rank methods
    @Test
    public void CardTest() throws Exception{
        Card threeofbatons = new Card(3, Suit.Batons);
        String threeofbatonsString = "3B";
        assertEquals(threeofbatonsString, new Card(3, Suit.Batons).toString());
        assertEquals(threeofbatons.getPoint(),10);
        assertEquals(threeofbatons.getRank(),2);
    }

    //Test the rule class: computation of points of a pile of cards
    @Test
    public void RulesTest() throws Exception{
        Rules rules = new Rules();

        //Computation of Points:
        ArrayList<Card> testdeck = new ArrayList<Card>();
        testdeck.add(0, new Card(5,Suit.Cups)); // 5 -> 0
        testdeck.add(1, new Card(1, Suit.Batons)); //Ace -> 11
        testdeck.add(2, new Card(9, Suit.Golds)); // Horse -> 3
        assertEquals(rules.computePoints(testdeck), 14);
        //Add a card, the pile isn't worth 14 points anymore:
        testdeck.add(3, new Card(9, Suit.Swords));
        assertFalse(rules.computePoints(testdeck) == 14);

        //Computation of who won a round:
        ArrayList<Card> rounddeck = new ArrayList<Card>();
        rounddeck.add(0, new Card(1,Suit.Cups));
        rounddeck.add(1, new Card(3, Suit.Cups));
        assertEquals(rules.roundWinner(rounddeck, Suit.Batons, 0), 0);

        rounddeck.remove(0); rounddeck.remove(0);
        rounddeck.add(0, new Card(6, Suit.Cups));
        rounddeck.add(1, new Card(5,Suit.Batons));
        assertEquals(rules.roundWinner(rounddeck, Suit.Batons, 0), 1);

        rounddeck.remove(0);
        try {
            rules.roundWinner(rounddeck, Suit.Batons, 1);
            Assert.fail();
        }catch(Exception e){
            String expected = "Not all players have played their card yet";
            Assert.assertEquals(expected, e.getMessage());
        }

    }

}
