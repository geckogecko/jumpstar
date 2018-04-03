package georg.steinbacher.community_jump_trainer.util;

/**
 * Created by stge on 03.04.18.
 */

public class CmInchesConverter {
    private static final double CONVERSATION_VALUE = 0.39370;

    public static double getInches(double cm) {
        return cm * CONVERSATION_VALUE;
    }

    public static double getCm(double inches) {
        return inches / CONVERSATION_VALUE;
    }
}
