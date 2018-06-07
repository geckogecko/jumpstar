package georg.steinbacher.community_jump_trainer.view;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.HashMap;

import georg.steinbacher.community_jump_trainer.R;
import georg.steinbacher.community_jump_trainer.core.Exercise;
import georg.steinbacher.community_jump_trainer.core.ExerciseStep;
import georg.steinbacher.community_jump_trainer.core.TrainingsPlan;

import static android.content.ContentValues.TAG;

public class ExerciseStepsView extends SliderLayout{
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

        SliderLayout exerciseImages = mView.findViewById(R.id.slider_layout);
        HashMap<String,Integer> file_maps = new HashMap<>();
        Resources resources = getContext().getResources();
        for(ExerciseStep step : mExercise.getDescription().getSteps()) {
            final String imageName = mExercise.getType().name().toLowerCase() + "_" +
                    mExercise.getId() + "_" +
                    step.getStepNr();
            final int resourceId = resources.getIdentifier(imageName, "drawable",
                    getContext().getPackageName());

            if(resourceId != 0) {
                file_maps.put(imageName, resourceId);
            } else {
                Log.e(TAG, "description image for step: " + imageName + " not found");
                //TODO what should we do if the image is not found?
            }
        }

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getContext());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            exerciseImages.addSlider(textSliderView);
        }
        exerciseImages.setPresetTransformer(SliderLayout.Transformer.Default);
    }
}
