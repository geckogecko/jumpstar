package georg.steinbacher.community_jump_trainer;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import georg.steinbacher.community_jump_trainer.core.Exercise;
import georg.steinbacher.community_jump_trainer.core.Rating;
import georg.steinbacher.community_jump_trainer.core.StandardExercise;
import georg.steinbacher.community_jump_trainer.core.TimeExercise;
import georg.steinbacher.community_jump_trainer.core.TrainingsPlan;
import georg.steinbacher.community_jump_trainer.util.Factory;
import georg.steinbacher.community_jump_trainer.util.JSONHolder;

public class TrainingActivity extends AppCompatActivity implements TrainingsPlan.ITrainingsPlanListener {
    private static final String TAG = "TrainingActivity";

    public static final String TRAININGS_PLAN_ID = "trainings_plan_id";

    private TrainingsPlan mTraingsPlan;
    private ProgressBar mProgressBar;
    private JSONHolder mJSONHolder = JSONHolder.getInstance();

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

        //set progress
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setMax(mTraingsPlan.getExerciseCount());
        mProgressBar.setProgress(mTraingsPlan.getCurrentExerciseIndex());

        //load the fragment for the current trainingsplan
        loadExerciseFragment(mTraingsPlan.getCurrentExercise());

    }

    @Override
    protected void onStart() {
        super.onStart();

        mTraingsPlan.setListener(this);

        findViewById(R.id.complete_exercise_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTraingsPlan.getCurrentExercise() != null) {
                    mTraingsPlan.getCurrentExercise().complete();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        mTraingsPlan.setListener(null);
    }

    @Override
    public void onCurrentExerciseCompleted(Exercise currentCompletedExercise) {
        if(!mTraingsPlan.completedLastExercise()) {
            loadExerciseFragment(mTraingsPlan.getCurrentExercise());
            mProgressBar.setProgress(mTraingsPlan.getCurrentExerciseIndex());
        }
    }

    @Override
    public void onTrainingsPlanCompleted(TrainingsPlan completedTrainingsPlan) {

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
}
