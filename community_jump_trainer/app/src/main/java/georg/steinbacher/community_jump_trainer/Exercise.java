package georg.steinbacher.community_jump_trainer;

import java.util.List;

/**
 * Created by georg on 15.03.18.
 */

public class Exercise {
    private String mName;
    private ExerciseDescription mDescription;
    private Difficulty mDifficulty;

    Exercise(String name, ExerciseDescription description, Difficulty difficulty) {
        mName = name;
        mDescription = description;
        mDifficulty = difficulty;
    }

    public String getName() {
        return mName;
    }

    public ExerciseDescription getDescription() {
        return mDescription;
    }

    public Difficulty getDifficulty() {
        return mDifficulty;
    }
}
