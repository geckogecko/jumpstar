package georg.steinbacher.community_jump_trainer.core;

import java.util.List;

/**
 * Created by stge on 30.03.18.
 */

public class StandardExercise extends Exercise {
    private static final String TAG = "StandardExercise";

    private int mRepetitions;

    public StandardExercise(int id,
                            String name,
                            ExerciseDescription description,
                            List<Equipment> equipment,
                            Difficulty difficulty,
                            Rating rating,
                            TargetArea targetArea,
                            int sets,
                            Category category,
                            int repetetions) {
        super(id, name, description, equipment, difficulty, rating, targetArea, sets, category);

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
