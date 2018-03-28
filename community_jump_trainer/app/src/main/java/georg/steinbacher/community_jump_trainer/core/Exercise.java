package georg.steinbacher.community_jump_trainer.core;

import java.util.List;

/**
 * Created by georg on 15.03.18.
 */

public class Exercise {
    private String mName;
    private ExerciseDescription mDescription;
    private List<Equipment> mEquipmentList;
    private Difficulty mDifficulty;
    private Rating mRating;

    Exercise(String name,
             ExerciseDescription description,
             List<Equipment> equipment,
             Difficulty difficulty,
             Rating rating) {
        mName = name;
        mDescription = description;
        mEquipmentList = equipment;
        mDifficulty = difficulty;
        mRating = rating;
    }

    public String getName() {
        return mName;
    }

    public List<Equipment> getNeededEquipment() {
        return mEquipmentList;
    }

    public ExerciseDescription getDescription() {
        return mDescription;
    }

    public Difficulty getDifficulty() {
        return mDifficulty;
    }

    public Rating getRating() {
        return mRating;
    }
}
