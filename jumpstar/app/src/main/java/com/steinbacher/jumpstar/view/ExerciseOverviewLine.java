package com.steinbacher.jumpstar.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.View;

import com.steinbacher.jumpstar.R;
import com.steinbacher.jumpstar.core.Exercise;

/**
 * Created by stge on 27.09.18.
 */

public class ExerciseOverviewLine extends LinearLayoutCompat {
    private Context mContext;
    private View mView;
    private Exercise mExercise;

    private AppCompatTextView mName;

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
    }

    public void setExercise(Exercise exercise) {
        mExercise = exercise;

        mName.setText(mExercise.getName());
    }
}
