package georg.steinbacher.community_jump_trainer;

import android.content.ComponentName;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

/**
 * Created by stge on 03.04.18.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    Context mContext;

    @Before
    public void init() {
        mContext = getTargetContext();
    }

    @Test
    public void SetupActivityShownTest() {
        //TODO check if the setup activity is shown
        //intended(hasComponent(new ComponentName(mContext, SetupActivity.class)));
    }
}
