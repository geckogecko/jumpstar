package com.steinbacher.jumpstar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import com.steinbacher.jumpstar.core.Equipment;
import com.steinbacher.jumpstar.core.TrainingsPlan;
import com.steinbacher.jumpstar.util.Factory;
import com.steinbacher.jumpstar.view.TrainingsPlanView;


public class TrainingsPlanSelectionFragment extends Fragment {
    private static final String TAG = "TrainingsPlanSelectionF";

    private View mView;
    private int mCurrentTrainingsPlanCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trainings_plan_selection, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;

        new TrainingsPlanLoader().execute();

        //for detecting if a new currentTrainingsPlan got added
        mCurrentTrainingsPlanCount = Configuration.getIntArray(getContext(), Configuration.CURRENT_TRAININGSPLANS_ID_KEY).length;
    }

    private class TrainingsPlanLoader extends AsyncTask<Void, Void, List<TrainingsPlan>> {

        @Override
        protected List<TrainingsPlan> doInBackground(Void... params) {
            List<TrainingsPlan> trainingsPlanList = Factory.getAllTrainingsPlans();
            List<TrainingsPlan> filteredTrainingsPlanList = new ArrayList<>();
            for (TrainingsPlan trainingsPlan : trainingsPlanList) {
                boolean showPlan = true;
                for (Equipment equipment : trainingsPlan.getNeededEquipment()) {
                    Equipment.Type type = equipment.getType();
                    if(type == Equipment.Type.HOME && !Configuration.getBoolean(getContext(), Configuration.EQUIPMENT_HOME, true)) {
                        showPlan = false;
                        break;
                    } else if(type == Equipment.Type.GYM && !Configuration.getBoolean(getContext(), Configuration.EQUIPMENT_GYM, true)) {
                        showPlan = false;
                        break;
                    } else if(type == Equipment.Type.BOTH && !Configuration.getBoolean(getContext(), Configuration.EQUIPMENT_HOME, true)
                            && !Configuration.getBoolean(getContext(), Configuration.EQUIPMENT_GYM, true)) {
                        showPlan = false;
                        break;
                    }
                }

                if(showPlan) {
                    filteredTrainingsPlanList.add(trainingsPlan);
                }
            }

            return filteredTrainingsPlanList;
        }

        @Override
        protected void onPostExecute(List<TrainingsPlan> result) {
            if(result.size() == 0) {
                AppCompatTextView textView = mView.findViewById(R.id.txt_no_trainingsplan_found);
                textView.setVisibility(View.VISIBLE);
            }

            ListView listView = mView.findViewById(R.id.selection_list_view);
            TrainingsplanAdapter trainingsPlanAdapter = new TrainingsplanAdapter(getContext(), R.layout.view_exercises, result);
            listView.setAdapter(trainingsPlanAdapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        final int newCurrentTrainingsPlanCount = Configuration.getIntArray(getContext(), Configuration.CURRENT_TRAININGSPLANS_ID_KEY).length;
        if(mCurrentTrainingsPlanCount != newCurrentTrainingsPlanCount) {
            MainActivity mainActivity = (MainActivity)getActivity();
            if(mainActivity != null) {
                mainActivity.changeContent(R.id.navigation_home, true);
            }
        }

    }
}
