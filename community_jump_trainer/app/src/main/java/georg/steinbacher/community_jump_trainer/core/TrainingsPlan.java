package georg.steinbacher.community_jump_trainer.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by georg on 28.03.18.
 */

public class TrainingsPlan extends TrainingsPlanEntry implements TrainingsPlanEntry.ITrainingsPlanEntryListener {
    private static final String TAG = "TrainingsPlan";

    private int mId;
    private String mName;
    private List<Exercise> mExercises;
    private long mCreationDate;
    private Rating mRating;
    private Exercise mCurrentExercise;
    private boolean mCompletedLastExercise;
    private String mDescription;

    private ITrainingsPlanListener mTrainingsPlanListener;
    public interface ITrainingsPlanListener {
        void onCurrentExerciseCompleted(Exercise currentCompletedExercise);
    }

    public TrainingsPlan(int id, String name, String description, List<Exercise> exercises, long creationDate, Rating rating) {
        mId = id;
        mName = name;
        mDescription = description;
        mExercises = exercises;
        mCreationDate = creationDate;
        mRating = rating;

        if (mExercises.size() > 0) {
            mCurrentExercise = exercises.get(0);
            mCurrentExercise.setListener(this);
        }
    }

    @Override
    public void onEntryCompleted(TrainingsPlanEntry completedEntry) {
        int completedExerciseIndex = mExercises.indexOf(completedEntry);

        // is this the last exercise ?
        if (completedExerciseIndex != mExercises.size() - 1) {
            Exercise nextExercise = mExercises.get(completedExerciseIndex + 1);
            mCurrentExercise = nextExercise;
            mCurrentExercise.setListener(this);
            mCompletedLastExercise = false;
        } else {
            mCurrentExercise = null;
            mCompletedLastExercise = true;
            this.complete();
        }

        if(mTrainingsPlanListener != null) {
            mTrainingsPlanListener.onCurrentExerciseCompleted((Exercise) completedEntry);
        }
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
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

    public Exercise getCurrentExercise() {
        return mCurrentExercise;
    }

    public void setTrainingsPlanListener(ITrainingsPlanListener trainingsPlanListener) {
        mTrainingsPlanListener = trainingsPlanListener;
    }

    public boolean completedLastExercise() {
        return mCompletedLastExercise;
    }

    public int getExerciseCount() {
        return mExercises.size();
    }

    public int getCurrentExerciseIndex() {
        if(mCurrentExercise == null) {
            return -1;
        } else {
            return mExercises.indexOf(mCurrentExercise);
        }
    }

    public List<Equipment.Type> getNeededEquipmentTypes() {
        List<Equipment.Type> neededTypes = new ArrayList<>();

        for (Exercise exercise : mExercises) {
            for (Equipment equipment : exercise.getNeededEquipment()) {
                Equipment.Type type = equipment.getType();
                if(!neededTypes.contains(type)) {
                    neededTypes.add(type);
                }
            }
        }

        return neededTypes;
    }
}
