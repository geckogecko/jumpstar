package georg.steinbacher.community_jump_trainer.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stge on 03.04.18.
 */

public class PublicProfile extends Profile {

    private List<TrainingsPlan> mCreatedTrainingsPlans;

    PublicProfile(String nickName) {
        super(nickName);

        mCreatedTrainingsPlans = new ArrayList<>();
    }

    PublicProfile(String nickName, List<TrainingsPlan> trainingsPlans) {
        super(nickName);

        mCreatedTrainingsPlans = trainingsPlans;
    }

    public int getCreatedTrainingsPlansCount() {
        return mCreatedTrainingsPlans.size();
    }

    public List<TrainingsPlan> getCreatedTrainingsPlans() {
        return mCreatedTrainingsPlans;
    }
}
