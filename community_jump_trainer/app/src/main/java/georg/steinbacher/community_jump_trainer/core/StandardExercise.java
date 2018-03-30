package georg.steinbacher.community_jump_trainer.core;

import java.util.List;

/**
 * Created by stge on 30.03.18.
 */

public class StandardExercise extends Exercise {
    private static final String TAG = "StandardExercise";

    private int mRepetitions;

    StandardExercise(String name,
                     ExerciseDescription description,
                     List<Equipment> equipment,
                     Difficulty difficulty,
                     Rating rating,
                     TargetArea targetArea,
                     int sets,
                     int repetetions) {
        super(name, description, equipment, difficulty, rating, targetArea, sets);

        mRepetitions = repetetions;
    }

    @Override
    public Type getType() {
        return Type.STANDARD;
    }

    public int getRepetitions() {
        return mRepetitions;
    }
}
