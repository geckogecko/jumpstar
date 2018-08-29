package com.steinbacher.jumpstar.core;

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
    private long mEstimatedDurationSeconds;

    private ITrainingsPlanListener mTrainingsPlanListener;
    public interface ITrainingsPlanListener {
        void onCurrentExerciseCompleted(Exercise currentCompletedExercise);
    }

    public TrainingsPlan(int id, String name, String description, List<TrainingsPlanEntry> entries, long creationDate,
                         Rating rating, long estimatedDurationSeconds) {
        mId = id;
        mName = name;
        mDescription = description;
        mEntries = entries;
        mCreationDate = creationDate;
        mRating = rating;
        mEstimatedDurationSeconds = estimatedDurationSeconds;

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

    public List<Equipment> getNeededEquipment() {
        List<Equipment> needed = new ArrayList<>();
        return findNeededEquipment(needed, this);
    }

    private List<Equipment> findNeededEquipment(List<Equipment> equipment, TrainingsPlan trainingsPlan) {
        for (TrainingsPlanEntry entry : trainingsPlan.getEntries()) {
            if(entry.getClass().equals(StandardExercise.class) ||
                    entry.getClass().equals(TimeExercise.class)) {
                Exercise exercise = (Exercise) entry;
                for (Equipment equ : exercise.getNeededEquipment()) {

                    //already included ?
                    boolean included = false;
                    for(Equipment addedEquip : equipment) {
                        if(addedEquip.getName().equals(equ.getName())){
                            included = true;
                        }
                    }

                    if (!included) {
                        equipment.add(equ);
                    }
                }
            } else {
                equipment = findNeededEquipment(equipment, (TrainingsPlan) entry);
            }
        }
        return equipment;
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

        return false;
    }

    public long getEstimatedDurationSeconds() {
        return mEstimatedDurationSeconds;
    }

    public void setEstimatedDurationSeconds(long estimatedDurationSeconds) {
        mEstimatedDurationSeconds = estimatedDurationSeconds;
    }
}
