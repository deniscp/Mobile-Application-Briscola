package it.polimi.group06.activity.helper;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
        /* ---- Code for launching the appropriate animation
                knowing who played which card
         */

        final ImageView shuttleView = cAct.findViewById(R.id.shuttle);
        final ImageView shuttleRobotView = cAct.findViewById(R.id.shuttle_robot);


        if (cPlayer == FIRSTPLAYER) {
            shuttleView.bringToFront();


            final ViewGroup rootView = cAct.findViewById(R.id.root_view);
            final View fromView = cAct.humanHand[cChoice];
            final View toView = cAct.findViewById(R.id.humanplayed);

            AnimatorSet animatorSet = getViewToViewScalingAnimator(rootView, shuttleView, fromView, toView, CARDVELOCITY, 0);

            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    shuttleView.setImageResource(cAct.getCardDrawable(cCard));
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

            shuttleRobotView.bringToFront();


            final ViewGroup rootView = cAct.findViewById(R.id.root_view);
            final View fromView = cAct.robotHand[cChoice];
            final View toView = cAct.findViewById(R.id.robotplayed);

            AnimatorSet animatorSet = getViewToViewScalingAnimator(rootView, shuttleRobotView, fromView, toView, CARDVELOCITY, 0);

            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    shuttleRobotView.setImageResource(cAct.getCardDrawable(cCard));
                    shuttleRobotView.setVisibility(View.VISIBLE);
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
        }
    }
}