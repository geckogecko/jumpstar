package georg.steinbacher.community_jump_trainer.core;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by stge on 03.04.18.
 */

public class StaticStatisticTest {
    private static final double MAX_REACH = 241;

    private StaticStatistic mStaticStatistic = new StaticStatistic();


    @Test
    public void reachHeightTest() {
        assertEquals(false, mStaticStatistic.isReachHeightSet());
        mStaticStatistic.setReachHeight(MAX_REACH);
        assertEquals(true, mStaticStatistic.isReachHeightSet());
        assertEquals(MAX_REACH, mStaticStatistic.getReachHeight(), 0.0001);
    }
}
