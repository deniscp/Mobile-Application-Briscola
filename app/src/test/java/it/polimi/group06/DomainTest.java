package it.polimi.group06;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import it.polimi.group06.domain.Card;
import it.polimi.group06.domain.Deck;
import it.polimi.group06.domain.Game;
import it.polimi.group06.domain.Human;
import it.polimi.group06.domain.Player;
import it.polimi.group06.domain.Suit;
import it.polimi.group06.domain.Rules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * This class tests all the classes in the domain of the application.
 * No tests for the classes AI, Human, Robot, Suit are implemented, because there is nothing to test (yet).
 * No tests for the classes Player and Table, because the current tests already cover the testable methods in these classes.
 * @author Timo Zandonella
 */
public class DomainTest {

    /**
     * Test of the player constructor in Player class
     * @throws Exception
     */
    @Test
    public void playerTest() throws Exception{
        Player player = new Human(0 ,"Group06");
        assertEquals("Group06",player.getUsername());
        assertEquals(0,player.getId());
    }

    @Test
    public void customDeckTest() throws Exception{
        Deck deck = new Deck(false);
        String sorteddeck = "1B2B3B4B5B6B7BJBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKS";
        assertEquals(sorteddeck, deck.toString());

        deck.replaceDeck(new ArrayList<Card>());
        assertEquals("", deck.toString());
        assertEquals(0,deck.remaining());

        deck.addCard(new Card(2,Suit.Batons)); //only card in the deck
        deck.pushCard(new Card(1,Suit.Batons));//added on top of the pile
        deck.addCard(new Card(3,Suit.Batons)); //added on the bottom of the pile

        assertEquals("1B2B3B", deck.toString()); //the expected order in which the cards are placed

        assertEquals(new Card(1,Suit.Batons), deck.drawCard()); //first card drawn from the top
        assertEquals(new Card(2,Suit.Batons), deck.drawCard()); //second card drawn from the top
        assertEquals(new Card(3,Suit.Batons), deck.drawCard()); //third card drawn from the top

        assertEquals("", deck.toString()); //Empty deck again
        assertEquals(0,deck.remaining()); //Empty deck again
    }


    @Test
    public void deckLength(){
        Deck deck = new Deck(true);
        int i=0;

        while (deck.remaining()>0){
            deck.drawCard();
            i++;
        }
        assertEquals(40,i);
    }

    /**
     Test of the custom toString() method in the DeckOfCards class
     @throws Exception
     */
    @Test
    public void DeckOfCardsTest() throws Exception{
        String sorteddeck = "1B2B3B4B5B6B7BJBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKS";
        assertEquals(sorteddeck, new Deck(false).toString());
    }

    /**
     * Test of the Card class and a few methods in it
     * @throws Exception
     */
    @Test
    public void CardTest() throws Exception{
        Card threeofbatons = new Card(3, Suit.Batons);
        String threeofbatonsString = "3B";
        //Test of the custom toString() method
        assertEquals(threeofbatonsString, new Card(3, Suit.Batons).toString());
        //Test that the points get assigned correctly
        assertEquals(threeofbatons.getPoint(),10);
        //Test that the rank gets assigned correctly
        assertEquals(threeofbatons.getRank(),2);
    }


    //Test the rule class: computation of points of a pile of cards
    @Test
    public void card() throws Exception {
        String threeofbatons = "3B";
    }

    /**
     * Test of the rules class for a test pile of a player
     * @throws Exception
     */
    @Test
    public void RulesTest() throws Exception{
        Rules rules = new Rules();

        //1. Test of the computation of points of the testpile
        //a) Preparation (add 3 cards to a testpile):
        ArrayList<Card> testpile = new ArrayList<Card>();
        testpile.add(0, new Card(5,Suit.Cups)); // 5 -> 0
        testpile.add(1, new Card(6,Suit.Swords)); // 6 -> 0
        testpile.add(2, new Card(1, Suit.Batons)); //Ace -> 11
        testpile.add(3, new Card(9, Suit.Golds)); // Horse -> 3

        //b) Test it for the pile
        assertEquals(rules.computePoints(testpile), 14);

        //c) After an addition of a card the pile isn't worth 14 points anymore:
        testpile.add(3, new Card(9, Suit.Swords));
        assertFalse(rules.computePoints(testpile) == 14);

        //2. Computation of who won a round:
        ArrayList<Card> cardsontable = new ArrayList<Card>();
        //a) Ace of Cups against 3 of Cups
        cardsontable.add(0, new Card(1,Suit.Cups));
        cardsontable.add(1, new Card(3, Suit.Cups));
        assertEquals(rules.roundWinner(cardsontable, Suit.Batons, 0), 0);
        cardsontable.remove(0);
        cardsontable.remove(0);
        //b) 6 of Cups against 5 of Batons (Suit): Result is that Suit wins
        cardsontable.add(0, new Card(6, Suit.Cups));
        cardsontable.add(1, new Card(5,Suit.Batons));
        assertEquals(rules.roundWinner(cardsontable, Suit.Batons, 0), 1);

        //c) If there is only one card in the to be compared array list, than the class puts out an error:
        cardsontable.remove(0);
        try {
            rules.roundWinner(cardsontable, Suit.Batons, 1);
            Assert.fail();
        }catch(Exception e){
            String expected = "Not all players have played their card yet";
            Assert.assertEquals(expected, e.getMessage());
        }
    }

    /**
     * Test the Game class, which is the heart of our domain
     * @throws Exception
     */
    @Test
    public void GameTest() throws Exception{
        Game game = new Game();
        //0. Because the in the Game class the deck is shuffled automatically in the constructor, we have to do some preparation for testing:
        //a) Set the Trump to the trump of every sorted deck
        game.getTable().setTrump(new Card(7,Suit.Batons));
        //b) We create a sorted deck...
        Deck deck = new Deck(false);
        //... and than remove the first 7 cards of it, because the first 6 cards have already been dealt to the two players in the Game constructor. Then ...
        for(int i=0;i<7;i++){
            deck.drawCard();
        }
        //.. we overwrite the dealt deck with our shortened sorted deck.
        game.getTable().setDeck(deck);
        //c) Now we also set the suit in the Game class.
        game.setBriscolaSuit(Suit.Batons);
        //d) We also create the standard two hands of two players, that would get dealt with a sorted deck. Then...
        ArrayList<Card> player0 = new ArrayList<Card>();
        ArrayList<Card> player1 = new ArrayList<Card>();
        player0.add(new Card(1, Suit.Batons)); player0.add(new Card(3, Suit.Batons)); player0.add(new Card(5, Suit.Batons));
        player1.add(new Card(2, Suit.Batons)); player1.add(new Card(4, Suit.Batons)); player1.add(new Card(6, Suit.Batons));
        //... we replace the Hands of the players with these artificial hands.
        game.getPlayers()[0].replaceHand(player0);
        game.getPlayers()[1].replaceHand(player1);
        //e) The we customly play two cards
        game.playerPlaysCard(0,0);
        game.playerPlaysCard(1,0);
        //1. Test of the assertion if the game is over:
        assertEquals(game.gameIsOver(),false);
        //2. Test if the round is over -> Result: Round is over, because two players have played a card
        assertEquals(game.roundIsOver(), true);
        game.newRound();
        //3. Test if the comparison between the two cards works correctly
        assertEquals(game.returnWinner(), 0);
        //4. Test the toConfiguration method:
        assertEquals(game.toConfiguration(),"0BKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKS7B..3B5BJB.4B6BHB.1B2B.");
    }
}
