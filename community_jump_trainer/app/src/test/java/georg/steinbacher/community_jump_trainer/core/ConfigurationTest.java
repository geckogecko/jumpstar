package georg.steinbacher.community_jump_trainer.core;

import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertEquals;

/**
 * Created by stge on 03.04.18.
 */

public class ConfigurationTest {

    private Configuration mConfiguration = Configuration.getInstance();

    @Test
    public void unitLocalSystemTest() {
        assertEquals(Configuration.UnitLocal.METRIC, mConfiguration.getUnitLocal());

        mConfiguration.setUnitLocal(Configuration.UnitLocal.IMPERIAL);
        assertEquals(Configuration.UnitLocal.IMPERIAL, mConfiguration.getUnitLocal());

        final Locale localFrance = new Locale(Locale.FRANCE.getLanguage() , Locale.FRANCE.getCountry());
        mConfiguration.setUnitLocal(localFrance);
        assertEquals(Configuration.UnitLocal.METRIC, mConfiguration.getUnitLocal());

        final Locale localUS = new Locale(Locale.US.getLanguage(), Locale.US.getCountry());
        mConfiguration.setUnitLocal(localUS);
        assertEquals(Configuration.UnitLocal.IMPERIAL, mConfiguration.getUnitLocal());
    }

    private static final double MAX_REACH = 241;
    @Test
    public void reachHeightTest() {
        assertEquals(false, mConfiguration.isReachHeightSet());
        mConfiguration.setReachHeight(MAX_REACH);
        assertEquals(true, mConfiguration.isReachHeightSet());
        assertEquals(MAX_REACH, mConfiguration.getReachHeight(), 0.0001);
    }

    private static final boolean SETUP_COMPLETED = true;
    @Test
    public void setupCompletedTest() {
        assertEquals(false, mConfiguration.isSetupCompleted());
        mConfiguration.setSetupCompleted(SETUP_COMPLETED);
        assertEquals(SETUP_COMPLETED, mConfiguration.isSetupCompleted());
    }

}
