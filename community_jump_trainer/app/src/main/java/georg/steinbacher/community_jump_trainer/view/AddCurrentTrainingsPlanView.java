package georg.steinbacher.community_jump_trainer.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import georg.steinbacher.community_jump_trainer.R;

//TODO finish this view
public class AddCurrentTrainingsPlanView extends CardView{
    private Context mContext;
    private View mRootView;

    private IAddCurrentTrainingsplanClickedListener mListener;

    public interface IAddCurrentTrainingsplanClickedListener {
        void onAddCurrentTrainingsplanClicked();
    }

    public AddCurrentTrainingsPlanView(Context context) {
        super(context);
        init(context);
    }

    public void setListener(IAddCurrentTrainingsplanClickedListener listener) {
        mListener = listener;
    }

    private void init(Context context){
        mContext = context;
        mRootView = inflate(context, R.layout.view_add_current_trainings_plan, this);

        Button buttonSelect = findViewById(R.id.button_select);
        buttonSelect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null) {
                    mListener.onAddCurrentTrainingsplanClicked();
                }
            }
        });
    }


}
