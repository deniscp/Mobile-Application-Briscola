package it.polimi.group06.domain;

import java.util.Iterator;

import it.polimi.group06.domain.mtest.Parser;

import it.polimi.group06.domain.Card;

/** @author denis on 29/10/17.
 * Class containing all the element needed to play a briscola game,
 * and wrapper methods abstracting the usage of the object it instantiates.
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

        /* Distribute cards to players
         */
        int i,j;
        for(i=0;i<3;i++)
            for(j=0;j<players.length;j++)
                players[j].takeCardInHand(deck.takeCard());

        /* Set a table with the 7th card as briscola and the remaining deck
         */
        this.table = new Table(deck.takeCard(), deck);

        /*
         * Set the briscola suit for easier further accesses
         */
        this.briscola = this.table.getTrump().getSuit();

        /* first of 20 rounds in a 2-player briscola game
         */
        this.round = 1;

        /* Human player always starts first
         */
        this.startingPlayer = 0;

        /* At the beginning of the game the current player is the starting player
         */
        this.currentPlayer=this.startingPlayer;

    }

    public Game(String conf) {
        this.players = new Player[2];
        this.players[0] = new Human(0, "Group06");
        this.players[1] = new Robot(1, "Robot00");
        Parser parser = new Parser(conf);
        DeckOfCards deck = new DeckOfCards();
        Card newCard;

        newCard= new Card(1,Suit.Batons);

        newCard.toString();


        /* Creating the deck
         */
        for (Card card : parser.deck())
            deck.addCard(card);


        /* Set hands of players
         */
        for (int i=0; i<2; i++)
            players[i].getHand().addAll( parser.hands()[i]);

        /* Set pile of players
         */
        for (int i=0; i<2; i++)
            players[i].getPlayerPile().addAll(parser.piles()[i]);


        /* Set a table with the briscola and the deck
         */
        this.table = new Table(parser.briscola(), deck);

        /* Set the played card on surface
         */
        this.table.getPlayedCards().addAll(parser.surface());


        /*
         * Set the briscola suit for easier further accesses
         */
        this.briscola = parser.trumpSuit();


        /* 20 rounds in a 2-player briscola game
         */
        this.round = parser.round();

        /* The player who started the turn
         */
        this.startingPlayer = parser.startingPlayer();

        /* At the beginning of the game the current player is the starting player
         */
        this.currentPlayer=parser.currentPlayer();
    }

    public int getStartingPlayer() {
        return startingPlayer;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public Table getTable() {
        return table;
    }

    public int getRound() {
        return round;
    }

    public Player[] getPlayers() {
        return this.players;
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
            for (Card card : this.players[i].getHand())
                conf.append(card.toString());
            conf.append(".");
        }

        for (Card card : this.players[0].getPlayerPile())
            conf.append(card.toString());

        conf.append(".");

        for (Card card : this.players[1].getPlayerPile())
            conf.append(card.toString());

        return conf.toString();
    }

    /** Checks if there are still rounds to play
     *
     * @return True if there is no round left to be played
     *          False if the game is not over
     */
    public boolean gameIsOver(){
        if (this.round == 21 )
            return true;
        else
            return false;
    }

    /** Checks if there are not cards yet to be played in the current round
     *
     * @return True if all players have already played their card
     *          False otherwise
     */
    public boolean roundIsOver(){
        if (this.table.getPlayedCards().size() == 2 )
            return true;
        else
            return false;
    }

    /** Plays a card of a player
     * @param playerNum the id of player that plays the card
     * @param cardPos the position of the card in the hand of the player
     */

    public void playerPlaysCard(int playerNum, int cardPos){
        this.table.placeCard( this.players[playerNum].throwCard(cardPos) );
        this.currentPlayer = ( this.getCurrentPlayer() +1 ) %2;
    }

    /** Initializes a new round by defining the current round winner,
     *  assigning the played cards to the winning player,
     *  distributing new cards if still any,
     *  advancing the round number,
     *  and updating the starting and the current player
     */
    public void newRound(){
        int winningPlayer;  //the winner of the current round

        winningPlayer=Rules.roundWinner(this.table.getPlayedCards(), this.briscola, this.startingPlayer);

        this.players[winningPlayer].getPlayerPile().addAll(this.table.collectPlayedCard());

        if(round <= 17) {
            players[winningPlayer].takeCardInHand(this.table.getDeck().takeCard());

            if (round == 17)
                players[(winningPlayer + 1) % 2].takeCardInHand(this.table.takeTrump());
            else
                players[(winningPlayer + 1) % 2].takeCardInHand(this.table.getDeck().takeCard());
        }

        this.round++;
        this.startingPlayer = this.currentPlayer = winningPlayer;
    }

    public int returnWinner() {

        for (int i = 0; i < 2; i++)
            getPlayers()[i].setPlayerPoints( Rules.computePoints(this.players[i].getPlayerPile() ) );

        if (getPlayers()[0].getPlayerPoints() > getPlayers()[1].getPlayerPoints())
            return 0;
        else if (getPlayers()[1].getPlayerPoints() > getPlayers()[0].getPlayerPoints())
            return 1;
        else
            return -1;
    }

    public static void main(String[] argv){
        Game game = new Game();
        System.out.println(game.toConfiguration());

        Game game0 = new Game(new Game().toConfiguration());
        System.out.println(game0.toConfiguration());

        System.out.println();


        Game game1 = new Game("0B5S4G6S2C5GKB7B6CHCHB1GKC5C4B1BHG7C6BJS6G7G4C3C7SJBHS2S3S4S1S2G3BJG5B.KS.JCKG2B.1C3G..");
        System.out.println(game1.toConfiguration());

        Game game2 = new Game("1B5GKB7B6CHCHB1GKC5C4B1BHG7C6BJS6G7G4C3C7SJBHS2S3S4S1S2G3BJG5B.KG.4G6S.KS5S2C.3G2B.JC1C");
        System.out.println(game2.toConfiguration());


//        game.players[0].takeCardInHand(game.table.getDeck().takeCard());
//        System.out.println(game.toConfiguration());
//        game.table.placeCard(game.players[0].throwCard(1));
//        System.out.println(game.toConfiguration());

    }
}