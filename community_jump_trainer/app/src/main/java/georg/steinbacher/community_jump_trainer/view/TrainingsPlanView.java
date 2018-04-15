package georg.steinbacher.community_jump_trainer.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;

import georg.steinbacher.community_jump_trainer.R;
import georg.steinbacher.community_jump_trainer.TrainingsPlanDetailActivity;
import georg.steinbacher.community_jump_trainer.core.TrainingsPlan;

import static georg.steinbacher.community_jump_trainer.TrainingsPlanDetailActivity.TRAININGS_PLAN_ID;


/**
 * Created by georg on 04.04.18.
 */

public class TrainingsPlanView extends CardView implements View.OnClickListener{
    private Context mContext;
    private TrainingsPlan mTrainingsPlan;

    public TrainingsPlanView(Context context) {
        super(context);
        mContext = context;
        init(context);
    }

    public TrainingsPlanView(Context context, AttributeSet attrs) {
        super(context,attrs);
        mContext = context;
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.view_trainings_plan, this);
        setOnClickListener(this);
    }

    public void setTrainingsPlan(TrainingsPlan trainingsPlan) {
        mTrainingsPlan = trainingsPlan;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), TrainingsPlanDetailActivity.class);
        intent.putExtra(TRAININGS_PLAN_ID, mTrainingsPlan.getName());
        mContext.startActivity(intent);
    }
}
