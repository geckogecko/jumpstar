package com.steinbacher.jumpstar;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.steinbacher.jumpstar.util.SharedPreferencesManager;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SharedPreferencesManagerTest {
    private static final String INT_KEY = "int";
    private static final int INT_VALUE = 123;
    private static final int INT_VALUE_DEFAULT = -1;

    private static final String BOOLEAN_KEY = "boolean";
    private static final boolean BOOLEAN_VALUE = true;
    private static final boolean BOOLEAN_VALUE_DEFAULT = false;

    private static final String STRING_KEY = "string";
    private static final String STRING_VALUE = "test";
    private static final String STRING_VALUE_DEFAULT = "default";

    private Context mContext;

    @Before
    public void init() {
        mContext = InstrumentationRegistry.getTargetContext();

        //clear shared prefs
        SharedPreferencesManager.clear(mContext);
    }

    @Test
    public void intTest() {
        assertEquals(INT_VALUE_DEFAULT, SharedPreferencesManager.getInt(mContext, INT_KEY, INT_VALUE_DEFAULT));

        SharedPreferencesManager.writeInt(mContext, INT_KEY, INT_VALUE);
        assertEquals(INT_VALUE, SharedPreferencesManager.getInt(mContext, INT_KEY, INT_VALUE_DEFAULT));

        assertEquals(INT_VALUE_DEFAULT, SharedPreferencesManager.getInt(mContext, "wrong key", INT_VALUE_DEFAULT));
    }

    @Test
    public void booleanTest() {
        assertEquals(BOOLEAN_VALUE_DEFAULT,
                SharedPreferencesManager.getBoolean(mContext, BOOLEAN_KEY, BOOLEAN_VALUE_DEFAULT));

        SharedPreferencesManager.writeBoolean(mContext, BOOLEAN_KEY, BOOLEAN_VALUE);
        assertEquals(BOOLEAN_VALUE, SharedPreferencesManager.getBoolean(mContext, BOOLEAN_KEY, BOOLEAN_VALUE_DEFAULT));

        assertEquals(BOOLEAN_VALUE_DEFAULT, SharedPreferencesManager.getBoolean(mContext, "wrong key", BOOLEAN_VALUE_DEFAULT));
    }

    @Test
    public void stringTest() {
        assertEquals(STRING_VALUE_DEFAULT,
                SharedPreferencesManager.getString(mContext, STRING_KEY, STRING_VALUE_DEFAULT));

        SharedPreferencesManager.writeString(mContext, STRING_KEY, STRING_VALUE);
        assertEquals(STRING_VALUE, SharedPreferencesManager.getString(mContext, STRING_KEY, STRING_VALUE_DEFAULT));

        assertEquals(STRING_VALUE_DEFAULT, SharedPreferencesManager.getString(mContext, "wrong key", STRING_VALUE_DEFAULT));
    }

    @Test
    public void containsTest() {
        assertEquals(false, SharedPreferencesManager.contains(mContext, BOOLEAN_KEY));
        SharedPreferencesManager.writeBoolean(mContext, BOOLEAN_KEY, BOOLEAN_VALUE);
        assertEquals(true, SharedPreferencesManager.contains(mContext, BOOLEAN_KEY));
    }
}
