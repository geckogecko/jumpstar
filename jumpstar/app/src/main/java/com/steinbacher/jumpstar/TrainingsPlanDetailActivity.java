package com.steinbacher.jumpstar;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.steinbacher.jumpstar.core.Equipment;
import com.steinbacher.jumpstar.core.TrainingsPlan;
import com.steinbacher.jumpstar.util.Factory;
import com.steinbacher.jumpstar.view.EquipmentView;
import com.steinbacher.jumpstar.view.ExercisesView;

import static com.steinbacher.jumpstar.Configuration.CURRENT_TRAININGSPLANS_ID_KEY;

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

        //image
        AppCompatImageView imgView = findViewById(R.id.image);
        final String imageName = "trainingsplan_" + mTrainingsPlan.getId();
        final int resourceId = getResources().getIdentifier(imageName, "drawable", getPackageName());

        if(resourceId != 0) {
            Drawable d = getResources().getDrawable(resourceId);
            imgView.setImageDrawable(d);
        } else {
            Log.e(TAG, "image for trainingsplan: " + imageName + " not found");
            //TODO what should we do if the image is not found?
        }

        //Description
        TextView txtDescription = findViewById(R.id.detail_trainings_plan_description);
        txtDescription.setText(mTrainingsPlan.getDescription());

        //Equipment
        EquipmentView equipmentViewHolder= findViewById(R.id.equipment_view);
        List<Equipment> equipmentList = mTrainingsPlan.getNeededEquipment();
        equipmentViewHolder.setEquipment(equipmentList);

        //exercises
        ExercisesView exercisesView = findViewById(R.id.detail_trainings_plan_exercises);
        exercisesView.setTrainingsplan(mTrainingsPlan);

        /*
        List<TrainingsPlanEntry> exercises = mTrainingsPlan.getEntries();
        String exercisesString = "";
        for (TrainingsPlanEntry exercise : exercises) {
            if(exercise.getClass().equals(TimeExercise.class) || exercise.getClass().equals(StandardExercise.class)) {
                exercisesString += ((Exercise)exercise).getName() + ", ";
            }
        }
        exercisesString = exercisesString.substring(0,exercisesString.length()-1);
        txtExercises.setText(exercisesString);
        */

        //duration
        TextView durationTextView = findViewById(R.id.detail_plan_estimated_time);
        int hours = (int) (mTrainingsPlan.getEstimatedDurationSeconds() / 3600);
        int minutes = (int) (mTrainingsPlan.getEstimatedDurationSeconds() % 3600) / 60;
        durationTextView.setText(getString(R.string.detail_plan_estimated_time, String.format("%02d:%02d", hours, minutes)));

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
