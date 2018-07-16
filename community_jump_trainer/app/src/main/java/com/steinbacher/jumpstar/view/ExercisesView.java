package com.steinbacher.jumpstar.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.steinbacher.jumpstar.R;
import com.steinbacher.jumpstar.core.Exercise;
import com.steinbacher.jumpstar.core.TrainingsPlan;
import com.steinbacher.jumpstar.core.TrainingsPlanEntry;
import com.steinbacher.jumpstar.drawables.CategoryCounter;
import com.steinbacher.jumpstar.drawables.CategorySummaryDrawable;

import java.util.ArrayList;
import java.util.List;


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

        ProgressBar categorySummery = findViewById(R.id.categorySummery);
        CategorySummaryDrawable categorySummaryDrawable = new CategorySummaryDrawable(trainingsplan, mContext);
        categorySummery.setProgressDrawable(categorySummaryDrawable);

        List<CategoryCounter> categoryCounts = new ArrayList<>();
        //init the mCategoryCounts
        for (int i = 0; i < Exercise.Category.values().length; i++) {
            categoryCounts.add(new CategoryCounter(Exercise.Category.values()[i]));
        }

        for(TrainingsPlanEntry entry : mTrainingPlan.getEntries()) {
            Exercise exercise = (Exercise) entry;
            for (CategoryCounter categoryCount : categoryCounts) {
                if (exercise.getCategory().equals(categoryCount.getCategory())) {
                    categoryCount.increaseCount(exercise);
                }
            }
        }

        LinearLayoutCompat holder = mView.findViewById(R.id.holder);
        for (CategoryCounter categoryCount : categoryCounts) {
            if(categoryCount.getCount() > 0) {
                ExerciseLineView line = new ExerciseLineView(mContext);
                line.setCategory(categoryCount.getCategory());

                String exercisesString = "";
                for(Exercise exercise : categoryCount.getExercises()) {
                    exercisesString += exercise.getName() + ", ";
                }
                exercisesString = exercisesString.substring(0, exercisesString.length()-2);
                line.setExercises(exercisesString);
                holder.addView(line);
            }
        }
    }
}
