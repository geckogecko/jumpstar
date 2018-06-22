package georg.steinbacher.community_jump_trainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import georg.steinbacher.community_jump_trainer.view.EquipmentView;

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

        //Description
        TextView txtDescription = findViewById(R.id.detail_trainings_plan_description);
        txtDescription.setText(mTrainingsPlan.getDescription());

        //Equipment
        EquipmentView equipmentViewHolder= findViewById(R.id.equipment_view);
        List<Equipment> equipmentList = mTrainingsPlan.getNeededEquipment();
        equipmentViewHolder.setEquipment(equipmentList);

        //exercises
        TextView txtExercises = findViewById(R.id.detail_trainings_plan_exercises);
        List<TrainingsPlanEntry> exercises = mTrainingsPlan.getEntries();
        String exercisesString = "";
        for (TrainingsPlanEntry exercise : exercises) {
            if(exercise.getClass().equals(TimeExercise.class) || exercise.getClass().equals(StandardExercise.class)) {
                exercisesString += ((Exercise)exercise).getName() + ", ";
            }
        }
        exercisesString = exercisesString.substring(0,exercisesString.length()-1);
        txtExercises.setText(exercisesString);

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
