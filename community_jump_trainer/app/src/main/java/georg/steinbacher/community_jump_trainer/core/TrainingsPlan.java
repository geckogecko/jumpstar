package georg.steinbacher.community_jump_trainer.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by georg on 28.03.18.
 */

public class TrainingsPlan {
    private static final String TAG = "TrainingsPlan";

    private String mName;
    private List<Exercise> mExercises;
    private long mCreationDate;
    private Rating mRating;

    public TrainingsPlan(String name, List<Exercise> exercises, long creationDate, Rating rating) {
        mName = name;
        mExercises = exercises;
        mCreationDate = creationDate;
        mRating = rating;
    }

    public String getName() {
        return mName;
    }

    public List<Exercise> getExercises() {
        return mExercises;
    }

    public long getCreationDate() {
        return mCreationDate;
    }

    public Rating getRating() {
        return mRating;
    }
}
