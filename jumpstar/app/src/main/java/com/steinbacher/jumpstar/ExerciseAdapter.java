package com.steinbacher.jumpstar;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.steinbacher.jumpstar.core.Exercise;
import com.steinbacher.jumpstar.core.TrainingsPlan;
import com.steinbacher.jumpstar.view.ExerciseOverviewLine;
import com.steinbacher.jumpstar.view.TrainingsPlanView;

import java.util.List;

/**
 * Created by stge on 27.09.18.
 */

public class ExerciseAdapter extends ArrayAdapter implements ExerciseOverviewLine.IExerciseOverviewLineListener{
    private static final String TAG = "ExerciseAdapter";

    private boolean mShowAddExerciseButton = false;

    private ExerciseOverviewLine.IExerciseOverviewLineListener mListener;
    public void setExerciseOverviewLineListener(ExerciseOverviewLine.IExerciseOverviewLineListener listener) {
        mListener = listener;
    }

    public ExerciseAdapter(@NonNull Context context, int resource, @NonNull List objects, boolean showAddExerciseButton) {
        super(context, resource, objects);

        mShowAddExerciseButton = showAddExerciseButton;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Exercise exercise = (Exercise) getItem(position);

        if (convertView == null) {
            convertView = new ExerciseOverviewLine(getContext());
        }

        ExerciseOverviewLine view = (ExerciseOverviewLine) convertView;
        view.setExercise(exercise);
        view.showAddExerciseButton(mShowAddExerciseButton);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ExerciseDetailActivity.class);
                intent.putExtra(ExerciseDetailActivity.EXERCISE_ID, exercise.getId());
                intent.putExtra(ExerciseDetailActivity.EXERCISE_TYPE, exercise.getType().toString());
                v.getContext().startActivity(intent);
            }
        });
        view.setExerciseOverviewLineListener(this);

        return view;
    }

    @Override
    public void onAddExerciseClicked(Exercise clickedExercise) {
        if(mListener != null) {
            mListener.onAddExerciseClicked(clickedExercise);
        } else {
            Log.d(TAG, "onAddExerciseClicked: no listener set");
        }
    }
}
