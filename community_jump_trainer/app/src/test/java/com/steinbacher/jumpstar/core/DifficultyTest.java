package com.steinbacher.jumpstar.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by georg on 28.03.18.
 */

public class DifficultyTest {

    private final double BEGINNER_VALUE_LOWEST_LIMIT = Difficulty.MIN;
    private final double BEGINNER_VALUE = 2.5;
    private final double BEGINNER_VALUE_HIGHEST_LIMIT = Difficulty.INTERMEDIATE_DIFFICULTY_LIMIT - 0.1;

    private final double INTERMEDIATE_VALUE_LOWEST_LIMIT = Difficulty.INTERMEDIATE_DIFFICULTY_LIMIT;
    private final double INTERMEDIATE_VALUE = 5.5;
    private final double INTERMEDIATE_VALUE_HIGHEST_LIMIT = Difficulty.EXPERT_DIFFICULTY_LIMIT - 0.1;

    private final double EXPERT_VALUE_LOWEST_LIMIT = Difficulty.EXPERT_DIFFICULTY_LIMIT;
    private final double EXPERT_VALUE = 8.5;
    private final double EXPERT_VALUE_HIGHEST_LIMIT = Difficulty.MAX;

    private Difficulty difficulty;

    @Test
    public void constructorTest() {
        difficulty = new Difficulty(BEGINNER_VALUE);
        assertEquals(BEGINNER_VALUE, difficulty.getValue(), 0.001);

        difficulty = new Difficulty(Difficulty.MIN - 1);
        assertEquals(Difficulty.MIN, difficulty.getValue(), 0.001);

        difficulty = new Difficulty(Difficulty.MAX + 1);
        assertEquals(Difficulty.MAX, difficulty.getValue(), 0.001);
    }


    @Test
    public void beginnerTest() {
        difficulty = new Difficulty(BEGINNER_VALUE_LOWEST_LIMIT);
        assertEquals(true, difficulty.isBeginner());
        assertEquals(false, difficulty.isIntermediate());
        assertEquals(false, difficulty.isExpert());

        difficulty = new Difficulty(BEGINNER_VALUE);
        assertEquals(true, difficulty.isBeginner());
        assertEquals(false, difficulty.isIntermediate());
        assertEquals(false, difficulty.isExpert());

        difficulty = new Difficulty(BEGINNER_VALUE_HIGHEST_LIMIT);
        assertEquals(true, difficulty.isBeginner());
        assertEquals(false, difficulty.isIntermediate());
        assertEquals(false, difficulty.isExpert());
    }

    @Test
    public void intermediateTest() {
        difficulty = new Difficulty(INTERMEDIATE_VALUE_LOWEST_LIMIT);
        assertEquals(false, difficulty.isBeginner());
        assertEquals(true, difficulty.isIntermediate());
        assertEquals(false, difficulty.isExpert());

        difficulty = new Difficulty(INTERMEDIATE_VALUE);
        assertEquals(false, difficulty.isBeginner());
        assertEquals(true, difficulty.isIntermediate());
        assertEquals(false, difficulty.isExpert());

        difficulty = new Difficulty(INTERMEDIATE_VALUE_HIGHEST_LIMIT);
        assertEquals(false, difficulty.isBeginner());
        assertEquals(true, difficulty.isIntermediate());
        assertEquals(false, difficulty.isExpert());
    }

    @Test
    public void expertTest() {
        difficulty = new Difficulty(EXPERT_VALUE_LOWEST_LIMIT);
        assertEquals(false, difficulty.isBeginner());
        assertEquals(false, difficulty.isIntermediate());
        assertEquals(true, difficulty.isExpert());

        difficulty = new Difficulty(EXPERT_VALUE);
        assertEquals(false, difficulty.isBeginner());
        assertEquals(false, difficulty.isIntermediate());
        assertEquals(true, difficulty.isExpert());

        difficulty = new Difficulty(EXPERT_VALUE_HIGHEST_LIMIT);
        assertEquals(false, difficulty.isBeginner());
        assertEquals(false, difficulty.isIntermediate());
        assertEquals(true, difficulty.isExpert());
    }
}
