package com.steinbacher.jumpstar.view;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.View;

import com.steinbacher.jumpstar.R;

/**
 * Created by stge on 08.10.18.
 */

public class NewPlanLineView extends LinearLayoutCompat{
    private Context mContext;
    private AppCompatTextView mPlanName;
    private AppCompatButton mSaveButton;

    public NewPlanLineView(Context context) {
        super(context);
        init(context);
    }

    public NewPlanLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        mContext = context;
        inflate(context, R.layout.view_new_plan_line, this);

        mPlanName = findViewById(R.id.plan_name);
        mSaveButton = findViewById(R.id.plan_save_button);
    }

    public void setPlanName(String planName) {
        mPlanName.setText(planName);
    }

    public void setOnSaveButtonClickListener(View.OnClickListener listener) {
        mSaveButton.setOnClickListener(listener);
    }
}
