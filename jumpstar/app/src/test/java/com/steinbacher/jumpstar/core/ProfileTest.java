package com.steinbacher.jumpstar.core;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by stge on 03.04.18.
 */

public class ProfileTest {
    final String NICKNAME = "Georg12";

    Profile mProfile;

    @Before
    public void init() {
        mProfile = new Profile(NICKNAME);
    }

    @Test
    public void constructorTest() {
        assertEquals(NICKNAME, mProfile.getNickname());
    }
}
