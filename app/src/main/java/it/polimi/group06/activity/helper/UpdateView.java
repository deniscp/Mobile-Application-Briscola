package it.polimi.group06.activity.helper;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import it.polimi.group06.R;
import it.polimi.group06.activity.GameActivity;
import it.polimi.group06.domain.Card;

import static it.polimi.group06.activity.helper.Constants.TAG;
import static it.polimi.group06.domain.Constants.FIRSTPLAYER;
import static it.polimi.group06.domain.Constants.SECONDPLAYER;

/**
 * Created by denis on 31/12/17.
 */

public class UpdateView implements Runnable{
    final private GameActivity cAct;

    public UpdateView(GameActivity currentActivity){
        cAct = currentActivity;
    }
    /**
     * Updates setting the remaining cards in the deck,
     * setting the remaining cards in the hands of the players,
     * removing cards from table after round is over...
     */
    public void run() {
        ArrayList<Card> humanHand = cAct.game.getPlayers()[FIRSTPLAYER].getHand();
        ArrayList<Card> robotHand = cAct.game.getPlayers()[SECONDPLAYER].getHand();
        int i;
        ImageView humanCards[] = cAct.humanHand;
        ImageView robotCards[] = {cAct.robotcard1,cAct.robotcard2,cAct.robotcard3};
        final ImageView shuttleView = cAct.findViewById(R.id.shuttle);
        final ImageView shuttleRobotView = cAct.findViewById(R.id.shuttle_robot);


        int playedSize = cAct.game.getTable().getPlayedCards().size();

        //Clear the table if a new round has just started
        //Maybe launch an animation that moves cards toward the winning player
        if(playedSize == 0 || playedSize ==1 && cAct.game.getStartingPlayer() == SECONDPLAYER) {

            shuttleRobotView.setImageResource(0);
            shuttleView.setImageResource(0);
        }

        // Adjust Human missing cards
        for(i=0; i < humanHand.size() ; i++ ) {
            humanCards[i].setImageResource(cAct.getCardDrawable(humanHand.get(i)));
            humanCards[i].setVisibility(View.VISIBLE);
            Log.d(TAG, "Adjusted "+robotHand.size()+" cards of human in hand");
        }
        for( ; i < 3 ; i++)
            humanCards[i].setVisibility(View.INVISIBLE);

        // Adjust Robot missing cards
        int nRobotCards = robotHand.size();

        // the robot has already played and decreased a card;
        if(cAct.game.getCurrentPlayerPosition() == FIRSTPLAYER) // if it is the human turn;
            if(cAct.game.getRound()<= 18)
                nRobotCards=3;
            else if (cAct.game.getRound()== 19)
                nRobotCards=2;
            else if (cAct.game.getRound()== 20)
                nRobotCards=1;

        for(i=0; i < nRobotCards ; i++ ){
            Log.d(TAG, "Adjusted the "+i+"th cards of robot in hand");
            robotCards[i].setVisibility(View.VISIBLE);
        }
        for( ; i < 3 ; i++)
            robotCards[i].setVisibility(View.INVISIBLE);

        //Set the number of remaining cards in the deck
        cAct.remaining.setText(String.valueOf(cAct.game.remainingCards()));

        //Set the points of both players
        cAct.humanPoints.setText(String.valueOf(cAct.game.getPlayers()[FIRSTPLAYER].getPlayerPoints()));
        cAct.robotPoints.setText(String.valueOf(cAct.game.getPlayers()[SECONDPLAYER].getPlayerPoints()));
        Log.d(TAG,"points updated");


        // make the deck not visible and fade briscola
        if(cAct.game.getRound() == 18) //Starting from the last but three round
        {
            cAct.remaining.setVisibility(View.INVISIBLE);
            cAct.pile.setVisibility(View.INVISIBLE);
            //fade the briscola
            cAct.briscola_image.startAnimation(cAct.briscolaTaken);
        }

        cAct.humanCardsClickable(true);
    }
}
