package georg.steinbacher.community_jump_trainer;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by georg on 25.03.18.
 */

public class ExerciseTest {

    @Test
    public void constructorTest() throws ExerciseDescription.MissingExerciseStepException {
        final String name = "testExercise";
        final ExerciseDescription exerciseDescription =
                new ExerciseDescription(new ArrayList<ExerciseStep>(), new ArrayList<Equipment>());
        Exercise exercise = new Exercise(name, exerciseDescription);

        assertEquals(name, exercise.getName());
        assertEquals(exerciseDescription, exercise.getDescription());
    }
}
