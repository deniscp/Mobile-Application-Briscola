package it.polimi.group06.domain;

import static it.polimi.group06.domain.Constants.DRAW;
import static it.polimi.group06.domain.Constants.FIRSTPLAYER;
import static it.polimi.group06.domain.Constants.NUMBEROFPLAYERS;
import static it.polimi.group06.domain.Constants.SECONDPLAYER;

/**
 * Class containing all the element needed to play a briscola game,
 * and wrapper methods abstracting the usage of the object it instantiates.
 *
 * @author Denis
 */
public class Game {
    private Player[] players;
    private int round;
    private int startingPlayer;
    private int currentPlayer;
    private Table table;
    private Suit briscola;

    /**
     * Creates a new game with two players.
     * The first player is a human, the second a robot, the human player starts the first round.
     * The deck is not shuffled.
     */
    public Game() {
        this.players = new Player[NUMBEROFPLAYERS];
        this.players[FIRSTPLAYER] = new Human(FIRSTPLAYER, "Group06");
        this.players[SECONDPLAYER] = new Robot(SECONDPLAYER, "Robot00");
        Deck deck = new Deck(true);

        /* Distribute cards to players
         */
        int i, j;
        for (i = 0; i < 3; i++)
            for (j = 0; j < players.length; j++)
                players[j].takeCardInHand(deck.drawCard());

        /* Set a table with the 7th card as briscola and the remaining deck
         */
        this.table = new Table(deck.drawCard(), deck);

        /*
         * Set the briscola suit for easier further accesses
         */
        this.briscola = this.table.getBriscola().getSuit();

        /* first of 20 rounds in a 2-player briscola game
         */
        this.round = 1;

        /* Human player always starts first
         */
        this.startingPlayer = FIRSTPLAYER;

        /* At the beginning of the game the current player is the starting player
         */
        this.currentPlayer = this.startingPlayer;
    }

    /**
     * Static method that creates a Game object using the standard constructor of Game class
     * and selectively replacing fields according to the passed string configuration as parsed by Parser
     *
     * @param conf the passed configuration file
     * @return the Game initialized according to String conf parameter
     * @see Parser
     */
    public static Game initializeFromConf(String conf) {
        Game gameFromConf = new Game();
        Parser parser = new Parser(conf);


        /* Replace hands of players
         */
        for (int i = 0; i < NUMBEROFPLAYERS; i++)
            gameFromConf.getPlayers()[i].replaceHand(parser.hands()[i]);

        /* Replace pile of players
         */
        for (int i = 0; i < NUMBEROFPLAYERS; i++)
            gameFromConf.getPlayers()[i].replacePlayerPile(parser.piles()[i]);


        /* Set the table replacing the briscola and the deck
         */
        gameFromConf.getTable().setBriscola(parser.briscola());
        gameFromConf.getTable().getDeck().replaceDeck(parser.deck());

        /* Set the played card on surface
         */
        gameFromConf.getTable().replacePlayedCards(parser.surface());


        /*
         * Set the briscola suit for easier future accesses
         */
        gameFromConf.setBriscolaSuit(parser.briscolaSuit());


        /* 20 rounds in a 2-player briscola game
         */
        gameFromConf.setRound(parser.round());

        /* The player who started the round
         */
        gameFromConf.setStartingPlayer(parser.startingPlayer());

        /* At the beginning of the game the current player is the starting player
         */
        gameFromConf.setCurrentPlayer(parser.currentPlayer());


        return gameFromConf;
    }

    public int getStartingPlayer() {
        return startingPlayer;
    }

    public int getCurrentPlayerPosition() {
        return currentPlayer;
    }

    public Player getCurrentPlayer()
    {
        return this.getPlayers()[this.getCurrentPlayerPosition()];
    }

    /**
     * Returns how many cards can still be picked by players.
     * @return The number of cards left in the deck plus one if the briscola is still on the table
     */
    public int remainingCards(){
        int briscola = 0;
        if(this.getTable().getBriscola()!= null)
            briscola = 1;
        return this.getTable().getDeck().remaining() + briscola;
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

    public void setBriscolaSuit(Suit briscola) {
        this.briscola = briscola;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void setStartingPlayer(int startingPlayer) {
        this.startingPlayer = startingPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Computes the String representing the actual configuration of the game
     *
     * @return String for the game configuration
     */
    public String toConfiguration() {
        StringBuilder conf = new StringBuilder();

        conf.append(currentPlayer).append(this.briscola);
        conf.append(this.table);

        for (int i = 0; i < NUMBEROFPLAYERS; i++) {
            for (Card card : this.players[i].getHand())
                conf.append(card.toString());
            conf.append(".");
        }

        for (Card card : this.players[FIRSTPLAYER].getPlayerPile())
            conf.append(card.toString());

        conf.append(".");

        for (Card card : this.players[SECONDPLAYER].getPlayerPile())
            conf.append(card.toString());

        return conf.toString();
    }

    /**
     * Checks if there are still rounds to play, i.e. the game is not finished
     *
     * @return True if there is no round left to be played,
     *         False if the game is not over
     */
    public boolean gameIsOver() {
        if (this.round == 21)
            return true;
        else
            return false;
    }

    /**
     * Checks if there are not cards yet to be played in the current round
     *
     * @return True if all players have already played their card,
     *         False otherwise
     */
    public boolean roundIsOver() {
        if (this.table.getPlayedCards().size() == NUMBEROFPLAYERS)
            return true;
        else
            return false;
    }

    /**
     * Plays a card of a player
     *
     * @param playerNum the id of player that plays the card
     * @param cardPos   the position of the card in the hand of the player
     */
    public void playerPlaysCard(int playerNum, int cardPos) {
        this.table.placeCard(this.players[playerNum].throwCard(cardPos));
        this.currentPlayer = (this.getCurrentPlayerPosition() + 1) % NUMBEROFPLAYERS;
    }

    /**
     * Selects, knowing who is the current player,
     * which is the current choice, if the card selected by the Human
     * or the one computed by the Robot's strategy.
     * It can be used with the playerPlaysCard method.
     *
     * @param humanChoice the card eventually selected by the Human
     * @return the position of the appropriate selected card
     */
    public int getCurrentChoice(int humanChoice){
        Player currentPlayer = this.getPlayers()[this.getCurrentPlayerPosition()];
        int choice = 0;

        if(currentPlayer instanceof Human)
            choice = humanChoice;
        if(currentPlayer instanceof Robot)
            choice = ((Robot) currentPlayer).strategy.cardPositionInHand();

        return choice;
    }

    /**
     * Initializes a new round by defining the current round winner,
     * assigning the played cards to the winning player,
     * distributing new cards if still any,
     * advancing the round number,
     * and updating the starting and the current player
     */
    public void newRound() {
        int winningPlayer;  //the winner of the current round

        winningPlayer = Rules.roundWinner(this.table.getPlayedCards(), this.briscola, this.startingPlayer);

        // The round winner collects the cards
        this.players[winningPlayer].getPlayerPile().addAll(this.table.collectPlayedCard());
        // The round winner updates his points
        this.players[winningPlayer].setPlayerPoints(Rules.computePoints(this.getPlayers()[winningPlayer].getPlayerPile()));


        if (round <= 17) {
            players[winningPlayer].takeCardInHand(this.table.getDeck().drawCard());

            if (round == 17)
                players[(winningPlayer + 1) % NUMBEROFPLAYERS].takeCardInHand(this.table.takeBriscola());
            else
                players[(winningPlayer + 1) % NUMBEROFPLAYERS].takeCardInHand(this.table.getDeck().drawCard());
        }

        this.round++;
        this.startingPlayer = this.currentPlayer = winningPlayer;
    }

    /**
     * Returns the winner at the end of a game, it should be called when the game is over.
     * @return The id of the winning player.
     */
    public int returnWinner() {

        /* Set the point of each player
         */
        for (int i = 0; i < NUMBEROFPLAYERS; i++)
            getPlayers()[i].setPlayerPoints(Rules.computePoints(this.players[i].getPlayerPile()));

        /* Check which player scored more points and return it.
         */
        if (getPlayers()[FIRSTPLAYER].getPlayerPoints() > getPlayers()[SECONDPLAYER].getPlayerPoints())
            return FIRSTPLAYER;
        else if (getPlayers()[SECONDPLAYER].getPlayerPoints() > getPlayers()[FIRSTPLAYER].getPlayerPoints())
            return SECONDPLAYER;
        else
            return DRAW;
    }
}