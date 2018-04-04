package georg.steinbacher.community_jump_trainer.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import georg.steinbacher.community_jump_trainer.R;

/**
 * Created by georg on 04.04.18.
 */

public class CurrentTrainingsPlanView extends CardView {
    public CurrentTrainingsPlanView(Context context) {
        super(context);
        init(context);
    }

    public CurrentTrainingsPlanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.view_current_trainings_plan, this);
    }
}
