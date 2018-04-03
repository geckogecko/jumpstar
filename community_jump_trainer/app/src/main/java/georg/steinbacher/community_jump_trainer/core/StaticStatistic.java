package georg.steinbacher.community_jump_trainer.core;

/**
 * Created by stge on 03.04.18.
 */

public class StaticStatistic {
    private boolean mIsReachHeightSet;
    private double mReachHeight; //max reach when standing

    public void setReachHeight(double reachHeight) {
        mReachHeight = reachHeight;
        mIsReachHeightSet = true;
    }

    public boolean isReachHeightSet() {
        return mIsReachHeightSet;
    }

    public double getReachHeight() {
        return mReachHeight;
    }

}
