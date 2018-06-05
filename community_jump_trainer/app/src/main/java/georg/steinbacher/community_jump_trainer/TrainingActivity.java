package georg.steinbacher.community_jump_trainer;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import georg.steinbacher.community_jump_trainer.core.Exercise;
import georg.steinbacher.community_jump_trainer.core.StandardExercise;
import georg.steinbacher.community_jump_trainer.core.TimeExercise;
import georg.steinbacher.community_jump_trainer.core.TrainingsPlan;
import georg.steinbacher.community_jump_trainer.core.TrainingsPlanEntry;
import georg.steinbacher.community_jump_trainer.drawables.TrainingsPlanProgressDrawable;
import georg.steinbacher.community_jump_trainer.util.Factory;

public class TrainingActivity extends AppCompatActivity implements TrainingsPlan.ITrainingsPlanListener,
        TrainingsPlanEntry.ITrainingsPlanEntryListener {
    private static final String TAG = "TrainingActivity";

    public static final String TRAININGS_PLAN_ID = "trainings_plan_id";

    private TrainingsPlan mTraingsPlan;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_training);

        int trainingsPlanId = getIntent().getIntExtra(TRAININGS_PLAN_ID, -1);
        if(trainingsPlanId == -1) {
            Log.e(TAG, "onCreate: no traingsPlanId got passed");
            return;
        }

        mTraingsPlan = Factory.createTraingsPlan(trainingsPlanId);

        if(mTraingsPlan.hasTrainingsPlan()) {
            //TODO show the trainingsplans in an overview
        } else {
            //set progress
            mProgressBar = findViewById(R.id.progress_bar);
            mProgressBar.setMax(mTraingsPlan.getEntryCount());
            mProgressBar.setProgress(mTraingsPlan.getCurrentEntryIndex());
            mProgressBar.setProgressDrawable(new TrainingsPlanProgressDrawable(
                    mTraingsPlan, getApplicationContext()));

            //load the fragment for the current trainingsplan
            //TODO remove the case to Exercise
            loadExerciseFragment((Exercise) mTraingsPlan.getCurrentEntry());
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        mTraingsPlan.setListener(this);
        mTraingsPlan.setTrainingsPlanListener(this);

        findViewById(R.id.complete_exercise_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTraingsPlan.getCurrentEntry() != null) {
                    mTraingsPlan.getCurrentEntry().complete();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        mTraingsPlan.setListener(null);
        mTraingsPlan.setTrainingsPlanListener(null);
    }

    @Override
    public void onCurrentExerciseCompleted(Exercise currentCompletedExercise) {
        mProgressBar.setProgress(mTraingsPlan.getCurrentEntryIndex());

        if(!mTraingsPlan.getLastCompletedEntry()) {
            //TODO remove the case to Exercise
            loadExerciseFragment((Exercise) mTraingsPlan.getCurrentEntry());
        }
    }

    private void loadExerciseFragment(Exercise exercise) {
        FragmentTransaction fragmentTrans = getSupportFragmentManager().beginTransaction();
        fragmentTrans.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top);

        if(exercise.getType() == Exercise.Type.STANDARD) {
            StandardExerciseFragment sef = new StandardExerciseFragment();
            sef.setExercise((StandardExercise) exercise);
            fragmentTrans.replace(R.id.main_content, sef).commit();
        } else if(exercise.getType() == Exercise.Type.TIME) {
            TimeExerciseFragment tef = new TimeExerciseFragment();
            tef.setExercise((TimeExercise) exercise);
            fragmentTrans.replace(R.id.main_content, tef).commit();
        }
    }

    @Override
    public void onEntryCompleted(TrainingsPlanEntry completedEntry) {
        //TODO return something
        finish();
    }
}
