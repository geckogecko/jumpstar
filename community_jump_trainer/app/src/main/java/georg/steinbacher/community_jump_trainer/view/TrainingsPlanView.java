package georg.steinbacher.community_jump_trainer.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import georg.steinbacher.community_jump_trainer.R;


/**
 * Created by georg on 04.04.18.
 */

public class TrainingsPlanView extends CardView {
    public TrainingsPlanView(Context context) {
        super(context);
        init(context);
    }

    public TrainingsPlanView(Context context, AttributeSet attrs) {
        super(context,attrs);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.trainings_plan_view, this);
    }
}
