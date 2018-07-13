package com.steinbacher.jumpstar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import com.steinbacher.jumpstar.core.Equipment;
import com.steinbacher.jumpstar.core.TrainingsPlan;
import com.steinbacher.jumpstar.util.Factory;
import com.steinbacher.jumpstar.view.TrainingsPlanView;


public class TrainingsPlanSelectionFragment extends Fragment {
    private static final String TAG = "TrainingsPlanSelectionF";

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

        List<TrainingsPlan> trainingsPlanList = Factory.getAllTrainingsPlans();
        LinearLayoutCompat linearLayoutCompat = view.findViewById(R.id.container);

        boolean showPlan = true;
        for (TrainingsPlan trainingsPlan : trainingsPlanList) {
            for (Equipment equipment : trainingsPlan.getNeededEquipment()) {
                Equipment.Type type = equipment.getType();
                if(type == Equipment.Type.HOME && !Configuration.getBoolean(getContext(), Configuration.EQUIPMENT_HOME, true)) {
                    showPlan = false;
                } else if(type == Equipment.Type.GYM && !Configuration.getBoolean(getContext(), Configuration.EQUIPMENT_GYM, true)) {
                    showPlan = false;
                }
            }

            if(showPlan) {
                TrainingsPlanView trainingsPlanView = new TrainingsPlanView(getContext());
                trainingsPlanView.setTrainingsPlan(trainingsPlan);
                linearLayoutCompat.addView(trainingsPlanView);
            }
        }

        //for detecting if a new currentTrainingsPlan got added
        mCurrentTrainingsPlanCount = Configuration.getIntArray(getContext(), Configuration.CURRENT_TRAININGSPLANS_ID_KEY).length;
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
