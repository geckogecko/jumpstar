package com.steinbacher.jumpstar.drawables;

import com.steinbacher.jumpstar.core.Exercise;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stge on 16.07.18.
 */

public class CategoryCounter {
    private Exercise.Category mCategory;
    private int mCount = 0;
    private List<Exercise> mExercises = new ArrayList<>();

    public CategoryCounter(Exercise.Category category) {
        mCategory = category;
    }

    public void increaseCount() {
        mCount++;
    }

    public void increaseCount(Exercise exercise) {
        mCount++;
        mExercises.add(exercise);
    }

    public Exercise.Category getCategory() {
        return mCategory;
    }

    public List<Exercise> getExercises() {
        return mExercises;
    }

    public int getCount() {
        return mCount;
    }
}