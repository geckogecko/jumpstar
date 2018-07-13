package com.steinbacher.jumpstar.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;

import com.steinbacher.jumpstar.R;

public class AddCurrentTrainingsPlanView extends LinearLayoutCompat{
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
