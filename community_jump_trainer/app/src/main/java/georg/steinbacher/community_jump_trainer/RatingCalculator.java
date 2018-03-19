package georg.steinbacher.community_jump_trainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by georg on 19.03.18.
 */

public class RatingCalculator {
    private static final String TAG = "RatingCalculator";

    private List<Rating> mRatings;

    public RatingCalculator() {
        mRatings = new ArrayList<>();
    }

    public RatingCalculator(List<Rating> ratings) {
        mRatings = ratings;
    }

    public void addRating(Rating rating) {
        mRatings.add(rating);
    }

    public float getAverageRating() {
        int ratingSum = 0;

        for (int i = 0; i < mRatings.size(); i++) {
            ratingSum += mRatings.get(i).getRatingValue();
        }

        return ratingSum / mRatings.size();
    }
}
