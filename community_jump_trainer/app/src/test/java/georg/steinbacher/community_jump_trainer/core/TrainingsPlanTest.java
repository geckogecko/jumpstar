package georg.steinbacher.community_jump_trainer.core;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
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
                Exercise.Category.STRENGTH,
                15);
        exercise2 = new TimeExercise("TestExercise2",
                new ExerciseDescription(new ArrayList<ExerciseStep>()),
                new ArrayList<Equipment>(),
                new Difficulty(7),
                new Rating(4.0),
                Exercise.TargetArea.CORE,
                3,
                Exercise.Category.STRENGTH,
                120);
        exercise3 = new StandardExercise("TestExercise3",
                new ExerciseDescription(new ArrayList<ExerciseStep>()),
                new ArrayList<Equipment>(),
                new Difficulty(7),
                new Rating(4.0),
                Exercise.TargetArea.ARMS,
                3,
                Exercise.Category.STRENGTH,
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
        assertEquals(0, trainingsPlan.getCurrentExerciseIndex());
        assertEquals(3, trainingsPlan.getExerciseCount());
    }

    @Test
    public void completeCurrentExerciseTest() {
        assertEquals(exercise1, trainingsPlan.getCurrentExercise());
        assertEquals(false, trainingsPlan.completedLastExercise());
        assertEquals(0, trainingsPlan.getCurrentExerciseIndex());

        trainingsPlan.getCurrentExercise().complete();
        assertEquals(exercise2, trainingsPlan.getCurrentExercise());
        assertEquals(false, trainingsPlan.completedLastExercise());
        assertEquals(1, trainingsPlan.getCurrentExerciseIndex());

        trainingsPlan.getCurrentExercise().complete();
        assertEquals(exercise3, trainingsPlan.getCurrentExercise());
        assertEquals(false, trainingsPlan.completedLastExercise());
        assertEquals(2, trainingsPlan.getCurrentExerciseIndex());

        trainingsPlan.getCurrentExercise().complete();
        assertEquals(null, trainingsPlan.getCurrentExercise());
        assertEquals(true, trainingsPlan.completedLastExercise());
        assertEquals(-1, trainingsPlan.getCurrentExerciseIndex());
    }

    @Mock
    TrainingsPlan.ITrainingsPlanListener trainingsPlanListener;

    @Test
    public void listenerTest() {
        trainingsPlan.getCurrentExercise().complete();
        verify(trainingsPlanListener, times(1)).onCurrentExerciseCompleted(exercise1);
        trainingsPlan.getCurrentExercise().complete();
        verify(trainingsPlanListener, times(1)).onCurrentExerciseCompleted(exercise2);
        trainingsPlan.getCurrentExercise().complete();
        verify(trainingsPlanListener, times(1)).onCurrentExerciseCompleted(exercise3);
        verify(trainingsPlanListener, times(1)).onTrainingsPlanCompleted(trainingsPlan);
    }
}
