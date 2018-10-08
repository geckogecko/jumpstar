package com.steinbacher.jumpstar.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.PopupMenu;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.steinbacher.jumpstar.Configuration;
import com.steinbacher.jumpstar.R;
import com.steinbacher.jumpstar.TrainingsPlanDetailActivity;
import com.steinbacher.jumpstar.core.TrainingsPlan;
import com.steinbacher.jumpstar.drawables.CategorySummaryDrawable;

import static android.content.ContentValues.TAG;
import static com.steinbacher.jumpstar.TrainingsPlanDetailActivity.TRAININGS_PLAN_ID;
import static com.steinbacher.jumpstar.TrainingsPlanDetailActivity.TRAININGS_PLAN_IS_OWN_PLAN;


/**
 * Created by georg on 04.04.18.
 */

public class TrainingsPlanView extends LinearLayoutCompat implements View.OnLongClickListener, View.OnClickListener, PopupMenu.OnMenuItemClickListener{
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

    private IRemoveMeListener mListener;
    public interface IRemoveMeListener {
        void remove(int id);
    }

    public void setRemoveMeListener(IRemoveMeListener listener) {
        mListener = listener;
    }

    private void init(Context context) {
        mContext = context;
        inflate(context, R.layout.view_trainings_plan, this);
        mCategorySummery = findViewById(R.id.categorySummery);
        setOnClickListener(this);
        setOnLongClickListener(this);
    }

    public void setTrainingsPlan(TrainingsPlan trainingsPlan) {
        mTrainingsPlan = trainingsPlan;
        setCategorySummary(trainingsPlan);

        //title
        TextView txtTitle = findViewById(R.id.name);
        txtTitle.setText(mTrainingsPlan.getName());

        //description
        TextView txtDescription = findViewById(R.id.description);
        txtDescription.setText(mTrainingsPlan.getDescription());

        //image
        AppCompatImageView imgView = findViewById(R.id.image);
        final String imageName = "trainingsplan_" + mTrainingsPlan.getId();
        final int resourceId = mContext.getResources().getIdentifier(imageName, "drawable",
                mContext.getPackageName());

        if(resourceId != 0) {
            Drawable d = getResources().getDrawable(resourceId);
            imgView.setImageDrawable(d);
        } else {
            Log.e(TAG, "image for trainingsplan: " + imageName + " not found");
            //TODO what should we do if the image is not found?
        }

        //cash_icon
        AppCompatImageView cashIcon = findViewById(R.id.cash_icon);
        if(mTrainingsPlan.isPremium()) {
            cashIcon.setVisibility(View.VISIBLE);
        } else {
            cashIcon.setVisibility(View.INVISIBLE);
        }
    }

    private void setCategorySummary(TrainingsPlan trainingsPlan) {
        CategorySummaryDrawable categorySummaryDrawable = new CategorySummaryDrawable(trainingsPlan, mContext);
        mCategorySummery.setProgressDrawable(categorySummaryDrawable);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), TrainingsPlanDetailActivity.class);
        intent.putExtra(TRAININGS_PLAN_ID, mTrainingsPlan.getId());
        intent.putExtra(TRAININGS_PLAN_IS_OWN_PLAN, mTrainingsPlan.isOwnPlan());
        mContext.startActivity(intent);
    }

    @Override
    public boolean onLongClick(View view) {
        if(mTrainingsPlan.isOwnPlan()) {
            PopupMenu popup = new PopupMenu(mContext, view, Gravity.RIGHT);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.current_trainingsplan_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(this);
            popup.show();
        }
        return false;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(item.getTitle().equals(mContext.getString(R.string.remove))) {
            if(mListener != null) {
                mListener.remove(mTrainingsPlan.getId());
            }
        }
        return false;
    }
}
