package com.steinbacher.jumpstar.view;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.steinbacher.jumpstar.Configuration;
import com.steinbacher.jumpstar.ExerciseStepsSwipeListener;
import com.steinbacher.jumpstar.R;
import com.steinbacher.jumpstar.core.Exercise;
import com.steinbacher.jumpstar.core.ExerciseStep;
import com.steinbacher.jumpstar.util.DrawableLoader;

import static android.content.ContentValues.TAG;

public class ExerciseStepsView extends LinearLayoutCompat{
    private Exercise mExercise;
    private View mView;
    private Context mContext;
    private Resources mResources;

    private AppCompatImageView mImageView;
    private AppCompatTextView mTextView;

    private AppCompatImageButton mButtonLeft;
    private AppCompatImageButton mButtonRight;

    private int mCurrentShownStep = -1;

    private ExerciseStepsSwipeListener mSwipeListener;

    public ExerciseStepsView(Context context) {
        super(context);
        init(context);
    }

    public ExerciseStepsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mView = inflate(mContext, R.layout.view_exercise_steps, this);

        mImageView = mView.findViewById(R.id.image_view);
        mTextView = mView.findViewById(R.id.text_view);
        if(!Configuration.getBoolean(mContext, Configuration.SHOW_EXERCISE_DESCRIPTION)) {
            mTextView.setVisibility(View.GONE);
        }

        mResources = getContext().getResources();

        mButtonLeft = mView.findViewById(R.id.button_left);
        mButtonLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeListener.onSwipeRight();
            }
        });

        mButtonRight = mView.findViewById(R.id.button_right);
        mButtonRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeListener.onSwipeLeft();
            }
        });
    }

    public void setTrainingsplan(Exercise exercise) {
        mExercise = exercise;
        if(exercise.getDescription().getSteps().size() > 0) {
            viewStep(0);
        }

        //Swipe listener
        mSwipeListener = new ExerciseStepsSwipeListener(getContext(), mExercise, this);
        mView.setOnTouchListener(mSwipeListener);
    }

    public void viewStep(int nr) {
        ExerciseStep step = mExercise.getDescription().getSteps().get(nr);

        DrawableLoader.loadExerciseImage(getContext(), mExercise, step.getStepNr(), mImageView);

        mTextView.setText(step.getDescription());
        mCurrentShownStep = nr;

        if(mExercise.getDescription().getSteps().size() == 1) {
            mButtonLeft.setVisibility(View.INVISIBLE);
            mButtonRight.setVisibility(View.INVISIBLE);
        } else if(nr <= 0) {
            mButtonLeft.setVisibility(View.INVISIBLE);
            mButtonRight.setVisibility(View.VISIBLE);
        } else if(nr == mExercise.getDescription().getSteps().size() -1) {
            mButtonLeft.setVisibility(View.VISIBLE);
            mButtonRight.setVisibility(View.INVISIBLE);
        } else {
            mButtonLeft.setVisibility(View.VISIBLE);
            mButtonRight.setVisibility(View.VISIBLE);
        }
    }

    public int getCurrentShownStep() {
        return mCurrentShownStep;
    }
}
