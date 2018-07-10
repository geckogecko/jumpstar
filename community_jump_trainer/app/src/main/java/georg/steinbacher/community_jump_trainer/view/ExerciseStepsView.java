package georg.steinbacher.community_jump_trainer.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.HashMap;

import georg.steinbacher.community_jump_trainer.R;
import georg.steinbacher.community_jump_trainer.core.Exercise;
import georg.steinbacher.community_jump_trainer.core.ExerciseStep;
import georg.steinbacher.community_jump_trainer.core.TrainingsPlan;

import static android.content.ContentValues.TAG;

public class ExerciseStepsView extends LinearLayoutCompat{
    private Exercise mExercise;
    private View mView;
    private Context mContext;

    public ExerciseStepsView(Context context) {
        super(context);
        init(context);
    }

    public ExerciseStepsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mView = inflate(mContext, R.layout.view_exercise_steps, this);
    }

    public void setTrainingsplan(Exercise exercise) {
        mExercise = exercise;

        AppCompatImageView imageView= mView.findViewById(R.id.image_view);

        Resources resources = getContext().getResources();

        ExerciseStep step = mExercise.getDescription().getSteps().get(0);
        final String imageName = mExercise.getType().name().toLowerCase() + "_" +
                mExercise.getId() + "_" +
                step.getStepNr();
        final int resourceId = resources.getIdentifier(imageName, "drawable",
                getContext().getPackageName());

        if(resourceId != 0) {
            imageView.setImageDrawable(ContextCompat.getDrawable(mContext, resourceId));
        } else {
            Log.e(TAG, "description image for step: " + imageName + " not found");
            //TODO what should we do if the image is not found?
        }

        AppCompatTextView textView = mView.findViewById(R.id.text_view);
        textView.setText(step.getDescription());

    }
}
