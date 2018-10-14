package com.steinbacher.jumpstar;

import android.app.Fragment;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;

import com.steinbacher.jumpstar.core.Exercise;
import com.steinbacher.jumpstar.core.StandardExercise;
import com.steinbacher.jumpstar.core.TimeExercise;
import com.steinbacher.jumpstar.util.Factory;

/**
 * Created by stge on 04.10.18.
 */

public class ExerciseDetailActivity extends AppCompatActivity{
    private static final String TAG = "ExerciseDetailActivity";

    public static final String EXERCISE_ID = "exercise_id";
    public static final String EXERCISE_TYPE = "exercise_type";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_exercise_detail);

        final int exerciseId = getIntent().getIntExtra(EXERCISE_ID, -1);
        final Exercise.Type exerciseType = Exercise.Type.valueOf(getIntent().getStringExtra(EXERCISE_TYPE));
        final Exercise exercise = Factory.getExercise(exerciseType, exerciseId);

        if(exerciseType == Exercise.Type.STANDARD) {
            StandardExerciseFragment fragment = new StandardExerciseFragment();
            fragment.setExercise((StandardExercise) exercise);
            getSupportFragmentManager().beginTransaction().add(R.id.content, fragment)
                    .commit();

        } else if(exerciseType == Exercise.Type.TIME) {
            TimeExerciseFragment fragment = new TimeExerciseFragment();
            fragment.setExercise((TimeExercise) exercise);
            getSupportFragmentManager().beginTransaction().add(R.id.content, fragment)
                    .commit();
        } else {
            Log.e(TAG, "Error: unknown exercise type");
        }

    }
}
