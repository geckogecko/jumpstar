package georg.steinbacher.community_jump_trainer.core;

import java.util.List;

/**
 * Created by georg on 15.03.18.
 */

public class Exercise {
    private String mName;
    private ExerciseDescription mDescription; //TODO this should be dependent on the Type
    private List<Equipment> mEquipmentList;
    private Difficulty mDifficulty;
    private Rating mRating;
    private Type mType;

    public enum Type {
        STANDARD,
        TIME
    }

    Exercise(String name,
             ExerciseDescription description,
             List<Equipment> equipment,
             Difficulty difficulty,
             Rating rating,
             Type type) {
        mName = name;
        mDescription = description;
        mEquipmentList = equipment;
        mDifficulty = difficulty;
        mRating = rating;
        mType = type;
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

    public Type getType() {
        return mType;
    }
}
