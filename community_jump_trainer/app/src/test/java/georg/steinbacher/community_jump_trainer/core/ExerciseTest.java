package georg.steinbacher.community_jump_trainer.core;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by georg on 25.03.18.
 */

public class ExerciseTest {

    @Test
    public void constructorTest() throws ExerciseDescription.MissingExerciseStepException {
        final String name = "testExercise";
        final ExerciseDescription exerciseDescription = new ExerciseDescription(new ArrayList<ExerciseStep>());
        final Equipment equipment0 = new Equipment("TestEquipment0", Equipment.Type.GYM);
        List<Equipment> equipmentList = new ArrayList<>();
        equipmentList.add(equipment0);
        final Difficulty difficulty = new Difficulty(7.5);
        final Rating rating = new Rating(8.4);

        Exercise exercise = new Exercise(name, exerciseDescription, equipmentList, difficulty, rating);

        assertEquals(name, exercise.getName());
        assertEquals(exerciseDescription, exercise.getDescription());
        assertEquals(equipmentList, exercise.getNeededEquipment());
        assertEquals(difficulty, exercise.getDifficulty());
        assertEquals(rating, exercise.getRating());
    }
}
