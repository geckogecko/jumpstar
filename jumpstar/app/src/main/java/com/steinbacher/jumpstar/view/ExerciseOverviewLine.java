package com.steinbacher.jumpstar.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.squareup.picasso.Picasso;
import com.steinbacher.jumpstar.ExerciseDetailActivity;
import com.steinbacher.jumpstar.MainActivity;
import com.steinbacher.jumpstar.R;
import com.steinbacher.jumpstar.core.Exercise;
import com.steinbacher.jumpstar.core.ExerciseStep;
import com.steinbacher.jumpstar.core.TrainingsPlan;
import com.steinbacher.jumpstar.core.TrainingsPlanEntry;
import com.steinbacher.jumpstar.util.DrawableLoader;

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
    private AppCompatImageButton mAddButton;

    private IExerciseOverviewLineListener mListener;
    public interface IExerciseOverviewLineListener {
        void onAddExerciseClicked(Exercise clickedExercise);
        void onExerciseUndoClicked(Exercise undoExercise);
    }

    public void setExerciseOverviewLineListener(IExerciseOverviewLineListener listener) {
        mListener = listener;
    }

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
        mAddButton = mView.findViewById(R.id.add_button);
        mAddButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null) {
                    mListener.onAddExerciseClicked(mExercise);
                    Snackbar snackbar = Snackbar.make(mView, mContext.getString(R.string.create_new_plan_exercise_added, mExercise.getName()),
                            Snackbar.LENGTH_LONG)
                            .setAction(R.string.create_new_plan_undo, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(mListener != null) {
                                        mListener.onExerciseUndoClicked(mExercise);
                                    }
                                }
                            });
                    snackbar.show();
                } else {
                    Log.d(TAG, "onClick: no listener added");
                }
            }
        });
    }

    public void setExercise(Exercise exercise) {
        mExercise = exercise;

        mName.setText(mExercise.getName());
        DrawableLoader.loadExerciseImage(getContext(), mExercise, 0, mImage);
    }

    public Exercise getExercise() {
        return mExercise;
    }

    public void showAddExerciseButton(boolean show) {
        if(show) {
            mAddButton.setVisibility(View.VISIBLE);
        } else {
            mAddButton.setVisibility(View.INVISIBLE);
        }
    }
}
