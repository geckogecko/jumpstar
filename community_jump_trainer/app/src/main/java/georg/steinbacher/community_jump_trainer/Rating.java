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


    public Rating(int rating){
        if(rating < MIN_RATING) {
            mRating = MIN_RATING;
        } else if(rating > MAX_RATING){
            mRating = MAX_RATING;
        } else {
            mRating = rating;
        }
    }

    public int getRatingValue() {
        return mRating;
    }
}
