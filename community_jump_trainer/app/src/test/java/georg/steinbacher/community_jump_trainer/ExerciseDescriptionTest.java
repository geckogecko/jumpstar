package georg.steinbacher.community_jump_trainer;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by georg on 25.03.18.
 */

public class ExerciseDescriptionTest {

    private List<ExerciseStep> mSteps;
    private List<Equipment> mEquipment;

    @Before
    public void init() {
        final ExerciseStep step0 = new ExerciseStep(0, "Step 1");
        mSteps = new ArrayList<>();
        mSteps.add(step0);

        final Equipment equipment0 = new Equipment("TestEquipment0", Equipment.Type.GYM);
        mEquipment = new ArrayList<>();
        mEquipment.add(equipment0);
    }


    @Test
    public void constructorTest() throws ExerciseDescription.MissingExerciseStepException{
        final ExerciseDescription exerciseDescription = new ExerciseDescription(mSteps, mEquipment);

        assertEquals(mSteps, exerciseDescription.getSteps());
        assertEquals(mEquipment, exerciseDescription.getNeededEquipment());
    }

    @Test
    public void exceptionTest() {
        //case wrong oder
        final ExerciseStep step2 = new ExerciseStep(2, "Step 2");
        mSteps.add(step2);

        try {
            final ExerciseDescription exerciseDescription = new ExerciseDescription(mSteps, mEquipment);
            assertTrue(false);
        } catch (ExerciseDescription.MissingExerciseStepException e) {
            assertTrue(true);
        }


        //case not starting with index 0
        final ExerciseStep step1 = new ExerciseStep(1, "Step 1");

        List<ExerciseStep> steps = new ArrayList<>();
        steps.add(step1);

        try {
            final ExerciseDescription exerciseDescription2 = new ExerciseDescription(steps, mEquipment);
            assertTrue(false);
        } catch (ExerciseDescription.MissingExerciseStepException e) {
            assertTrue(true);
        }

    }

    @Test
    public void testOrderingOfSteps() throws ExerciseDescription.MissingExerciseStepException{
        final ExerciseStep step0 = new ExerciseStep(0, "Step 0");
        final ExerciseStep step1 = new ExerciseStep(1, "Step 1");
        final ExerciseStep step2 = new ExerciseStep(2, "Step 2");
        final ExerciseStep step3 = new ExerciseStep(3, "Step 3");
        List<ExerciseStep> steps = new ArrayList<>();
        steps.add(step2);
        steps.add(step0);
        steps.add(step1);
        steps.add(step3);

        final ExerciseDescription exerciseDescription = new ExerciseDescription(steps, mEquipment);

        List<ExerciseStep> returnedSteps = exerciseDescription.getSteps();
        assertEquals(returnedSteps.get(0), step0);
        assertEquals(returnedSteps.get(1), step1);
        assertEquals(returnedSteps.get(2), step2);
        assertEquals(returnedSteps.get(3), step3);
    }
}
