package georg.steinbacher.community_jump_trainer.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import georg.steinbacher.community_jump_trainer.R;

/**
 * Created by georg on 04.04.18.
 */

public class CurrentTrainingsPlanView extends CardView {

    private View mRootView;

    private String mTitle = "";

    public CurrentTrainingsPlanView(Context context) {
        super(context);
        init(context);
    }

    public CurrentTrainingsPlanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mRootView = inflate(context, R.layout.view_current_trainings_plan, this);
    }

    public void setTitle(String name) {
        mTitle = name;
        TextView txtView = mRootView.findViewById(R.id.name);
        txtView.setText(name);
    }

    public String getTitle() {
        return mTitle;
    }

    public void setOnStartClickListener(OnClickListener listener) {
        Button button = mRootView.findViewById(R.id.button_start);
        button.setOnClickListener(listener);
    }
}
