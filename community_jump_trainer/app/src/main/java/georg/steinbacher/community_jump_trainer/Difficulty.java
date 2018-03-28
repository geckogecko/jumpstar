package georg.steinbacher.community_jump_trainer;

/**
 * Created by georg on 28.03.18.
 */

class Difficulty {
    private static final String TAG = "Difficulty";

    static final int MAX = 10;
    static final int MIN = 0;

    static final int INTERMEDIATE_DIFFICULTY_LIMIT = 4; // <= BEGINNER
    static final int EXPERT_DIFFICULTY_LIMIT = 7;

    private double mValue;

    Difficulty(double value) {
        if(value > MAX) {
            mValue = MAX;
        } else if(value < MIN) {
            mValue = value;
        }

        mValue = value;
    }

    double getValue() {
        return mValue;
    }

    boolean isBeginner() {
        return mValue < INTERMEDIATE_DIFFICULTY_LIMIT;
    }

    boolean isIntermediate() {
        return mValue >= INTERMEDIATE_DIFFICULTY_LIMIT && mValue < EXPERT_DIFFICULTY_LIMIT;
    }

    boolean isExpert() {
        return mValue >= EXPERT_DIFFICULTY_LIMIT;
    }


}
