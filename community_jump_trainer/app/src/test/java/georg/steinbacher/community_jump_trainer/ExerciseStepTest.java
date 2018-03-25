package georg.steinbacher.community_jump_trainer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
