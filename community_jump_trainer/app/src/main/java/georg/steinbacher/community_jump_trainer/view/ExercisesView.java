package georg.steinbacher.community_jump_trainer.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import georg.steinbacher.community_jump_trainer.R;
import georg.steinbacher.community_jump_trainer.core.Exercise;
import georg.steinbacher.community_jump_trainer.core.TrainingsPlan;
import georg.steinbacher.community_jump_trainer.core.TrainingsPlanEntry;
import georg.steinbacher.community_jump_trainer.drawables.CategorySummaryDrawable;


public class ExercisesView extends LinearLayoutCompat{
    private static final String TAG = "ExercisesView";

    Context mContext;
    View mView;
    TrainingsPlan mTrainingPlan;

    public ExercisesView(Context context) {
        super(context);
        init(context);
    }

    public ExercisesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    void init(Context context) {
        mContext = context;

        mView = inflate(mContext, R.layout.view_exercises, this);
    }

    public void setTrainingsplan(TrainingsPlan trainingsplan) {
        mTrainingPlan = trainingsplan;

        String warmupText = "";
        String stretchText = "";
        String strengthText = "";
        String plyomentricsText = "";
        for(TrainingsPlanEntry entry : mTrainingPlan.getEntries()) {
            Exercise exercise = (Exercise) entry;
            if(exercise.getCategory() == Exercise.Category.PLYOMETRIC) {
                if(!plyomentricsText.equals("")) {
                    plyomentricsText += ", ";
                }
                plyomentricsText += exercise.getName();
            } else if(exercise.getCategory() == Exercise.Category.WARMUP) {
                if(!warmupText.equals("")) {
                    warmupText += ", ";
                }
                warmupText += exercise.getName();
            } else if(exercise.getCategory() == Exercise.Category.STRETCH) {
                if(!stretchText.equals("")) {
                    stretchText += ", ";
                }
                stretchText += exercise.getName();
            } else if(exercise.getCategory() == Exercise.Category.STRENGTH) {
                if(!strengthText.equals("")) {
                    strengthText += ", ";
                }
                strengthText += exercise.getName();
            }
        }

        TextView warmup = findViewById(R.id.txt_warmup_exercises);
        warmup.setText(warmupText);

        TextView stretch = findViewById(R.id.txt_stretch_exercises);
        stretch.setText(stretchText);

        TextView strength = findViewById(R.id.txt_strength_exercises);
        strength.setText(strengthText);

        TextView plyomentrics = findViewById(R.id.txt_plyometric_exercises);
        plyomentrics.setText(plyomentricsText);
    }
}
