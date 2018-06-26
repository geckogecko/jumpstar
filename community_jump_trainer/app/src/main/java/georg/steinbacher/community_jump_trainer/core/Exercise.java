package georg.steinbacher.community_jump_trainer.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by georg on 15.03.18.
 */

public class Exercise extends TrainingsPlanEntry{
    private int mId;
    private String mName;
    private ExerciseDescription mDescription;
    private List<Equipment> mEquipmentList;
    private Difficulty mDifficulty;
    private Rating mRating;
    private TargetArea mTargetArea;
    private int mSets;
    private Category mCategory;

    public enum Type {
        UNKNOWN,
        STANDARD,
        TIME
    }

    public enum TargetArea {
        FULL_BODY,
        LEGS,
        ARMS,
        CORE,
        UPPER_BODY
    }

    //TODO which categories should we use?
    public enum Category {
        WARMUP,
        STRETCH,
        STRENGTH,
        PLYOMETRIC
    }

    public Exercise(int id,
                    String name,
                    ExerciseDescription description,
                    List<Equipment> equipment,
                    Difficulty difficulty,
                    Rating rating,
                    TargetArea targetArea,
                    int sets,
                    Category category) {
        mId = id;
        mName = name;
        mDescription = description;
        mEquipmentList = equipment;
        mDifficulty = difficulty;
        mRating = rating;
        mTargetArea = targetArea;
        mSets = sets;
        mCategory = category;
    }

    public int getId()
    {
        return mId;
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
        return Type.UNKNOWN;
    }

    public TargetArea getTargetArea() {
        return mTargetArea;
    }

    public int getSets() {
        return mSets;
    }

    public Category getCategory() {
        return mCategory;
    }
}
