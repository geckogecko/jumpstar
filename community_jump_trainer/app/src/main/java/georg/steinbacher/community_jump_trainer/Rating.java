package georg.steinbacher.community_jump_trainer;

import android.util.Log;

/**
 * Created by georg on 19.03.18.
 */

public class Rating {
    private static final String TAG = "Rating";

    public static final int MAX_RATING = 10;
    public static final int MIN_RATING = 0;

    private int mRating;


    public Rating(int rating) throws RatingOutOfBoundsException{
        if(rating < MIN_RATING || rating > MAX_RATING) {
            throw new RatingOutOfBoundsException("Rating value outside possible values requested");
        } else {
            mRating = rating;
        }
    }

    public int getRatingValue() {
        return mRating;
    }

    public class RatingOutOfBoundsException extends Exception {
        public RatingOutOfBoundsException(String error) {
            super(error);
        }
    }
}
