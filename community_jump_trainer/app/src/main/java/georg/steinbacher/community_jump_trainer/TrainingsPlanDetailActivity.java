package georg.steinbacher.community_jump_trainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import georg.steinbacher.community_jump_trainer.core.Equipment;
import georg.steinbacher.community_jump_trainer.core.Exercise;
import georg.steinbacher.community_jump_trainer.core.StandardExercise;
import georg.steinbacher.community_jump_trainer.core.TimeExercise;
import georg.steinbacher.community_jump_trainer.core.TrainingsPlan;
import georg.steinbacher.community_jump_trainer.core.TrainingsPlanEntry;
import georg.steinbacher.community_jump_trainer.util.Factory;

import static georg.steinbacher.community_jump_trainer.Configuration.CURRENT_TRAININGSPLANS_ID_KEY;

//TODO if this trainingsplan includes other trainingsplans -> show them here
public class TrainingsPlanDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "TrainingsPlanDetailActi";
    public static final String TRAININGS_PLAN_ID = "trainings_plan_id";

    private TrainingsPlan mTrainingsPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainings_plan_detail);

        int trainingsPlanId = getIntent().getIntExtra(TRAININGS_PLAN_ID, -1);
        if(trainingsPlanId == -1) {
            Log.e(TAG, "onCreate: No trainingsplanId provided");
            finish();
        } else {
            mTrainingsPlan = Factory.createTraingsPlan(trainingsPlanId);
        }

        //title
        TextView txtName = findViewById(R.id.detail_trainings_plan_name);
        txtName.setText(mTrainingsPlan.getName());

        //Equipment
        //TODO if entry is a trainingsplan step into it and load the equipment
        TextView equipmentTextView = findViewById(R.id.exercise_equipment);
        List<String> equipmentList = new ArrayList<>();
        for (Equipment equipment: mTrainingsPlan.getNeededEquipment()) {
            equipmentList.add(equipment.getName());
        }

        if(equipmentList.size() > 0) {
            String equipmentString = "";
            for (String equipment : equipmentList) {
                equipmentString += equipment + ",";
            }
            equipmentString = equipmentString.substring(0, equipmentString.length() - 1);
            equipmentTextView.setText(getString(R.string.exercise_equipment, equipmentString));
        } else {
            equipmentTextView.setVisibility(View.GONE);
        }

        //add button
        AppCompatButton btnAddTrainingsPlan = findViewById(R.id.detail_button_add_trainings_plan);
        final int[] currentPlans = Configuration.getIntArray(getApplicationContext(), CURRENT_TRAININGSPLANS_ID_KEY);
        boolean planIsCurrentTrainingsPlan = false;
        for (int i = 0; i < currentPlans.length; i++) {
            if(currentPlans[i] == mTrainingsPlan.getId()) {
                planIsCurrentTrainingsPlan = true;
                break;
            }
        }

        if(planIsCurrentTrainingsPlan) {
            btnAddTrainingsPlan.setVisibility(View.GONE);
        } else {
            btnAddTrainingsPlan.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        final int[] currentPlans = Configuration.getIntArray(getApplicationContext(), CURRENT_TRAININGSPLANS_ID_KEY);
        int[] newCurrentPlans = new int[currentPlans.length +1];
        newCurrentPlans[0] = mTrainingsPlan.getId();
        for (int i = 0; i < currentPlans.length; i++) {
            newCurrentPlans[i+1] = currentPlans[i];
        }
        Configuration.set(getApplicationContext(), CURRENT_TRAININGSPLANS_ID_KEY, newCurrentPlans);

        finish();
    }
}
