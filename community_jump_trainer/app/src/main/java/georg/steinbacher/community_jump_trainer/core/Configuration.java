package georg.steinbacher.community_jump_trainer.core;

import java.util.Locale;

/**
 * Created by stge on 03.04.18.
 */

public class Configuration {

    public static final String SETUP_COMPLETED_KEY = "setup_completed";
    public static final String REACHED_HEIGHT_KEY = "reached_height";

    public enum UnitLocal {
        METRIC,
        IMPERIAL
    }

    private UnitLocal mUnitLocal;
    private boolean mIsReachHeightSet;
    private double mReachHeight; //max reach when standing
    private boolean mSetupCompleted;

    private static final Configuration ourInstance = new Configuration();
    public static Configuration getInstance() {
        return ourInstance;
    }
    private Configuration() {
        mUnitLocal = UnitLocal.METRIC;
    }

    public void setUnitLocal(UnitLocal unitLocal) {
        mUnitLocal = unitLocal;
    }

    public void setUnitLocal(Locale locale) {
        String countryCode = locale.getCountry();
        switch (countryCode) {
            case "US":
            case "LR":
            case "MM":
                mUnitLocal = UnitLocal.IMPERIAL;
                break;
            default:
                mUnitLocal = UnitLocal.METRIC;
        }
    }

    public UnitLocal getUnitLocal() {
        return mUnitLocal;
    }

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

    public void setSetupCompleted(boolean setupCompleted) {
        mSetupCompleted = setupCompleted;
    }

    public boolean isSetupCompleted() {
        return mSetupCompleted;
    }

}
