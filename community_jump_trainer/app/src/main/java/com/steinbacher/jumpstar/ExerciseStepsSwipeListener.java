package com.steinbacher.jumpstar;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.steinbacher.jumpstar.core.Exercise;
import com.steinbacher.jumpstar.util.OnSwipeTouchListener;
import com.steinbacher.jumpstar.view.ExerciseStepsView;

/**
 * Created by stge on 12.07.18.
 */

public class ExerciseStepsSwipeListener extends OnSwipeTouchListener {
    private Context mContext;
    private Exercise mExercise;
    private ExerciseStepsView mSteps;

    public ExerciseStepsSwipeListener(Context context, Exercise exercise, ExerciseStepsView exerciseStepsView) {
        super(context);

        mContext = context;
        mExercise = exercise;
        mSteps = exerciseStepsView;
    }

    @Override
    public void onSwipeLeft() {
        super.onSwipeLeft();

        if (mExercise.getDescription().getSteps().size() - 1 > mSteps.getCurrentShownStep()) {
            Animation animSlide = AnimationUtils.loadAnimation(mContext,
                    R.anim.slide_out_left);
            animSlide.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mSteps.viewStep(mSteps.getCurrentShownStep() + 1);

                    Animation animSlide = AnimationUtils.loadAnimation(mContext,
                            R.anim.slide_in_right);
                    mSteps.startAnimation(animSlide);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            mSteps.startAnimation(animSlide);

        }
    }

    @Override
    public void onSwipeRight() {
        super.onSwipeRight();

        if(mSteps.getCurrentShownStep() > 0) {
            Animation animSlide = AnimationUtils.loadAnimation(mContext,
                    R.anim.slide_out_right);
            animSlide.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mSteps.viewStep(mSteps.getCurrentShownStep() - 1);

                    Animation animSlide = AnimationUtils.loadAnimation(mContext,
                            R.anim.slide_in_left);
                    mSteps.startAnimation(animSlide);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            mSteps.startAnimation(animSlide);
        }
    }
}
