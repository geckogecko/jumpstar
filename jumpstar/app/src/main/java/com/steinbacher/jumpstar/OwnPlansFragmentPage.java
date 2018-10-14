package com.steinbacher.jumpstar;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.github.mikephil.charting.data.Entry;
import com.steinbacher.jumpstar.core.Equipment;
import com.steinbacher.jumpstar.core.Exercise;
import com.steinbacher.jumpstar.core.TrainingsPlan;
import com.steinbacher.jumpstar.core.TrainingsPlanEntry;
import com.steinbacher.jumpstar.db.PlanContract;
import com.steinbacher.jumpstar.db.PlanReader;
import com.steinbacher.jumpstar.db.VerticalHeightContract;
import com.steinbacher.jumpstar.db.VerticalHeightReader;
import com.steinbacher.jumpstar.util.Factory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stge on 04.10.18.
 */

public class OwnPlansFragmentPage extends Fragment{
    private static final String TAG = "OwnPlansFragmentPage";

    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_own_plans, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;

        new OwnPlansFragmentPage.TrainingsPlanLoader().execute();
    }


    private class TrainingsPlanLoader extends AsyncTask<Void, Void, List<TrainingsPlan>> {

        @Override
        protected List<TrainingsPlan> doInBackground(Void... params) {
            PlanReader reader = new PlanReader(getContext());
            Cursor cursor = reader.getAll();

            List<TrainingsPlan> plans = new ArrayList<>();
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    plans.add(Factory.createTraingsPlan(cursor));
                }
            }
            return plans;
        }

        @Override
        protected void onPostExecute(List<TrainingsPlan> result) {
            ListView listView = mView.findViewById(R.id.selection_list_view);
            TrainingsplanAdapter trainingsPlanAdapter = new TrainingsplanAdapter(getContext(), R.layout.view_exercises, result);
            listView.setAdapter(trainingsPlanAdapter);
        }
    }
}
