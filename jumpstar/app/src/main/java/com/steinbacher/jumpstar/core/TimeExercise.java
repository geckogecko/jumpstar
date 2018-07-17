package com.steinbacher.jumpstar.core;

import java.util.List;

/**
 * Created by stge on 30.03.18.
 */

public class TimeExercise extends Exercise {
    private static final String TAG = "TimeExercise";

    private int mTime; //Seconds

    public TimeExercise(int id,
                        String name,
                        ExerciseDescription description,
                        List<Equipment> equipment,
                        Difficulty difficulty,
                        Rating rating,
                        TargetArea targetArea,
                        int sets,
                        Category category,
                        int time) {
        super(id, name, description, equipment, difficulty, rating, targetArea, sets, category);

        mTime = time;
    }

    @Override
    public Type getType() {
        return Type.TIME;
    }

    public int getTime() {
        return mTime;
    }
}
