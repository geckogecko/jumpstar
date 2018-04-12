package georg.steinbacher.community_jump_trainer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

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
        mTraingsPlan.setListener(this);

        loadExerciseFragment(mTraingsPlan.getCurrentExercise(),
                mTraingsPlan.getCurrentExerciseIndex(),
                mTraingsPlan.getExerciseCount());
    }

    @Override
    public void onCurrentExerciseCompleted(Exercise currentCompletedExercise) {
        if(!mTraingsPlan.completedLastExercise()) {
            loadExerciseFragment(mTraingsPlan.getCurrentExercise(),
                    mTraingsPlan.getCurrentExerciseIndex(),
                    mTraingsPlan.getExerciseCount());
        }
    }

    @Override
    public void onTrainingsPlanCompleted(TrainingsPlan completedTrainingsPlan) {

    }

    private void loadExerciseFragment(Exercise exercise, int progress, int maxProgress) {
        if(exercise.getType() == Exercise.Type.STANDARD) {
            StandardExerciseFragment sef = new StandardExerciseFragment();
            sef.setExercise(exercise);
            sef.setProgress(progress, maxProgress);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_content, sef).commit();
        } else if(exercise.getType() == Exercise.Type.TIME) {
            TimeExerciseFragment tef = new TimeExerciseFragment();
            tef.setExercise(exercise);
            tef.setProgress(progress, maxProgress);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_content, tef).commit();
        }
    }
}
