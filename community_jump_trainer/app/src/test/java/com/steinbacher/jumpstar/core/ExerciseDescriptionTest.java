package com.steinbacher.jumpstar.core;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by georg on 25.03.18.
 */

public class ExerciseDescriptionTest {

    private List<ExerciseStep> mSteps;

    @Before
    public void init() {
        final ExerciseStep step0 = new ExerciseStep(0, "Step 1");
        mSteps = new ArrayList<>();
        mSteps.add(step0);
    }


    @Test
    public void constructorTest() throws ExerciseDescription.MissingExerciseStepException {
        final ExerciseDescription exerciseDescription = new ExerciseDescription(mSteps);

        assertEquals(mSteps, exerciseDescription.getSteps());
    }

    @Test
    public void exceptionTest() {
        //case wrong oder
        final ExerciseStep step2 = new ExerciseStep(2, "Step 2");
        mSteps.add(step2);

        try {
            final ExerciseDescription exerciseDescription = new ExerciseDescription(mSteps);
            assertTrue(false);
        } catch (ExerciseDescription.MissingExerciseStepException e) {
            assertTrue(true);
        }


        //case not starting with index 0
        final ExerciseStep step1 = new ExerciseStep(6, "Step 6");

        List<ExerciseStep> steps = new ArrayList<>();
        steps.add(step1);

        try {
            final ExerciseDescription exerciseDescription2 = new ExerciseDescription(steps);
            assertTrue(false);
        } catch (ExerciseDescription.MissingExerciseStepException e) {
            assertTrue(true);
        }

    }

    @Test
    public void testOrderingOfSteps() throws ExerciseDescription.MissingExerciseStepException {
        final ExerciseStep step0 = new ExerciseStep(0, "Step 0");
        final ExerciseStep step1 = new ExerciseStep(1, "Step 1");
        final ExerciseStep step2 = new ExerciseStep(2, "Step 2");
        final ExerciseStep step3 = new ExerciseStep(3, "Step 3");
        List<ExerciseStep> steps = new ArrayList<>();
        steps.add(step2);
        steps.add(step0);
        steps.add(step1);
        steps.add(step3);

        final ExerciseDescription exerciseDescription = new ExerciseDescription(steps);

        List<ExerciseStep> returnedSteps = exerciseDescription.getSteps();
        assertEquals(returnedSteps.get(0), step0);
        assertEquals(returnedSteps.get(1), step1);
        assertEquals(returnedSteps.get(2), step2);
        assertEquals(returnedSteps.get(3), step3);
    }
}
