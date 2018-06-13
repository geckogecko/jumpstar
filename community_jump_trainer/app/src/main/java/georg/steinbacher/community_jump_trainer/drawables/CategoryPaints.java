package georg.steinbacher.community_jump_trainer.drawables;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

import georg.steinbacher.community_jump_trainer.R;
import georg.steinbacher.community_jump_trainer.core.Exercise;

/**
 * Created by Georg Steinbacher on 01.05.18.
 */

public class CategoryPaints {
    public static Paint getPrimaryColor(Context context, Exercise.Category category) {
        Paint paint = new Paint();

        if(category == Exercise.Category.STRETCH) {
            paint.setColor(context.getResources().getColor(R.color.colorExerciseStretch));
        } else if(category == Exercise.Category.STRENGTH) {
            paint.setColor(context.getResources().getColor(R.color.colorExerciseStrength));
        } else if(category == Exercise.Category.PLYOMETRIC) {
            paint.setColor(context.getResources().getColor(R.color.colorExercisePlyometric));
        } else if(category == Exercise.Category.WARMUP) {
            paint.setColor(context.getResources().getColor(R.color.colorExerciseWarmup));
        }

        return paint;
    }

    public static Paint getSecondaryColor(Context context, Exercise.Category category) {
        Paint paint = new Paint();

        if(category == Exercise.Category.STRETCH) {
            paint.setColor(context.getResources().getColor(R.color.colorExerciseStretchLight));
        } else if(category == Exercise.Category.STRENGTH) {
            paint.setColor(context.getResources().getColor(R.color.colorExerciseStrengthLight));
        } else if(category == Exercise.Category.PLYOMETRIC) {
            paint.setColor(context.getResources().getColor(R.color.colorExercisePlyometricLight));
        } else if(category == Exercise.Category.WARMUP) {
            paint.setColor(context.getResources().getColor(R.color.colorExerciseWarmupLight));
        }

        return paint;
    }
}
