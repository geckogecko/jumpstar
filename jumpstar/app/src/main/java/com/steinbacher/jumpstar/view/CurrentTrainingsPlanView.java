package com.steinbacher.jumpstar.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.steinbacher.jumpstar.Configuration;
import com.steinbacher.jumpstar.R;
import com.steinbacher.jumpstar.TrainingActivity;
import com.steinbacher.jumpstar.TrainingsPlanDetailActivity;
import com.steinbacher.jumpstar.core.TrainingsPlan;
import com.steinbacher.jumpstar.drawables.CategorySummaryDrawable;
import com.steinbacher.jumpstar.util.DrawableLoader;

import static com.steinbacher.jumpstar.TrainingsPlanDetailActivity.TRAININGS_PLAN_ID;
import static com.steinbacher.jumpstar.TrainingsPlanDetailActivity.TRAININGS_PLAN_IS_OWN_PLAN;

/**
 * Created by georg on 04.04.18.
 */

public class CurrentTrainingsPlanView extends LinearLayoutCompat implements View.OnLongClickListener, PopupMenu.OnMenuItemClickListener{
    private static final String TAG = "CurrentTrainingsPlanVie";
    private View mRootView;
    private Context mContext;
    private TrainingsPlan mTrainingsPlan;
    private ProgressBar mCategorySummery;

    private IViewRemovedListener mListener;

    public interface IViewRemovedListener {
        void onRemoved();
    }

    public CurrentTrainingsPlanView(Context context, IViewRemovedListener listener,
                                    TrainingsPlan trainingsPlan) {
        super(context);
        init(context, listener, trainingsPlan);
    }

    private void init(Context context, IViewRemovedListener listener, TrainingsPlan trainingsPlan) {
        mContext = context;
        mListener = listener;
        mTrainingsPlan = trainingsPlan;
        mRootView = inflate(context, R.layout.view_current_trainings_plan, this);
        setOnLongClickListener(this);

        TextView txtView = mRootView.findViewById(R.id.name);
        txtView.setText(mTrainingsPlan.getName());

        Button startButton = mRootView.findViewById(R.id.button_start);
        startButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TrainingActivity.class);
                intent.putExtra(TrainingActivity.TRAININGS_PLAN_ID, mTrainingsPlan.getId());
                mContext.startActivity(intent);
            }
        });

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TrainingsPlanDetailActivity.class);
                intent.putExtra(TRAININGS_PLAN_ID, mTrainingsPlan.getId());
                intent.putExtra(TRAININGS_PLAN_IS_OWN_PLAN, mTrainingsPlan.isOwnPlan());
                mContext.startActivity(intent);
            }
        });

        mCategorySummery = findViewById(R.id.categorySummery);
        setCategorySummary(trainingsPlan);

        TextView txtDescription = mRootView.findViewById(R.id.description);
        txtDescription.setText(mTrainingsPlan.getDescription());

        //image
        AppCompatImageView imgView = findViewById(R.id.image);
        DrawableLoader.loadPlanImage(getContext(), mTrainingsPlan, imgView);
    }

    private void setCategorySummary(TrainingsPlan trainingsPlan) {
        CategorySummaryDrawable categorySummaryDrawable = new CategorySummaryDrawable(trainingsPlan, mContext);
        mCategorySummery.setProgressDrawable(categorySummaryDrawable);
    }

    @Override
    public boolean onLongClick(View v) {
        PopupMenu popup = new PopupMenu(mContext, v, Gravity.RIGHT);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.current_trainingsplan_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
        return false;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(item.getTitle().equals(mContext.getString(R.string.remove))) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            mRootView.setVisibility(View.GONE);
            parent.removeView(mRootView);

            //remove from config
            int[] currentConfig = Configuration.getIntArray(mContext, Configuration.CURRENT_TRAININGSPLANS_ID_KEY);
            int[] newConfig = new int[currentConfig.length-1];
            int j = 0;
            final int planId = mTrainingsPlan.isOwnPlan() ? -mTrainingsPlan.getId() : mTrainingsPlan.getId();
            for (int i = 0; i < currentConfig.length; i++) {
                if(currentConfig[i] != planId) {
                    newConfig[j] = currentConfig[i];
                    j++;
                }
            }

            Configuration.set(mContext, Configuration.CURRENT_TRAININGSPLANS_ID_KEY, newConfig);
            mListener.onRemoved();
        }
        return false;
    }
}
