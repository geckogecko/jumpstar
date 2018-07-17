package com.steinbacher.jumpstar.core;

public class TrainingsPlanEntry {

    private boolean mCompleted;
    private ITrainingsPlanEntryListener mListener;

    public interface ITrainingsPlanEntryListener {
        void onEntryCompleted(TrainingsPlanEntry completedEntry);
    }

    public void setListener(ITrainingsPlanEntryListener entryListener) {
        mListener = entryListener;
    }

    /**
     * Call when the user completed the current entry.
     * By clicking "next" or "finish" for example
     */
    public void complete() {
        mCompleted = true;

        if (mListener != null) {
            mListener.onEntryCompleted(this);
        }
    }

    public boolean isCompleted() {
        return mCompleted;
    }
}
