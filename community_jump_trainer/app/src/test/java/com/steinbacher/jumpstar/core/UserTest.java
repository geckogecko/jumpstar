package com.steinbacher.jumpstar.core;

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

public class UserTest {
    TrainingsPlan trainingsPlan;
    List<TrainingsPlan> trainingsPlanList;

    @Before
    public void init() throws ExerciseDescription.MissingExerciseStepException {
        final int testId = 1;
        final String testName = "TestPlan";
        final String testDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse diam enim, fermentum in finibus at, dictum interdum mauris. Suspendisse sed leo ut lorem dapibus semper id et leo. Suspendisse ligula neque, dictum sed sem a, auctor scelerisque magna.";
        List<TrainingsPlanEntry> exercises = new ArrayList<>();
        exercises.add(new Exercise(1,
                "TestExercise",
                new ExerciseDescription(new ArrayList<ExerciseStep>()),
                new ArrayList<Equipment>(),
                new Difficulty(4),
                new Rating(7.0),
                Exercise.TargetArea.FULL_BODY,
                3,
                Exercise.Category.STRENGTH));
        final long timeStamp = System.currentTimeMillis();
        final Rating rating = new Rating(5.0);

        trainingsPlan = new TrainingsPlan(testId, testName, testDescription, exercises, timeStamp, rating);
        trainingsPlanList = new ArrayList<>();
        trainingsPlanList.add(trainingsPlan);

        // listenerCalledTest
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void setGetTrainingsPlanTest() {
        // set/get trainingsplan test
        User user = new User();
        assertEquals(true, user.addTrainingsPlan(trainingsPlan));
        assertEquals(trainingsPlan, user.getTrainingsPlanList().get(0));

        // constructor / get trainingsplan test
        user = new User(trainingsPlanList, null);
        assertEquals(trainingsPlanList, user.getTrainingsPlanList());

        //set mutliple times the same trainingsplane
        assertEquals(false, user.addTrainingsPlan(trainingsPlan));
    }

    @Test
    public void setGetCurrentTrainingsPlanTest() {
        User user = new User();
        user.addTrainingsPlan(trainingsPlan);
        assertEquals(null, user.getCurrentTrainingsPlan());

        user.setCurrentTrainingsPlan(trainingsPlan);
        assertEquals(trainingsPlan, user.getCurrentTrainingsPlan());
    }

    @Test
    public void hasCurrentTrainingsPlanTest() {
        User user = new User();
        user.addTrainingsPlan(trainingsPlan);
        assertEquals(false, user.hasCurrentTrainingsPlan());

        user.setCurrentTrainingsPlan(trainingsPlan);
        assertEquals(true, user.hasCurrentTrainingsPlan());
    }

    // listenerCalledTest
    @Mock
    User.IUserListener userListener;

    @InjectMocks
    User eventMonitor = new User();

    @Test
    public void listenerCalledTest() {
        eventMonitor.setCurrentTrainingsPlan(trainingsPlan);
        verify(userListener, times(1)).onCurrentTrainingsPlanChanged(trainingsPlan);
        verify(userListener, times(0)).onTrainingsPlanAdded(null);

        //we add the trainingsPlan 2 times but the callback should only be called once since
        //the second time the plan is not added.
        eventMonitor.addTrainingsPlan(trainingsPlan);
        eventMonitor.addTrainingsPlan(trainingsPlan);
        verify(userListener, times(1)).onTrainingsPlanAdded(trainingsPlan);
    }

    @Test
    public void listenerSetGetTest() {
        User user = new User();
        assertEquals(null, user.getListener());

        user.setListener(userListener);
        assertEquals(userListener, user.getListener());
    }
}
