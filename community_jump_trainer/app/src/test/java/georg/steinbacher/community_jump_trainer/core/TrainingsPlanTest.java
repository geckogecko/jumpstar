package georg.steinbacher.community_jump_trainer.core;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by georg on 28.03.18.
 */

public class TrainingsPlanTest {

    final String testName = "TestPlan";
    List<Exercise> exercises;
    final long timeStamp = System.currentTimeMillis();
    final Rating rating = new Rating(5.0);

    @InjectMocks
    TrainingsPlan trainingsPlan;

    Exercise exercise1, exercise2, exercise3;

    @Before
    public void init() throws ExerciseDescription.MissingExerciseStepException {
        exercises = new ArrayList<>();
        exercise1 = new StandardExercise("TestExercise1",
                new ExerciseDescription(new ArrayList<ExerciseStep>()),
                new ArrayList<Equipment>(),
                new Difficulty(4),
                new Rating(7.0),
                Exercise.TargetArea.FULL_BODY,
                3,
                15);
        exercise2 = new TimeExercise("TestExercise2",
                new ExerciseDescription(new ArrayList<ExerciseStep>()),
                new ArrayList<Equipment>(),
                new Difficulty(7),
                new Rating(4.0),
                Exercise.TargetArea.CORE,
                3,
                120);
        exercise3 = new StandardExercise("TestExercise3",
                new ExerciseDescription(new ArrayList<ExerciseStep>()),
                new ArrayList<Equipment>(),
                new Difficulty(7),
                new Rating(4.0),
                Exercise.TargetArea.ARMS,
                3,
                10);
        exercises.add(exercise1);
        exercises.add(exercise2);
        exercises.add(exercise3);

        trainingsPlan = new TrainingsPlan(testName, exercises, timeStamp, rating);

        // listenerCalledTest
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void constructorTest() {
        assertEquals(testName, trainingsPlan.getName());
        assertEquals(exercises, trainingsPlan.getExercises());
        assertEquals(timeStamp, trainingsPlan.getCreationDate());
        assertEquals(rating, trainingsPlan.getRating());
    }

    @Test
    public void completeCurrentExerciseTest() {
        assertEquals(exercise1, trainingsPlan.getCurrentExercise());
        trainingsPlan.getCurrentExercise().complete();
        assertEquals(exercise2, trainingsPlan.getCurrentExercise());
        trainingsPlan.getCurrentExercise().complete();
        assertEquals(exercise3, trainingsPlan.getCurrentExercise());
        trainingsPlan.getCurrentExercise().complete();
        assertEquals(null, trainingsPlan.getCurrentExercise());
    }

    @Mock
    TrainingsPlan.ITrainingsPlanListener trainingsPlanListener;

    @Test
    public void trainingsPlanCompletedListenerTest() {
        trainingsPlan.getCurrentExercise().complete();
        trainingsPlan.getCurrentExercise().complete();
        trainingsPlan.getCurrentExercise().complete();
        verify(trainingsPlanListener, times(1)).onTrainingsPlanCompleted(trainingsPlan);
    }
}
