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

    public User() {
        mTrainingsPlanList = new ArrayList<>();
    }

    public User(List<TrainingsPlan> trainingsPlanList, TrainingsPlan currentTrainingsPlan) {
        mTrainingsPlanList = trainingsPlanList;
        mCurrentTrainingsPlan = currentTrainingsPlan;
    }

    public void addTrainingsPlan(TrainingsPlan trainingsPlan) {
        mTrainingsPlanList.add(trainingsPlan);
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
        mCurrentTrainingsPlan = currentTrainingsPlan;
    }
}
