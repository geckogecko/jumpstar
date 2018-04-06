package georg.steinbacher.community_jump_trainer;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Locale;

import georg.steinbacher.community_jump_trainer.util.SharedPreferencesManager;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ConfigurationTest {

    private Context mContext;

    @Before
    public void init() {
        mContext = InstrumentationRegistry.getTargetContext();

        //clear shared prefs
        SharedPreferencesManager.clear(mContext);
    }

    @Test
    public void booleanTest() {
        final boolean testValue = true;
        final String booleanKey = "double";

        assertEquals(false, Configuration.isSet(mContext, booleanKey));
        Configuration.set(mContext, booleanKey, testValue);
        assertEquals(true, Configuration.isSet(mContext, booleanKey));
        assertEquals(testValue, Configuration.getBoolean(mContext, booleanKey));
    }

    @Test
    public void intTest() {
        final int testValue = 1;
        final String intKey = "int";

        assertEquals(false, Configuration.isSet(mContext, intKey));
        Configuration.set(mContext, intKey, testValue);
        assertEquals(true, Configuration.isSet(mContext, intKey));
        assertEquals(testValue, Configuration.getInt(mContext, intKey));
    }

    @Test
    public void doubleTest() {
        final double testValue = 1.1234;
        final String doubleKey = "int";

        assertEquals(false, Configuration.isSet(mContext, doubleKey));
        Configuration.set(mContext, doubleKey, testValue);
        assertEquals(true, Configuration.isSet(mContext, doubleKey));
        assertEquals(testValue, Configuration.getDouble(mContext, doubleKey), 0.0001);
    }

    @Test
    public void unitLocalTest() {
        final Locale metricLocal = Locale.GERMANY;
        final Locale imperialLocal = Locale.US;

        assertEquals(Configuration.UnitLocal.METRIC, Configuration.getUnitLocal(mContext));
        Configuration.setUnitLocal(mContext, metricLocal);
        assertEquals(Configuration.UnitLocal.METRIC, Configuration.getUnitLocal(mContext));

        Configuration.setUnitLocal(mContext, imperialLocal);
        assertEquals(Configuration.UnitLocal.IMPERIAL, Configuration.getUnitLocal(mContext));
    }
}
