package georg.steinbacher.community_jump_trainer.core;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import georg.steinbacher.community_jump_trainer.core.Equipment;

import static org.junit.Assert.*;

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
