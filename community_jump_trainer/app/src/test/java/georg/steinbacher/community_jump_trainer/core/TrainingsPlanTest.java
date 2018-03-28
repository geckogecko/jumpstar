package georg.steinbacher.community_jump_trainer.core;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by georg on 28.03.18.
 */

public class TrainingsPlanTest {

    @Test
    public void constructorTest() throws ExerciseDescription.MissingExerciseStepException{
        final String testName = "TestPlan";
        List<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise("TestExercise",
                new ExerciseDescription(new ArrayList<ExerciseStep>()),
                new ArrayList<Equipment>(),
                new Difficulty(4),
                new Rating(7.0)));
        final long timeStamp = System.currentTimeMillis();
        final Rating rating = new Rating(5.0);

        final TrainingsPlan trainingsPlan = new TrainingsPlan(testName, exercises, timeStamp, rating);

        assertEquals(testName, trainingsPlan.getName());
        assertEquals(exercises, trainingsPlan.getExercises());
        assertEquals(timeStamp, trainingsPlan.getCreationDate());
        assertEquals(rating, trainingsPlan.getRating());
    }
}
