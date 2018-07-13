package com.steinbacher.jumpstar.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

import com.steinbacher.jumpstar.R;

/**
 * Created by georg on 07.04.18.
 */


//TODO remove ?
public class TrainingsPlanHistoryView extends CardView {

    private View mRootView;

    private String mTitle = "";
    private Date mDate;

    public TrainingsPlanHistoryView(Context context) {
        super(context);
        init(context);
    }

    public TrainingsPlanHistoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mRootView = inflate(context, R.layout.view_trainings_plan_history, this);
    }

    public void setTitle(String name) {
        mTitle = name;
        TextView txtView = mRootView.findViewById(R.id.name);
        txtView.setText(name);
    }

    public String getTitle() {
        return mTitle;
    }

    public void setDate(Date date) {
        mDate = date;
        TextView txtView = mRootView.findViewById(R.id.date);
        txtView.setText(date.toString());
    }

    public Date getDate() {
        return mDate;
    }
}
