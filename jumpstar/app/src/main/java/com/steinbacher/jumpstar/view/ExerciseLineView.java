package com.steinbacher.jumpstar.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.View;

import com.steinbacher.jumpstar.R;
import com.steinbacher.jumpstar.core.Exercise;
import com.steinbacher.jumpstar.drawables.CategoryPaints;

/**
 * Created by stge on 16.07.18.
 */

public class ExerciseLineView extends LinearLayoutCompat{

    Context mContext;
    View mView;

    AppCompatTextView mCategory;
    AppCompatTextView mExercises;

    public ExerciseLineView(Context context) {
        super(context);
        init(context);
    }

    public ExerciseLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ExerciseLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mView = inflate(mContext, R.layout.view_exercise_line, this);
        mCategory = mView.findViewById(R.id.txt_category);
        mExercises = mView.findViewById(R.id.txt_exercises_names);
    }

    public void setCategory(Exercise.Category category) {
        mCategory.setTextColor(CategoryPaints.getPrimaryColor(mContext, category).getColor());

        String categoryName = category.name().toLowerCase();
        categoryName = categoryName.substring(0, 1).toUpperCase() + categoryName.substring(1);
        mCategory.setText(categoryName+ ":");
    }

    public void setExercises(String exercisesString) {
        mExercises.setText(exercisesString);
    }
}
