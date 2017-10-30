package it.polimi.group06briscola.model;

/**
 * Created by denis on 29/10/17.
 */

public class Game {
    Player[] players;
    int round;
    int startingPlayer;
    Table table;

    Game(){
        //players = {new Human(), new  Robot()};
        DeckOfCards deck = new DeckOfCards(false, "");

        /** Distribute cards to players
         */
        int i,j;
        for(i=0;i<3;i++)
            for(j=0;j<players.length;j++)
                players[j].hand[i]= deck.takeLastCard();

        Card briscola = deck.takeLastCard();

        this.table = new Table(briscola, deck);

    }
}
