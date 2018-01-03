package it.polimi.group06.activity.helper;

import android.view.View;
import android.widget.ImageView;

import it.polimi.group06.activity.GameActivity;
import it.polimi.group06.domain.Card;

import static it.polimi.group06.domain.Constants.FIRSTPLAYER;
import static it.polimi.group06.domain.Constants.SECONDPLAYER;

/**
 * Created by denis on 30/12/17.
 */

public class LaunchCard implements Runnable {

    final private int cPlayer;
    final private int cChoice;
    final private Card cCard;
    final private GameActivity cAct;

    public LaunchCard(int currentPlayer, int currentChoice, Card currentCard, GameActivity currentActivity) {
        cPlayer = currentPlayer;
        cChoice = currentChoice;
        cCard = currentCard;
        cAct = currentActivity;
    }

    @Override
    public void run() {
//    void launchAnimation(int currentPlayerPosition, int currentPlayedCard, Card currentCard) {
        /* ---- Code for launching the appropriate animation
                knowing who played which card
         */

        ImageView humanCards[] = {cAct.cardzero_image,cAct.cardone_image,cAct.cardtwo_image};
        ImageView robotCards[] = {cAct.robotcard1,cAct.robotcard2,cAct.robotcard3};

        if (cPlayer == FIRSTPLAYER){
            cAct.humancard.setImageResource(cAct.getCardDrawable(cCard, cAct.getApplicationContext()));
            humanCards[cChoice].setVisibility(View.GONE);

            cAct.humancard.startAnimation(cAct.humanmiddle);

        }
        else if (cPlayer == SECONDPLAYER) {

            cAct.robotcard.setImageResource(cAct.getCardDrawable(cCard, cAct.getApplicationContext()));
            robotCards[cChoice].setVisibility(View.GONE);
        }
    }
}