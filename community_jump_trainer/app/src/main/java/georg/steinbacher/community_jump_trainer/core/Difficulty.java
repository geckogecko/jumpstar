package georg.steinbacher.community_jump_trainer.core;

/**
 * Created by georg on 28.03.18.
 */

public class Difficulty {
    private static final String TAG = "Difficulty";

    public static final int MAX = 10;
    public static final int MIN = 0;

    public static final int INTERMEDIATE_DIFFICULTY_LIMIT = 4; // <= BEGINNER
    public static final int EXPERT_DIFFICULTY_LIMIT = 7;

    private double mValue;

    public Difficulty(double value) {
        if(value > MAX) {
            mValue = MAX;
        } else if(value < MIN) {
            mValue = value;
        }

        mValue = value;
    }

    public double getValue() {
        return mValue;
    }

    public boolean isBeginner() {
        return mValue < INTERMEDIATE_DIFFICULTY_LIMIT;
    }

    public boolean isIntermediate() {
        return mValue >= INTERMEDIATE_DIFFICULTY_LIMIT && mValue < EXPERT_DIFFICULTY_LIMIT;
    }

    public boolean isExpert() {
        return mValue >= EXPERT_DIFFICULTY_LIMIT;
    }


}
