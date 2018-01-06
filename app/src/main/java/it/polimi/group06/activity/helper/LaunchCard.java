package it.polimi.group06.activity.helper;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.View.*;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import it.polimi.group06.R;
import it.polimi.group06.activity.GameActivity;
import it.polimi.group06.domain.Card;

import static it.polimi.group06.activity.helper.CardAnimation.getViewToViewScalingAnimator;
import static it.polimi.group06.activity.helper.Constants.CARDVELOCITY;
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

        final ImageView humanCards[] = cAct.humanHand;
        ImageView robotCards[] = {cAct.robotcard1, cAct.robotcard2, cAct.robotcard3};

        if (cPlayer == FIRSTPLAYER) {
//            cAct.humancard.setImageResource(cAct.getCardDrawable(cCard, cAct.getApplicationContext()));
//            humanCards[cChoice].setVisibility(View.GONE);

//            cAct.humancard.startAnimation(cAct.humanmiddle);

            final ViewGroup rootView = cAct.findViewById(R.id.root_view);
            final View fromView = cAct.humanHand[cChoice];
            final View toView = cAct.findViewById(R.id.humanplayed);
            final ImageView shuttleView = cAct.findViewById(R.id.shuttle);

            Rect fromViewRect = new Rect();
            Rect toViewRect = new Rect();
            fromView.getGlobalVisibleRect(fromViewRect);
            toView.getGlobalVisibleRect(toViewRect);

            AnimatorSet animatorSet = getViewToViewScalingAnimator(rootView, shuttleView, fromViewRect, toViewRect, CARDVELOCITY , 0);

            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    shuttleView.setImageResource(cAct.getCardDrawable(cCard, cAct.getApplicationContext()));
                    shuttleView.setVisibility(View.VISIBLE);
                    fromView.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
//                    shuttleView.setVisibility(View.GONE);
//                    fromView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animatorSet.start();

        } else if (cPlayer == SECONDPLAYER) {

            cAct.robotcard.setImageResource(cAct.getCardDrawable(cCard, cAct.getApplicationContext()));
            robotCards[cChoice].setVisibility(View.GONE);
        }
    }
}