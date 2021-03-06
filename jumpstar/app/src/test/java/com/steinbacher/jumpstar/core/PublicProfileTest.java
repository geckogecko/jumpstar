package com.steinbacher.jumpstar.core;

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
    PublicProfile mProfile1;
    List<TrainingsPlan> mCreatedTrainingsplans;

    @Before
    public void init() {
        mCreatedTrainingsplans = new ArrayList<>();
        mProfile = new PublicProfile(NICKNAME, mCreatedTrainingsplans);
        mProfile1 = new PublicProfile(NICKNAME);
    }

    @Test
    public void constructorTest() {
        assertEquals(mCreatedTrainingsplans, mProfile.getCreatedTrainingsPlans());
        assertEquals(0, mProfile.getCreatedTrainingsPlansCount());
        assertEquals(NICKNAME, mProfile1.getNickname());
    }
}
