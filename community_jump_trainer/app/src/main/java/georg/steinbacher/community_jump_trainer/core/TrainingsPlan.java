package georg.steinbacher.community_jump_trainer.core;

import java.util.List;

/**
 * Created by georg on 28.03.18.
 */

public class TrainingsPlan implements Exercise.IExerciseListener {
    private static final String TAG = "TrainingsPlan";

    private int mId;
    private String mName;
    private List<Exercise> mExercises;
    private long mCreationDate;
    private Rating mRating;
    private Exercise mCurrentExercise;
    private boolean mCompletedLastExercise;

    private ITrainingsPlanListener mListener;

    public interface ITrainingsPlanListener {
        void onCurrentExerciseCompleted(Exercise currentCompletedExercise);
        void onTrainingsPlanCompleted(TrainingsPlan completedTrainingsPlan);
    }

    public TrainingsPlan(int id, String name, List<Exercise> exercises, long creationDate, Rating rating) {
        mId = id;
        mName = name;
        mExercises = exercises;
        mCreationDate = creationDate;
        mRating = rating;

        if (mExercises.size() > 0) {
            mCurrentExercise = exercises.get(0);
            mCurrentExercise.setListener(this);
        }
    }

    @Override
    public void onExerciseCompleted(Exercise completedExercise) {
        int completedExerciseIndex = mExercises.indexOf(completedExercise);

        // is this the last exercise ?
        if (completedExerciseIndex != mExercises.size() - 1) {
            Exercise nextExercise = mExercises.get(completedExerciseIndex + 1);
            mCurrentExercise = nextExercise;
            mCurrentExercise.setListener(this);
            mCompletedLastExercise = false;
        } else {
            mCurrentExercise = null;
            mCompletedLastExercise = true;

            if (mListener != null) {
                mListener.onTrainingsPlanCompleted(this);
            }
        }

        if(mListener != null) {
            mListener.onCurrentExerciseCompleted(completedExercise);
        }
    }

    public int getId() {
        return mId;
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

    public Exercise getCurrentExercise() {
        return mCurrentExercise;
    }

    public void setListener(ITrainingsPlanListener trainingsPlanListener) {
        mListener = trainingsPlanListener;
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
}
