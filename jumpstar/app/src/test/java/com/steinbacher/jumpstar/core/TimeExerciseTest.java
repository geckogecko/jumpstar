package com.steinbacher.jumpstar.core;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by stge on 30.03.18.
 */

public class TimeExerciseTest {

    private TimeExercise mExercise;
    private final int time = 60; //1min

    @Before
    public void init() throws ExerciseDescription.MissingExerciseStepException {
        final int id = 1;
        final String name = "testExercise";
        final ExerciseDescription exerciseDescription = new ExerciseDescription(new ArrayList<ExerciseStep>());
        final Equipment equipment0 = new Equipment("TestEquipment0", Equipment.Type.GYM);
        List<Equipment> equipmentList = new ArrayList<>();
        equipmentList.add(equipment0);
        final Difficulty difficulty = new Difficulty(7.5);
        final Rating rating = new Rating(8.4);
        final Exercise.TargetArea targetArea = Exercise.TargetArea.FULL_BODY;
        final int sets = 3;
        final Exercise.Category category= Exercise.Category.STRENGTH;

        mExercise = new TimeExercise(id, name, exerciseDescription, equipmentList, difficulty, rating, targetArea, sets, category, time);
    }

    @Test
    public void getTypeTest() {
        assertEquals(Exercise.Type.TIME, mExercise.getType());
    }

    @Test
    public void getTimeTest() {
        assertEquals(time, mExercise.getTime());
    }
}
