package georg.steinbacher.community_jump_trainer.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import georg.steinbacher.community_jump_trainer.R;
import georg.steinbacher.community_jump_trainer.TrainingsPlanDetailActivity;
import georg.steinbacher.community_jump_trainer.core.Equipment;
import georg.steinbacher.community_jump_trainer.core.TrainingsPlan;
import georg.steinbacher.community_jump_trainer.drawables.CategorySummaryDrawable;

import static georg.steinbacher.community_jump_trainer.TrainingsPlanDetailActivity.TRAININGS_PLAN_ID;


/**
 * Created by georg on 04.04.18.
 */

public class TrainingsPlanView extends CardView implements View.OnClickListener{
    private Context mContext;
    private TrainingsPlan mTrainingsPlan;
    private ProgressBar mCategorySummery;

    public TrainingsPlanView(Context context) {
        super(context);
        init(context);
    }

    public TrainingsPlanView(Context context, AttributeSet attrs) {
        super(context,attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        inflate(context, R.layout.view_trainings_plan, this);
        mCategorySummery = findViewById(R.id.categorySummery);
        setOnClickListener(this);
    }

    public void setTrainingsPlan(TrainingsPlan trainingsPlan) {
        mTrainingsPlan = trainingsPlan;
        setCategorySummary(trainingsPlan);
    }

    private void setCategorySummary(TrainingsPlan trainingsPlan) {
        CategorySummaryDrawable categorySummaryDrawable = new CategorySummaryDrawable(trainingsPlan, mContext);
        mCategorySummery.setProgressDrawable(categorySummaryDrawable);

        //set the background color of the card
        //TODO indicate that somehow different
        /*
        CardView card = findViewById(R.id.cardView);
        card.setCardBackgroundColor(categorySummaryDrawable.getIndicatorPaint().getColor());
        */
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), TrainingsPlanDetailActivity.class);
        intent.putExtra(TRAININGS_PLAN_ID, mTrainingsPlan.getId());
        mContext.startActivity(intent);
    }
}
