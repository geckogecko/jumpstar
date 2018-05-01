package georg.steinbacher.community_jump_trainer.drawables;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import georg.steinbacher.community_jump_trainer.core.Exercise;
import georg.steinbacher.community_jump_trainer.core.TrainingsPlan;

import static android.content.ContentValues.TAG;

/**
 * Created by Georg Steinbacher on 01.05.18.
 */

public class CategorySummaryDrawable extends Drawable {
    private int mSegmentsNumber = Exercise.Category.values().length;
    private Context mContext;
    private TrainingsPlan mTraingsPlan;
    private List<CategoryCounter> mCategoryCounts = new ArrayList<>();

    public CategorySummaryDrawable(TrainingsPlan trainingsPlan, Context context) {
        mTraingsPlan = trainingsPlan;
        mContext = context;

        //init the mCategoryCounts
        for (int i = 0; i < Exercise.Category.values().length; i++) {
            mCategoryCounts.add(new CategoryCounter(Exercise.Category.values()[i]));
        }

        for (Exercise exercise : mTraingsPlan.getExercises()) {
            for (CategoryCounter mCategoryCount : mCategoryCounts) {
                if(exercise.getCategory().equals(mCategoryCount.getCategory())) {
                    mCategoryCount.increaseCount();
                }
            }
        }
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        final Rect b = getBounds();
        final float gapWidth = b.height() / 3f;

        float leftOffset = 0;
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        for (int i = 0; i < mCategoryCounts.size(); i++) {
            if(mCategoryCounts.get(i).getCount() > 0) {
                final float segmentWidth = calculateSegmentWidth(mCategoryCounts.get(i),
                        b.width(),
                        gapWidth) - gapWidth;
                RectF segment = new RectF(0, 0, segmentWidth, b.height());

                paint = CategoryPaints.getPrimaryColor(mContext, mCategoryCounts.get(i).getCategory());

                segment.offset(leftOffset, 0);
                canvas.drawRoundRect(segment, 5, 5, paint);
                leftOffset += (segmentWidth + gapWidth);
            }
        }
    }

    /**
     * Returns the paint for the Category which got the  highest counter
     * returns the first if multiple cats have a equal amount.
     * @return
     */
    public Paint getIndicatorPaint() {
        int highest = 0;

        for (int i = 1; i < mCategoryCounts.size(); i++) {
            if(mCategoryCounts.get(i).getCount() > mCategoryCounts.get(highest).getCount()) {
                highest = i;
            }
        }

        return CategoryPaints.getSecondaryColor(mContext, mCategoryCounts.get(highest).getCategory());
    }

    private float calculateSegmentWidth(CategoryCounter categoryCounter, float width,  float gapWidth) {
        if(categoryCounter.getCount() == 0) {
            return 0;
        } else {
            final float percentage = categoryCounter.getCount() * 100 / mTraingsPlan.getExercises().size();
            return (percentage / 100) * width;
        }
    }

    @Override
    protected boolean onLevelChange(int level) {
        invalidateSelf();
        return super.onLevelChange(level);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    private class CategoryCounter {
        private Exercise.Category mCategory;
        private int mCount = 0;

        CategoryCounter(Exercise.Category category) {
            mCategory = category;
        }

        public void increaseCount() {
            mCount++;
        }

        public Exercise.Category getCategory() {
            return mCategory;
        }

        public int getCount() {
            return mCount;
        }
    }
}
