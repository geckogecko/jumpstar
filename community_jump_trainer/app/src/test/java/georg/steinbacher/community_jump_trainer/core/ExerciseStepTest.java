package georg.steinbacher.community_jump_trainer.core;

import georg.steinbacher.community_jump_trainer.core.ExerciseStep;

import static org.junit.Assert.*;

/**
 * Created by georg on 25.03.18.
 */

public class ExerciseStepTest {

    public void constructorTest() {
        final int stepNr = 0;
        final String description = "Test description";

        final ExerciseStep exerciseStep = new ExerciseStep(stepNr, description);

        assertEquals(stepNr, exerciseStep.getStepNr());
        assertEquals(description, exerciseStep.getDescription());
    }
}
