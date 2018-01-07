package it.polimi.group06.activity.helper;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by denis on 06/01/18.
 */

public class CardAnimation {

    public static AnimatorSet getViewToViewScalingAnimator(final /*RelativeLayout*/ ViewGroup parentView,
                                                           final ImageView viewToAnimate,
                                                           final View fromView,
                                                           final View toView,
                                                           final long duration,
                                                           final long startDelay) {

        Rect fromViewRect = new Rect();
        Rect toViewRect = new Rect();
        float actualRotation, toActualLeft, toActualTop;
        int toActualHeight, toActualWidth;

        fromView.getGlobalVisibleRect(fromViewRect);

        //Store actual rotation
        actualRotation=toView.getRotation();

        //Pretend no rotation
        toView.setRotation(0f);
        toView.getGlobalVisibleRect(toViewRect);

//        toViewRect.height  toViewRect.width
        toActualHeight=toViewRect.height();
        toActualWidth=toViewRect.width();

//        toViewRect.left  toViewRect.top
        toActualLeft=toViewRect.left;
        toActualTop=toViewRect.top;

        //Restore previous actual rotation
        toView.setRotation(actualRotation);
        toView.getGlobalVisibleRect(toViewRect);




        // get all coordinates at once
        final Rect parentViewRect = new Rect(), viewToAnimateRect = new Rect();
        parentView.getGlobalVisibleRect(parentViewRect);
        viewToAnimate.getGlobalVisibleRect(viewToAnimateRect);

        viewToAnimate.setScaleX(1f);
        viewToAnimate.setScaleY(1f);

        // rescaling of the object on X-axis
        final ValueAnimator valueAnimatorWidth = ValueAnimator.ofInt(fromViewRect.width(), toActualWidth);
        valueAnimatorWidth.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // Get animated width value update
                int newWidth = (int) valueAnimatorWidth.getAnimatedValue();

                // Get and update LayoutParams of the animated view
                ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) viewToAnimate.getLayoutParams();

                lp.width = newWidth;
                viewToAnimate.setLayoutParams(lp);
            }
        });

        // rescaling of the object on Y-axis
        final ValueAnimator valueAnimatorHeight = ValueAnimator.ofInt(fromViewRect.height(), toActualHeight);
        valueAnimatorHeight.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // Get animated width value update
                int newHeight = (int) valueAnimatorHeight.getAnimatedValue();

                // Get and update LayoutParams of the animated view
                ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) viewToAnimate.getLayoutParams();
                lp.height = newHeight;
                viewToAnimate.setLayoutParams(lp);
            }
        });

        // moving of the object on X-axis
        ObjectAnimator translateAnimatorX = ObjectAnimator.ofFloat(viewToAnimate, "X", fromViewRect.left - parentViewRect.left, toActualLeft - parentViewRect.left);

        // moving of the object on Y-axis
        ObjectAnimator translateAnimatorY = ObjectAnimator.ofFloat(viewToAnimate, "Y", fromViewRect.top - parentViewRect.top, toActualTop - parentViewRect.top);

        // rotating the view
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(viewToAnimate, "rotation", fromView.getRotation(), toView.getRotation());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new DecelerateInterpolator(1f));
        animatorSet.setDuration(duration); // can be decoupled for each animator separately
        animatorSet.setStartDelay(startDelay); // can be decoupled for each animator separately
        animatorSet.playTogether(valueAnimatorWidth, valueAnimatorHeight, translateAnimatorX, translateAnimatorY,rotateAnimator);
        return animatorSet;
    }

}