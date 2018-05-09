package georg.steinbacher.community_jump_trainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import georg.steinbacher.community_jump_trainer.core.TrainingsPlan;
import georg.steinbacher.community_jump_trainer.util.Factory;

import static georg.steinbacher.community_jump_trainer.Configuration.CURRENT_TRAININGSPLANS_ID_KEY;

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

        TextView txtName = findViewById(R.id.detail_trainings_plan_name);
        txtName.setText(mTrainingsPlan.getName());

        AppCompatButton btnAddTrainingsPlan = findViewById(R.id.detail_button_add_trainings_plan);
        btnAddTrainingsPlan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int[] currentPlans = Configuration.getIntArray(getApplicationContext(), CURRENT_TRAININGSPLANS_ID_KEY);
        int[] newCurrentPlans = new int[currentPlans.length +1];
        for (int i = 0; i < currentPlans.length; i++) {
            newCurrentPlans[i] = currentPlans[i];
        }
        newCurrentPlans[newCurrentPlans.length - 1] = mTrainingsPlan.getId();
        Configuration.set(getApplicationContext(), CURRENT_TRAININGSPLANS_ID_KEY, newCurrentPlans);
        
        finish();
    }
}
