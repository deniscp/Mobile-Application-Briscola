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
import it.polimi.group06.domain.Table;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by Timo Zandonella on 17.11.2017.
 */

public class DomainTest {


    /**
     * Test of the player constructor in Player class
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

    //Test the creation of an unsorted deck of cards and the toString() method
    @Test
    public void deckOfCardsTest() throws Exception{
        String sorteddeck = "1B2B3B4B5B6B7BJBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKS";
        assertEquals(sorteddeck, new Deck(false).toString());
    }

    //Test the creation of a card, the point and rank methods
    @Test
    public void cardTest() throws Exception{
        Card threeofbatons = new Card(3, Suit.Batons);
        String threeofbatonsString = "3B";
        assertEquals(threeofbatonsString, new Card(3, Suit.Batons).toString());
        assertEquals(threeofbatons.getPoint(),10);
        assertEquals(threeofbatons.getRank(),2);
    }

    //Test the rule class: computation of points of a pile of cards
    @Test
    public void card() throws Exception {
        String threeofbatons = "3B";
    }

    public void rulesTest() throws Exception{
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
        ArrayList<Card> cardsontable = new ArrayList<Card>();
        cardsontable.add(0, new Card(1,Suit.Cups));
        cardsontable.add(1, new Card(3, Suit.Cups));
        assertEquals(rules.roundWinner(cardsontable, Suit.Batons, 0), 0);

        cardsontable.remove(0); cardsontable.remove(0);
        cardsontable.add(0, new Card(6, Suit.Cups));
        cardsontable.add(1, new Card(5,Suit.Batons));
        assertEquals(rules.roundWinner(cardsontable, Suit.Batons, 0), 1);

        //If there is only one card in the to be compared array list, than the class puts out an error:
        cardsontable.remove(0);
        try {
            rules.roundWinner(cardsontable, Suit.Batons, 1);
            Assert.fail();
        }catch(Exception e){
            String expected = "Not all players have played their card yet";
            Assert.assertEquals(expected, e.getMessage());
        }
    }

    //Test the Game class, to see if all the game functionality works correctly
    @Test
    public void gameTest(){
        Game game = new Game();
        game.getTable().setTrump(new Card(7,Suit.Batons));
        //For later stages we set the deck to a sorted deck...
        Deck deck = new Deck(false);
        //... and remove the first 7 cards of it, because they have already been dealt in the Game constructor
        for(int i=0;i<7;i++){
            deck.drawCard();
        }
        game.getTable().setDeck(deck);
        game.setBriscolaSuit(Suit.Batons);
        ArrayList<Card> player0 = new ArrayList<Card>();
        ArrayList<Card> player1 = new ArrayList<Card>();
        player0.add(new Card(1, Suit.Batons)); player0.add(new Card(3, Suit.Batons)); player0.add(new Card(5, Suit.Batons));
        player1.add(new Card(2, Suit.Batons)); player1.add(new Card(4, Suit.Batons)); player1.add(new Card(6, Suit.Batons));
        game.getPlayers()[0].replaceHand(player0);
        game.getPlayers()[1].replaceHand(player1);
        game.playerPlaysCard(0,0);
        game.playerPlaysCard(1,0);
        //Game is not over, when players each only played one card
        assertEquals(game.gameIsOver(),false);
        //Round is over, because both players each played one card
        assertEquals(game.roundIsOver(), true);
        game.newRound();
        //Player 0 has one with the Ace of Batons
        assertEquals(game.returnWinner(), 0);
        //The toConfiguration methods puts out the correct configuration
        assertEquals(game.toConfiguration(),"0BKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKS7B..3B5BJB.4B6BHB.1B2B.");
    }

    @Test
    public void tableTest(){
        Table table = new Table(new Card(3, Suit.Batons), new Deck(false));

    }
}
