package it.polimi.group06briscola.model;

import java.util.Iterator;

/**
 * Created by denis on 29/10/17.
 */

public class Game {
    Player[] players;
    int round;
    int startingPlayer;
    Table table;
    Suit briscola;

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
         * Set the suit briscola for easier further accesses
         */
        briscola = this.table.getTrump().getSuit();

        /** 20 rounds in a 2-player briscola game
         */
        this.round = 20;

        /** Human player always starts first
         */
        this.startingPlayer = 0;

    }

    public Game(String conf){
        this.players = new Player[2];
        this.players[0] = new Human(0,"Group06");
        this.players[1] = new  Robot(1,"Robot00");
        DeckOfCards deck = new DeckOfCards();

//        parseToDeck(conf.substring(2,));

        /** Distribute cards to players
         */
        int i,j;
        for(i=0;i<3;i++)
            for(j=0;j<players.length;j++)
                players[j].takeCardInHand(deck.takeCard());

        /** Set a table with the 7th card as briscola and the remaining deck
         */
        this.table = new Table(deck.takeCard(), deck);

        /** 20 rounds in a 2-player briscola game
         */
        this.round = 20;

        /** Human player always starts first
         */
        this.startingPlayer = 0;

    }

    /**
     * Computes the String representing the actual configuration of the game
     * @return String for the game configuration
     */
    public String toConfiguration(){
        StringBuilder conf = new StringBuilder();

        conf.append(startingPlayer).append(this.briscola);
        conf.append(this.table);

        for(int i=0; i<2 ; i++) {
            for (Iterator c = this.players[i].getHand().iterator(); c.hasNext(); ) {
                conf.append(c.next().toString());
            }
            conf.append(".");
        }
        for (Iterator i = this.players[0].getPlayerPile().iterator(); i.hasNext();){
            conf.append(i.next().toString());
        }
        conf.append(".");

        for (Iterator i = this.players[1].getPlayerPile().iterator(); i.hasNext();){
            conf.append(i.next().toString());
        }
        return conf.toString();
    }

    public static void main(String[] argv){
        Game game = new Game();
        System.out.println(game.toConfiguration());
//        game.players[0].takeCardInHand(game.table.getDeck().takeCard());
//        System.out.println(game.toConfiguration());
        game.table.setPlayedCard(game.players[0].playCard(1));
        System.out.println(game.toConfiguration());

    }
}