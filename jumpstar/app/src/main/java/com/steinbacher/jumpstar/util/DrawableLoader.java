package com.steinbacher.jumpstar.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.steinbacher.jumpstar.core.Exercise;
import com.steinbacher.jumpstar.core.ExerciseStep;
import com.steinbacher.jumpstar.core.TrainingsPlan;

import static android.content.ContentValues.TAG;

/**
 * Created by stge on 10.10.18.
 */

public class DrawableLoader {
    private static final String TAG = "DrawableLoader";

    public static void loadExerciseImage(Context context, Exercise exercise, int stepsNr, AppCompatImageView iamgeView) {
        ExerciseStep step = exercise.getDescription().getSteps().get(stepsNr);
        final String imageName = exercise.getType().name().toLowerCase() + "_" +
                exercise.getId() + "_" + step.getStepNr();
        final int resourceId = context.getResources().getIdentifier(imageName, "drawable",
                context.getPackageName());

        if(resourceId != 0) {
            Picasso.get().load(resourceId).into(iamgeView);
        } else {
            Log.e(TAG, "description image for step: " + imageName + " not found");
        }
    }

    public static void loadPlanImage(Context context, TrainingsPlan plan, AppCompatImageView iamgeView) {
        final String imageName = "trainingsplan_" + plan.getId();
        final int resourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

        if(resourceId != 0) {
            Picasso.get().load(resourceId).into(iamgeView);
        } else {
            Log.e(TAG, "image for trainingsplan: " + imageName + " not found");
        }
    }
}
