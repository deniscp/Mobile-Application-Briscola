package it.polimi.group06briscola.model;

import java.lang.reflect.GenericArrayType;
import java.util.Iterator;

import it.polimi.group06briscola.model.test.Parser;

/**
 * Created by denis on 29/10/17.
 */

public class Game {
    private Player[] players;
    private int round;
    private int startingPlayer;
    private int currentPlayer;
    private Table table;
    private Suit briscola;

    public Game(){

        this.players = new Player[2];
        this.players[0] = new Human(0,"Group06");
        this.players[1] = new  Robot(1,"Robot00");
        DeckOfCards deck = new DeckOfCards(false);

        /** Distribute cards to players
         */
        int i,j;
        for(i=0;i<3;i++)
            for(j=0;j<players.length;j++)
                players[j].takeCardInHand(deck.takeCard());

        /** Set a table with the 7th card as briscola and the remaining deck
         */
        this.table = new Table(deck.takeCard(), deck);

        /**
         * Set the briscola suit for easier further accesses
         */
        this.briscola = this.table.getTrump().getSuit();

        /** first of 20 rounds in a 2-player briscola game
         */
        this.round = 1;

        /** Human player always starts first
         */
        this.startingPlayer = 0;

        /** At the beginning of the game the current player is the starting player
         */
        this.currentPlayer=this.startingPlayer;

    }

    public Game(String conf) {
        this.players = new Player[2];
        this.players[0] = new Human(0, "Group06");
        this.players[1] = new Robot(1, "Robot00");
        Parser parser = new Parser(conf);
        DeckOfCards deck = new DeckOfCards();

        /* Creating the deck
         */
        for (Iterator<Card> card = parser.deck().iterator(); card.hasNext(); )
            deck.addCard(card.next());


        /* Set hands of players
         */
        for (int i=0; i<2; i++)
            for (Iterator<Card> cardIterator = parser.hands()[i].iterator(); cardIterator.hasNext(); )
                players[i].takeCardInHand(cardIterator.next());

        /* Set pile of players
         */
        for (int i=0; i<2; i++)
            for (Iterator<Card> cardIterator = parser.piles()[i].iterator(); cardIterator.hasNext(); )
                players[i].getPlayerPile().add(cardIterator.next());


        /** Set a table with the briscola and the deck
         */
        this.table = new Table(parser.briscola(), deck);

        /* Set the played card on surface
         */
        for (Iterator<Card> cardIterator = parser.surface().iterator(); cardIterator.hasNext(); )
            this.table.setPlayedCard(cardIterator.next());


        /**
         * Set the briscola suit for easier further accesses
         */
        this.briscola = parser.briscola().getSuit();


        /** 20 rounds in a 2-player briscola game
         */
        this.round = parser.round();

        /** The player who started the turn
         */
        this.startingPlayer = parser.startingPlayer();

        /** At the beginning of the game the current player is the starting player
         */
        this.currentPlayer=parser.currentPlayer();
    }

    /**
     * Computes the String representing the actual configuration of the game
     * @return String for the game configuration
     */
    public String toConfiguration(){
        StringBuilder conf = new StringBuilder();

        conf.append(currentPlayer).append(this.briscola);
        conf.append(this.table);

        for(int i=0; i<2 ; i++) {
            for (Iterator<Card> c = this.players[i].getHand().iterator(); c.hasNext(); )
                conf.append(c.next().toString());
            conf.append(".");
        }

        for(Iterator<Card> c = this.players[0].getPlayerPile().iterator(); c.hasNext();)
            conf.append(c.next().toString());

        conf.append(".");

        for(Iterator<Card> c = this.players[1].getPlayerPile().iterator(); c.hasNext();)
            conf.append(c.next().toString());

        return conf.toString();
    }

    public static void main(String[] argv){
        Game game = new Game();
        System.out.println(game.toConfiguration());

        Game game0 = new Game(new Game().toConfiguration());
        System.out.println(game0.toConfiguration());

        Game game1 = new Game("0B5S4G6S2C5GKB7B6CHCHB1GKC5C4B1BHG7C6BJS6G7G4C3C7SJBHS2S3S4S1S2G3BJG5B.KS.JCKG2B.1C3G..");
        System.out.println(game1.toConfiguration());

        Game game2 = new Game("1B5GKB7B6CHCHB1GKC5C4B1BHG7C6BJS6G7G4C3C7SJBHS2S3S4S1S2G3BJG5B.KG.4G6S.KS5S2C.3G2B.JC1C");
        System.out.println(game2.toConfiguration());

//        game.players[0].takeCardInHand(game.table.getDeck().takeCard());
//        System.out.println(game.toConfiguration());
//        game.table.setPlayedCard(game.players[0].playCard(1));
//        System.out.println(game.toConfiguration());

    }
}