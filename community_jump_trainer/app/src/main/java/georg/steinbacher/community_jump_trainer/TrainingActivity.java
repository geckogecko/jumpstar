package georg.steinbacher.community_jump_trainer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import georg.steinbacher.community_jump_trainer.core.Exercise;
import georg.steinbacher.community_jump_trainer.core.Rating;
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
        exercises.add(new Exercise("exercise1", null,
                null, null, null, null, 0, null));
        exercises.add(new Exercise("exercise2", null,
                null, null, null, null, 0, null));
        exercises.add(new Exercise("exercise3", null,
                null, null, null, null, 0, null));
        mTraingsPlan = new TrainingsPlan("test", exercises, 0, new Rating(0));
        mTraingsPlan.setListener(this);

        ExerciseFragment exerciseFragment = new ExerciseFragment();
        exerciseFragment.setExercise(mTraingsPlan.getCurrentExercise());
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, exerciseFragment)
                .commit();
    }

    @Override
    public void onCurrentExerciseCompleted(Exercise currentCompletedExercise) {
        ExerciseFragment exerciseFragment = new ExerciseFragment();
        exerciseFragment.setExercise(mTraingsPlan.getCurrentExercise());
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, exerciseFragment)
                .commit();
    }

    @Override
    public void onTrainingsPlanCompleted(TrainingsPlan completedTrainingsPlan) {

    }
}
