package georg.steinbacher.community_jump_trainer.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by georg on 19.03.18.
 */

public class RatingTest {
    @Test
    public void ratingOutOfBoundsTest() {
        final Rating ratingMin = new Rating(Rating.MIN_RATING - 1);
        assertEquals(Rating.MIN_RATING, ratingMin.getRatingValue(), 0.001);

        final Rating ratingMax = new Rating(Rating.MAX_RATING + 1);
        assertEquals(Rating.MAX_RATING, ratingMax.getRatingValue(), 0.001);
    }

    @Test
    public void setGetRatingTest() {
        Rating rating = new Rating(Rating.MIN_RATING);
        assertEquals(Rating.MIN_RATING, rating.getRatingValue(), 0.001);

        rating = new Rating(Rating.MAX_RATING);
        assertEquals(Rating.MAX_RATING, rating.getRatingValue(), 0.001);

        final double testValue = 5.0;
        rating = new Rating(testValue);
        assertEquals(testValue, rating.getRatingValue(), 0.001);
    }
}
