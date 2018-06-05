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
    private List<TrainingsPlanEntry> mEntries;
    private long mCreationDate;
    private Rating mRating;
    private TrainingsPlanEntry mCurrentEntry;
    private boolean mLastCompletedEntry;
    private String mDescription;

    private ITrainingsPlanListener mTrainingsPlanListener;
    public interface ITrainingsPlanListener {
        void onCurrentExerciseCompleted(Exercise currentCompletedExercise);
    }

    public TrainingsPlan(int id, String name, String description, List<TrainingsPlanEntry> entries, long creationDate, Rating rating) {
        mId = id;
        mName = name;
        mDescription = description;
        mEntries = entries;
        mCreationDate = creationDate;
        mRating = rating;

        if (mEntries.size() > 0) {
            mCurrentEntry = entries.get(0);
            mCurrentEntry.setListener(this);
        }
    }

    @Override
    public void onEntryCompleted(TrainingsPlanEntry completedEntry) {
        int completedIndex = mEntries.indexOf(completedEntry);

        // is this the last entry ?
        if (completedIndex != mEntries.size() - 1) {
            TrainingsPlanEntry nextExercise = mEntries.get(completedIndex + 1);
            mCurrentEntry = nextExercise;
            mCurrentEntry.setListener(this);
            mLastCompletedEntry = false;
        } else {
            mCurrentEntry = null;
            mLastCompletedEntry = true;
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

    public List<TrainingsPlanEntry> getEntries() {
        return mEntries;
    }

    public long getCreationDate() {
        return mCreationDate;
    }

    public Rating getRating() {
        return mRating;
    }

    public TrainingsPlanEntry getCurrentEntry() {
        return mCurrentEntry;
    }

    public void setTrainingsPlanListener(ITrainingsPlanListener trainingsPlanListener) {
        mTrainingsPlanListener = trainingsPlanListener;
    }

    public boolean getLastCompletedEntry() {
        return mLastCompletedEntry;
    }

    public int getEntryCount() {
        return mEntries.size();
    }

    public int getCurrentEntryIndex() {
        if(mCurrentEntry == null) {
            return -1;
        } else {
            return mEntries.indexOf(mCurrentEntry);
        }
    }

    public List<Equipment.Type> getNeededEquipmentTypes() {
        List<Equipment.Type> neededTypes = new ArrayList<>();

        for (TrainingsPlanEntry entry : mEntries) {
            //TODO if entry is a trainingsplan load the equipment of it
            if(entry.getClass().equals(StandardExercise.class) ||
                    entry.getClass().equals(TimeExercise.class)) {
                Exercise exercise = (Exercise) entry;
                for (Equipment equipment : exercise.getNeededEquipment()) {
                    Equipment.Type type = equipment.getType();
                    if (!neededTypes.contains(type)) {
                        neededTypes.add(type);
                    }
                }
            }
        }

        return neededTypes;
    }

    /**
     *
     * @return true if the trainingsplan consists of one or more other trainingsplans
     */
    public boolean hasTrainingsPlan() {
        for(TrainingsPlanEntry entry : mEntries) {
            if(entry.getClass().equals(TrainingsPlan.class)) {
                return true;
            }
        }
    }
}
