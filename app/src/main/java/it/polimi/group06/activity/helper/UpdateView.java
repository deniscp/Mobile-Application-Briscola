package it.polimi.group06.activity.helper;

import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import it.polimi.group06.activity.GameActivity;
import it.polimi.group06.domain.Card;
import it.polimi.group06.domain.Human;

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
     * Updates setting the remainng cards in the deck,
     * setting the remainng cards in the hands of the players,
     * removing cards from table after round is over...
     */
    public void run() {
        ArrayList<Card> humanHand = cAct.game.getPlayers()[FIRSTPLAYER].getHand();
        ArrayList<Card> robotHand = cAct.game.getPlayers()[SECONDPLAYER].getHand();
        int i;
        ImageView humanCards[] = {cAct.cardzero_image,cAct.cardone_image,cAct.cardtwo_image};
        ImageView robotCards[] = {cAct.robotcard1,cAct.robotcard2,cAct.robotcard3};

        //Clear the table if a new round has just started
        //Maybe launch an animation that moves cards toward the winning player
        if(cAct.game.getTable().getPlayedCards().size() == 0) {
            cAct.humancard.setImageResource(0);
            cAct.robotcard.setImageResource(0);
        }

        if(cAct.game.getTable().getPlayedCards().size() == 1)
            if(cAct.game.getCurrentPlayer() instanceof Human) //If Robot has played as first
                cAct.humancard.setImageResource(0);         //Remove the previous human card from the table




        // Adjust Human missing cards
        for(i=0; i < humanHand.size() ; i++ ) {
            humanCards[i].setImageResource(cAct.getCardDrawable(humanHand.get(i), cAct.getApplicationContext()));
            humanCards[i].setVisibility(View.VISIBLE);
        }
        for( ; i < 3 ; i++)
            humanCards[i].setVisibility(View.GONE);

        // Adjust Robot missing cards
        for(i=0; i < robotHand.size() ; i++ )
            robotCards[i].setVisibility(View.VISIBLE);
        for( ; i < 3 ; i++)
            robotCards[i].setVisibility(View.GONE);

        //Set the number of remaining cards in the deck
        cAct.remaining.setText(String.valueOf(cAct.game.remainingCards()));
        if(cAct.game.remainingCards() == 0) // make the deck not visible
            cAct.remaining.setVisibility(View.INVISIBLE);

    }
}
