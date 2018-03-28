package georg.steinbacher.community_jump_trainer.core;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by georg on 28.03.18.
 */

public class UserTest {
    TrainingsPlan trainingsPlan;
    List<TrainingsPlan> trainingsPlanList;

    @Before
    public void init() throws ExerciseDescription.MissingExerciseStepException{
        final String testName = "TestPlan";
        List<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise("TestExercise",
                new ExerciseDescription(new ArrayList<ExerciseStep>()),
                new ArrayList<Equipment>(),
                new Difficulty(4),
                new Rating(7.0)));
        final long timeStamp = System.currentTimeMillis();
        final Rating rating = new Rating(5.0);

        trainingsPlan = new TrainingsPlan(testName, exercises, timeStamp, rating);
        trainingsPlanList = new ArrayList<>();
        trainingsPlanList.add(trainingsPlan);
    }

    @Test
    public void setGetTrainingsPlanTest(){
        // set/get trainingsplan test
        User user = new User();
        user.addTrainingsPlan(trainingsPlan);
        assertEquals(trainingsPlan, user.getTrainingsPlanList().get(0));

        // constructor / get trainingsplan test
        user = new User(trainingsPlanList, null);
        assertEquals(trainingsPlanList, user.getTrainingsPlanList());
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
}
