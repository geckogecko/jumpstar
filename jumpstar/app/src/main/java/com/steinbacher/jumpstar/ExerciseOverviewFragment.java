package com.steinbacher.jumpstar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ListView;

import com.steinbacher.jumpstar.core.Equipment;
import com.steinbacher.jumpstar.core.Exercise;
import com.steinbacher.jumpstar.core.TrainingsPlan;
import com.steinbacher.jumpstar.util.Factory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stge on 24.09.18.
 */

public class ExerciseOverviewFragment extends Fragment {
    private View mView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;
    }

    private class ExercisesLoader extends AsyncTask<Void, Void, List<Exercise>> {

        @Override
        protected List<Exercise> doInBackground(Void... params) {

            return Factory.;
        }

        @Override
        protected void onPostExecute(List<TrainingsPlan> result) {

            ListView listView = mView.findViewById(R.id.selection_list_view);
            TrainingsplanAdapter trainingsPlanAdapter = new TrainingsplanAdapter(getContext(), R.layout.fragment_exercise_overview, result);
            listView.setAdapter(trainingsPlanAdapter);
        }
    }
}
