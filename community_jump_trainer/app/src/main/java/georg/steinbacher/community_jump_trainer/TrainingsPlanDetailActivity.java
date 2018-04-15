package georg.steinbacher.community_jump_trainer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TrainingsPlanDetailActivity extends AppCompatActivity {
    public static final String TRAININGS_PLAN_ID = "trainings_plan_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainings_plan_detail);
    }
}
