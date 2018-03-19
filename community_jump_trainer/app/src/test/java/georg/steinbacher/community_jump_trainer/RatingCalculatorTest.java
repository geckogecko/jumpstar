package georg.steinbacher.community_jump_trainer;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by georg on 19.03.18.
 */

public class RatingCalculatorTest {
    @Test
    public void ratingCalculatorConstructorTest2() {
        final Rating rating1 = new Rating(Rating.MIN_RATING);
        final Rating rating2 = new Rating(5);
        final Rating rating3 = new Rating(Rating.MAX_RATING);
        List<Rating> ratingList = new ArrayList<>();
        ratingList.add(rating1);
        ratingList.add(rating2);
        ratingList.add(rating3);

        RatingCalculator calculator = new RatingCalculator(ratingList);

        assertEquals(5.000, calculator.getAverageRating(), 0.001);
    }

    @Test
    public void calculateRatingTest() {
        final Rating rating1 = new Rating(Rating.MIN_RATING);
        final Rating rating2 = new Rating(5);
        final Rating rating3 = new Rating(Rating.MAX_RATING);

        RatingCalculator calculator = new RatingCalculator();
        calculator.addRating(rating1);
        calculator.addRating(rating2);
        calculator.addRating(rating3);

        assertEquals(5.000, calculator.getAverageRating(), 0.001);
    }
}
