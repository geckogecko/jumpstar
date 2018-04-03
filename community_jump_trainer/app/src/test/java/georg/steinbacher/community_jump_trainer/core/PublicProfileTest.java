package georg.steinbacher.community_jump_trainer.core;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by stge on 03.04.18.
 */

public class PublicProfileTest {
    final String NICKNAME = "Georg12";

    PublicProfile mProfile;
    List<TrainingsPlan> mCreatedTrainingsplans;

    @Before
    public void init() {
        mCreatedTrainingsplans = new ArrayList<>();
        mProfile = new PublicProfile(NICKNAME, mCreatedTrainingsplans);
    }

    @Test
    public void constructorTest() {
        assertEquals(mCreatedTrainingsplans, mProfile.getCreatedTrainingsPlans());
        assertEquals(0, mProfile.getCreatedTrainingsPlansCount());
    }
}
