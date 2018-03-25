package georg.steinbacher.community_jump_trainer;

import java.util.List;

/**
 * Created by georg on 15.03.18.
 */

public class Exercise {
    private String mName;
    private ExerciseDescription mDescription;

    Exercise(String name, ExerciseDescription description) {
        mName = name;
        mDescription = description;
    }

    public String getName() {
        return mName;
    }

    public ExerciseDescription getDescription() {
        return mDescription;
    }
}
