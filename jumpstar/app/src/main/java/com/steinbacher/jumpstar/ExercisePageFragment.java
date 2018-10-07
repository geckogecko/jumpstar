package com.steinbacher.jumpstar;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.InputType;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.steinbacher.jumpstar.core.Exercise;
import com.steinbacher.jumpstar.db.VerticalHeightWriter;
import com.steinbacher.jumpstar.util.Factory;
import com.steinbacher.jumpstar.view.ExerciseOverviewLine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Georg Steinbacher on 27.09.18.
 */

public class ExercisePageFragment extends Fragment {
    private View mView;
    private ListView mListView;
    private ExerciseAdapter mAdapter;
    private boolean mShowAddExerciseButton = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_exercise_page, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;
        mListView = mView.findViewById(R.id.list);
    }

    public void init(Exercise.Category category, boolean showAddExerciseButton) {
        mShowAddExerciseButton = showAddExerciseButton;
        new ExercisesLoader().execute(category);
    }

    private class ExercisesLoader extends AsyncTask<Exercise.Category, Void, List<Exercise>> {
        @Override
        protected List<Exercise> doInBackground(Exercise.Category... cats) {
            List<Exercise> allExercises = Factory.getAllExercises();
            List<Exercise> filteredExercises = new ArrayList<>();

            for(Exercise exercise : allExercises) {
                if(exercise.getCategory().equals(cats[0])) {
                    filteredExercises.add(exercise);
                }
            }

            return filteredExercises;
        }

        @Override
        protected void onPostExecute(List<Exercise> result) {
            mAdapter = new ExerciseAdapter(getContext(), R.layout.fragment_exercise_page, result, mShowAddExerciseButton);
            mListView.setAdapter(mAdapter);
        }
    }
}