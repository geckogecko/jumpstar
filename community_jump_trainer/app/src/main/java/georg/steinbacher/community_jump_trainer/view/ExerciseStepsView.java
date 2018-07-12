package georg.steinbacher.community_jump_trainer.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.HashMap;

import georg.steinbacher.community_jump_trainer.ExerciseStepsSwipeListener;
import georg.steinbacher.community_jump_trainer.R;
import georg.steinbacher.community_jump_trainer.core.Exercise;
import georg.steinbacher.community_jump_trainer.core.ExerciseStep;
import georg.steinbacher.community_jump_trainer.core.TrainingsPlan;

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
        final String imageName = mExercise.getType().name().toLowerCase() + "_" +
                mExercise.getId() + "_" +
                step.getStepNr();
        final int resourceId = mResources.getIdentifier(imageName, "drawable",
                getContext().getPackageName());

        if(resourceId != 0) {
            mImageView.setImageDrawable(ContextCompat.getDrawable(mContext, resourceId));
        } else {
            Log.e(TAG, "description image for step: " + imageName + " not found");
            //TODO what should we do if the image is not found?
        }


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
