package georg.steinbacher.community_jump_trainer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

import java.util.List;

import georg.steinbacher.community_jump_trainer.core.Equipment;
import georg.steinbacher.community_jump_trainer.core.TrainingsPlan;
import georg.steinbacher.community_jump_trainer.util.Factory;
import georg.steinbacher.community_jump_trainer.view.TrainingsPlanView;

import static georg.steinbacher.community_jump_trainer.TrainingsPlanDetailActivity.TRAININGS_PLAN_ID;


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

        LinearLayoutCompat linearLayoutCompat = (LinearLayoutCompat) view;

        //TODO add only plans which the user has the equipment for
        // or mark plans with missing equipment
        for (TrainingsPlan trainingsPlan : trainingsPlanList) {
            for (Equipment.Type type : trainingsPlan.getNeededEquipmentTypes()) {
                Log.i(TAG, "onViewCreated: " + type.name().toString());
            }

            TrainingsPlanView trainingsPlanView = new TrainingsPlanView(getContext());
            trainingsPlanView.setTrainingsPlan(trainingsPlan);
            linearLayoutCompat.addView(trainingsPlanView);
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
                mainActivity.changeContent(R.id.navigation_home);
            }
        }

    }
}
