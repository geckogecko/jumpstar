package georg.steinbacher.community_jump_trainer.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by georg on 25.03.18.
 */

public class EquipmentTest {

    @Test
    public void constructorTest() {
        final String name = "name";
        final Equipment.Type type = Equipment.Type.GYM;

        final Equipment equipment = new Equipment(name, type);

        assertEquals(name, equipment.getName());
        assertEquals(type, equipment.getType());
    }
}
