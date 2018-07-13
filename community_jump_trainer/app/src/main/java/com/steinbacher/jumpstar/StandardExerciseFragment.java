package com.steinbacher.jumpstar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.steinbacher.jumpstar.core.StandardExercise;
import com.steinbacher.jumpstar.drawables.CategoryPaints;
import com.steinbacher.jumpstar.view.ExerciseStepsView;

public class StandardExerciseFragment extends Fragment {
    private static final String TAG = "StandardExerciseFragmen";

    private StandardExercise mExercise;
    private View mView;

    private ExerciseStepsView mSteps;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_standard_exercise, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mView = view;

        //Name
        TextView nameTextView = mView.findViewById(R.id.exercise_name);
        nameTextView.setText(mExercise.getName());

        //ExerciseSteps
        mSteps = mView.findViewById(R.id.exercise_step_view);
        mSteps.setTrainingsplan(mExercise);

        //Sets
        TextView setsTextView = mView.findViewById(R.id.exercise_sets);
        setsTextView.setText(getString(R.string.exercise_sets, Integer.toString(mExercise.getSets())));

        //Repetitions
        TextView repetitionsTextView = mView.findViewById(R.id.exercise_repetitions);
        int[] reps = mExercise.getRepetitions();

        if(reps[0] == -1) {
            repetitionsTextView.setText(getString(R.string.exercise_repetitions_standard_as_much));
        } else {
            String repsString = Integer.toString(reps[0]);

            if (reps.length > 1) {
                for (int i = 1; i < reps.length; i++) {
                    repsString += ",";
                    repsString += Integer.toString(reps[i]);
                }
            }
            repetitionsTextView.setText(getString(R.string.exercise_repetitions, repsString));
        }

        //Category
        TextView txtCategory = mView.findViewById(R.id.exercise_category);
        String category = mExercise.getCategory().name().toLowerCase();
        category = category.substring(0, 1).toUpperCase() + category.substring(1);
        txtCategory.setText(category);
        txtCategory.setTextColor(CategoryPaints.getPrimaryColor(getContext(), mExercise.getCategory()).getColor());

        //Swipe listener
        //view.setOnTouchListener(new ExerciseStepsSwipeListener(getContext(), mExercise, mSteps));
    }

    public void setExercise(StandardExercise exercise) {
        mExercise = exercise;
    }
}
