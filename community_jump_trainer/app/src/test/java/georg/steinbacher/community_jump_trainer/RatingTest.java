package georg.steinbacher.community_jump_trainer;

import junit.framework.Assert;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by georg on 19.03.18.
 */

public class RatingTest {
    @Test
    public void ratingOutOfBoundsTest() {
        try {
            final Rating rating = new Rating(Rating.MIN_RATING -1);
            Assert.fail("Should have thrown Rating.RatingOutOfBoundsException");
        } catch (Rating.RatingOutOfBoundsException e) {}

        try {
            final Rating rating = new Rating(Rating.MAX_RATING + 1);
            Assert.fail("Should have thrown Rating.RatingOutOfBoundsException");
        } catch (Rating.RatingOutOfBoundsException e) {}
    }

    @Test
    public void setGetRatingTest() {
        try {
            Rating rating = new Rating(Rating.MIN_RATING);
            assertEquals(rating.getRatingValue(), Rating.MIN_RATING);

            rating = new Rating(Rating.MAX_RATING);
            assertEquals(rating.getRatingValue(), Rating.MAX_RATING);

            final int testValue = 5;
            rating = new Rating(testValue);
            assertEquals(rating.getRatingValue(), testValue);
        } catch (Rating.RatingOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
