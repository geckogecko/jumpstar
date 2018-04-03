package georg.steinbacher.community_jump_trainer.util;

import org.junit.Test;

import georg.steinbacher.community_jump_trainer.util.CmInchesConverter;

import static org.junit.Assert.assertEquals;

/**
 * Created by georg on 28.03.18.
 */

public class CmInchesConverterTest {

    @Test
    public void getInchesTest() {
        assertEquals(0, CmInchesConverter.getInches(0), 0.00001);
        assertEquals(0.3937, CmInchesConverter.getInches(1), 0.000001);
        assertEquals(-0.3937, CmInchesConverter.getInches(-1), 0.00001);
        assertEquals(19.685, CmInchesConverter.getInches(50), 0.00001);
    }

    @Test
    public void getCmTest() {
        assertEquals(0, CmInchesConverter.getCm(0), 0.00001);
        assertEquals(1, CmInchesConverter.getCm(0.3937), 0.000001);
        assertEquals(-1, CmInchesConverter.getCm(-0.3937), 0.00001);
        assertEquals(50, CmInchesConverter.getCm(19.685), 0.00001);
    }
}
