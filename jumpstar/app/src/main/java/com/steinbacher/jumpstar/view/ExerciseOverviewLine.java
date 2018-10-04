package com.steinbacher.jumpstar.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.steinbacher.jumpstar.R;
import com.steinbacher.jumpstar.core.Exercise;
import com.steinbacher.jumpstar.core.ExerciseStep;

import static android.content.ContentValues.TAG;

/**
 * Created by stge on 27.09.18.
 */

public class ExerciseOverviewLine extends LinearLayoutCompat {
    private Context mContext;
    private View mView;
    private Exercise mExercise;

    private AppCompatTextView mName;
    private AppCompatImageView mImage;

    public ExerciseOverviewLine(Context context) {
        super(context);
        init(context);
    }

    public ExerciseOverviewLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ExerciseOverviewLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mView = inflate(mContext, R.layout.view_exercise_overview_line, this);

        mName = mView.findViewById(R.id.txt_exercise_name);
        mImage = mView.findViewById(R.id.image_view);
    }

    public void setExercise(Exercise exercise) {
        mExercise = exercise;

        mName.setText(mExercise.getName());

        ExerciseStep step = mExercise.getDescription().getSteps().get(0);
        final String imageName = mExercise.getType().name().toLowerCase() + "_" +
                mExercise.getId() + "_" +
                step.getStepNr();
        final int resourceId = mContext.getResources().getIdentifier(imageName, "drawable",
                getContext().getPackageName());

        if(resourceId != 0) {
            mImage.setImageDrawable(ContextCompat.getDrawable(mContext, resourceId));
        } else {
            Log.e(TAG, "description image for step: " + imageName + " not found");
            //TODO what should we do if the image is not found?
        }
    }
}
