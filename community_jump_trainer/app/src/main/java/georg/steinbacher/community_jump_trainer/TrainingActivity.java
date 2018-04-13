package georg.steinbacher.community_jump_trainer;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import georg.steinbacher.community_jump_trainer.core.Exercise;
import georg.steinbacher.community_jump_trainer.core.Rating;
import georg.steinbacher.community_jump_trainer.core.StandardExercise;
import georg.steinbacher.community_jump_trainer.core.TimeExercise;
import georg.steinbacher.community_jump_trainer.core.TrainingsPlan;

public class TrainingActivity extends AppCompatActivity implements TrainingsPlan.ITrainingsPlanListener {

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

        //TODO get the real trainingsPlan
        String trainingsPlanId = getIntent().getStringExtra(TRAININGS_PLAN_ID);
        List<Exercise> exercises = new ArrayList<>();
        exercises.add(new StandardExercise("exercise1", null,
                null, null, null, null, 0, null, 12));
        exercises.add(new TimeExercise("exercise2", null,
                null, null, null, null, 0, null, 90));
        exercises.add(new StandardExercise("exercise3", null,
                null, null, null, null, 0, null, 13));
        mTraingsPlan = new TrainingsPlan("test", exercises, 0, new Rating(0));

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
                mTraingsPlan.getCurrentExercise().complete();
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
            sef.setExercise(exercise);
            fragmentTrans.replace(R.id.main_content, sef).commit();
        } else if(exercise.getType() == Exercise.Type.TIME) {
            TimeExerciseFragment tef = new TimeExerciseFragment();
            tef.setExercise(exercise);
            fragmentTrans.replace(R.id.main_content, tef).commit();
        }
    }
}
