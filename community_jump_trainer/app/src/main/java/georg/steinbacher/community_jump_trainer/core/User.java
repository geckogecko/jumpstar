package georg.steinbacher.community_jump_trainer.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by georg on 28.03.18.
 */

public class User {
    private static final String TAG = "User";

    private List<TrainingsPlan> mTrainingsPlanList;
    private TrainingsPlan mCurrentTrainingsPlan;
    private StaticStatistic mStaticStatistic;

    private IUserListener mListener;

    public interface IUserListener {
        void onCurrentTrainingsPlanChanged(TrainingsPlan currentTrainingsPlan);

        void onTrainingsPlanAdded(TrainingsPlan addedTrainingsPlan);
    }

    public User() {
        mTrainingsPlanList = new ArrayList<>();
    }

    public User(List<TrainingsPlan> trainingsPlanList,
                TrainingsPlan currentTrainingsPlan,
                StaticStatistic staticStatistic) {
        mTrainingsPlanList = trainingsPlanList;
        mCurrentTrainingsPlan = currentTrainingsPlan;
        mStaticStatistic = staticStatistic;
    }

    /**
     * @param trainingsPlan
     * @return false when the provided trainingsPlan is already in the list
     */
    public boolean addTrainingsPlan(TrainingsPlan trainingsPlan) {
        if (mTrainingsPlanList.contains(trainingsPlan)) {
            return false;
        }

        mTrainingsPlanList.add(trainingsPlan);

        if (mListener != null) {
            mListener.onTrainingsPlanAdded(trainingsPlan);
        }

        return true;
    }


    public List<TrainingsPlan> getTrainingsPlanList() {
        return mTrainingsPlanList;
    }

    public boolean hasCurrentTrainingsPlan() {
        return mCurrentTrainingsPlan != null;
    }

    public TrainingsPlan getCurrentTrainingsPlan() {
        return mCurrentTrainingsPlan;
    }

    public void setCurrentTrainingsPlan(TrainingsPlan currentTrainingsPlan) {
        //notify listener
        if (mListener != null && mCurrentTrainingsPlan != currentTrainingsPlan) {
            mListener.onCurrentTrainingsPlanChanged(currentTrainingsPlan);
        }

        mCurrentTrainingsPlan = currentTrainingsPlan;
    }

    public void setListener(IUserListener listener) {
        mListener = listener;
    }

    public IUserListener getListener() {
        return mListener;
    }

    public StaticStatistic getStaticStatistic() {
        return mStaticStatistic;
    }
}
