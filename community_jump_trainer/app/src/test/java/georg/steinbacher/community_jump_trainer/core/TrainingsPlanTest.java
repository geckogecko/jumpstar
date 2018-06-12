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

    final int id = 1;
    final String testName = "TestPlan";
    final String testDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse diam enim, fermentum in finibus at, dictum interdum mauris. Suspendisse sed leo ut lorem dapibus semper id et leo. Suspendisse ligula neque, dictum sed sem a, auctor scelerisque magna.";
    List<TrainingsPlanEntry> exercises;
    final long timeStamp = System.currentTimeMillis();
    final Rating rating = new Rating(5.0);

    @InjectMocks
    TrainingsPlan trainingsPlan;

    Exercise exercise1, exercise2, exercise3;

    @Before
    public void init() throws ExerciseDescription.MissingExerciseStepException {
        exercises = new ArrayList<>();
        exercise1 = new StandardExercise(1,
                "TestExercise1",
                new ExerciseDescription(new ArrayList<ExerciseStep>()),
                new ArrayList<Equipment>(),
                new Difficulty(4),
                new Rating(7.0),
                Exercise.TargetArea.FULL_BODY,
                3,
                Exercise.Category.STRENGTH,
                new int[3]);
        exercise2 = new TimeExercise(2,
                "TestExercise2",
                new ExerciseDescription(new ArrayList<ExerciseStep>()),
                new ArrayList<Equipment>(),
                new Difficulty(7),
                new Rating(4.0),
                Exercise.TargetArea.CORE,
                3,
                Exercise.Category.STRENGTH,
                120);
        exercise3 = new StandardExercise(3,
                "TestExercise3",
                new ExerciseDescription(new ArrayList<ExerciseStep>()),
                new ArrayList<Equipment>(),
                new Difficulty(7),
                new Rating(4.0),
                Exercise.TargetArea.ARMS,
                3,
                Exercise.Category.STRENGTH,
                new int[3]);
        exercises.add(exercise1);
        exercises.add(exercise2);
        exercises.add(exercise3);

        trainingsPlan = new TrainingsPlan(id, testName, testDescription, exercises, timeStamp, rating);

        // listenerCalledTest
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void constructorTest() {
        assertEquals(testName, trainingsPlan.getName());
        assertEquals(testDescription, trainingsPlan.getDescription());
        assertEquals(exercises, trainingsPlan.getEntries());
        assertEquals(timeStamp, trainingsPlan.getCreationDate());
        assertEquals(rating, trainingsPlan.getRating());
        assertEquals(0, trainingsPlan.getCurrentEntryIndex());
        assertEquals(3, trainingsPlan.getEntryCount());
        assertEquals(id, trainingsPlan.getId());
    }

    @Test
    public void completeCurrentExerciseTest() {
        assertEquals(exercise1, trainingsPlan.getCurrentEntry());
        assertEquals(false, trainingsPlan.getLastCompletedEntry());
        assertEquals(0, trainingsPlan.getCurrentEntryIndex());

        trainingsPlan.getCurrentEntry().complete();
        assertEquals(exercise2, trainingsPlan.getCurrentEntry());
        assertEquals(false, trainingsPlan.getLastCompletedEntry());
        assertEquals(1, trainingsPlan.getCurrentEntryIndex());

        trainingsPlan.getCurrentEntry().complete();
        assertEquals(exercise3, trainingsPlan.getCurrentEntry());
        assertEquals(false, trainingsPlan.getLastCompletedEntry());
        assertEquals(2, trainingsPlan.getCurrentEntryIndex());

        trainingsPlan.getCurrentEntry().complete();
        assertEquals(null, trainingsPlan.getCurrentEntry());
        assertEquals(true, trainingsPlan.getLastCompletedEntry());
        assertEquals(-1, trainingsPlan.getCurrentEntryIndex());
    }

    @Mock
    TrainingsPlan.ITrainingsPlanListener trainingsPlanListener;

    @Mock
    TrainingsPlanEntry.ITrainingsPlanEntryListener listener;

    @Test
    public void listenerTest() {
        trainingsPlan.getCurrentEntry().complete();
        verify(trainingsPlanListener, times(1)).onCurrentExerciseCompleted(exercise1);
        trainingsPlan.getCurrentEntry().complete();
        verify(trainingsPlanListener, times(1)).onCurrentExerciseCompleted(exercise2);
        trainingsPlan.getCurrentEntry().complete();
        verify(trainingsPlanListener, times(1)).onCurrentExerciseCompleted(exercise3);
        verify(listener, times(1)).onEntryCompleted(trainingsPlan);
    }
}
