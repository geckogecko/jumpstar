package georg.steinbacher.community_jump_trainer;


import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;
import georg.steinbacher.community_jump_trainer.core.Equipment;
import georg.steinbacher.community_jump_trainer.core.ExerciseStep;
import georg.steinbacher.community_jump_trainer.core.TimeExercise;
import georg.steinbacher.community_jump_trainer.drawables.CategoryPaints;
import georg.steinbacher.community_jump_trainer.view.EquipmentView;
import georg.steinbacher.community_jump_trainer.view.ExerciseStepsView;

import static android.content.ContentValues.TAG;
import static georg.steinbacher.community_jump_trainer.Configuration.PREPARATION_COUNTDOWN_TIME;
import static georg.steinbacher.community_jump_trainer.Configuration.PREPARATION_COUNTDOWN_TIME_DEFAULT;

public class TimeExerciseFragment extends Fragment implements CountdownView.OnCountdownEndListener, CountdownView.OnCountdownIntervalListener{
    private TimeExercise mExercise;
    private View mView;
    private CountdownView mCountdownView;
    private ProgressBar mProgressBar;

    private LinearLayoutCompat bottomLayout;
    private LinearLayoutCompat topLayout;
    private ExerciseStepsView mSteps;
    private TextView mSets;
    private TextView mHoldTime;
    private TextView mPrepare;

    private boolean mPreperationCountdown;
    private boolean mCountdownRunning;
    private boolean mCountdownPaused;

    private Button mExerciseStart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_exercise, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mView = view;
        bottomLayout = mView.findViewById(R.id.bottom_layout_holder);
        topLayout = mView.findViewById(R.id.top_layout_holder);
        mPrepare = mView.findViewById(R.id.prepare);

        //Name
        TextView textView = mView.findViewById(R.id.exercise_name);
        textView.setText(mExercise.getName());

        //ExerciseSteps
        mSteps = mView.findViewById(R.id.exercise_step_view);
        mSteps.setTrainingsplan(mExercise);

        //Countdown
        mCountdownView = mView.findViewById(R.id.exercise_countdown);
        if(mExercise.getTime() == -1) {
            mCountdownView.setVisibility(View.GONE);
        } else {
            mCountdownView.setOnCountdownEndListener(this);
            mCountdownView.setOnCountdownIntervalListener(1000, this); //trigger every second
            mCountdownView.updateShow(mExercise.getTime() * 1000);
        }

        //Button
        mExerciseStart = mView.findViewById(R.id.exercise_start_button);
        if(mExercise.getTime() == -1) {
            mExerciseStart.setVisibility(View.GONE);
        } else {
            mExerciseStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCountdownRunning && !mCountdownPaused) {
                        stopTimer();
                    } else if(!mCountdownRunning && mCountdownPaused) {
                        startTimer();
                    } else if(!mPreperationCountdown){
                        mPreperationCountdown = true;
                        startTimer();
                        mPrepare.setVisibility(View.VISIBLE);
                        mExerciseStart.setEnabled(false);
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mCountdownRunning = true;
                                mPrepare.setVisibility(View.GONE);
                                mExerciseStart.setEnabled(true);
                                mCountdownView.start(mExercise.getTime() * 1000);
                            }
                        }, getPreperationTime() * 1000);
                    }
                }
            });
        }

        //ProgressBar
        mProgressBar = mView.findViewById(R.id.time_exercise_progress_bar);
        if(mExercise.getTime() == -1) {
            mProgressBar.setVisibility(View.GONE);
        }

        //Sets
        mSets = mView.findViewById(R.id.exercise_sets);
        int sets = mExercise.getSets();
        mSets.setText(getString(R.string.exercise_sets, Integer.toString(sets)));

        //Hold time
        mHoldTime = mView.findViewById(R.id.exercise_hold_time);
        final int seconds = mExercise.getTime();
        if(seconds == -1) {
            mHoldTime.setText(getString(R.string.time_exercise_hold_time_as_much));
        } else {
            final int minutes = seconds / 60;
            final int restSeconds = seconds - (minutes * 60);
            String restSecondsString = Integer.toString(restSeconds);
            if(restSeconds < 9) {
                restSecondsString = "0" + restSeconds;
            }

            mHoldTime.setText(getString(R.string.time_exercise_hold_time, minutes + ":" + restSecondsString)
            + " " + getString(R.string.minutes_short));
        }

        //Category
        TextView txtCategory = mView.findViewById(R.id.exercise_category);
        String category = mExercise.getCategory().name().toLowerCase();
        category = category.substring(0, 1).toUpperCase() + category.substring(1);
        txtCategory.setText(category);
        txtCategory.setTextColor(CategoryPaints.getPrimaryColor(getContext(), mExercise.getCategory()).getColor());

        //Equipment
        EquipmentView equipmentViewHolder = mView.findViewById(R.id.equipment_view);
        List<Equipment> equipmentList = mExercise.getNeededEquipment();
        equipmentViewHolder.setEquipment(equipmentList);
    }

    private void stopTimer() {
        mExerciseStart.setText(R.string.time_exercise_resume);

        bottomLayout.setVisibility(View.VISIBLE);
        topLayout.setVisibility(View.VISIBLE);

        mCountdownView.pause();
        mCountdownPaused = true;
        mCountdownRunning = false;

        Drawable drawable = getContext().getResources().getDrawable(R.drawable.baseline_play_arrow_white_24);
        drawable.setBounds(0,0,drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        mExerciseStart.setCompoundDrawables(drawable, null, null, null);
    }

    private void startTimer() {
        mExerciseStart.setText(R.string.time_exercise_pause);

        bottomLayout.setVisibility(View.INVISIBLE);
        topLayout.setVisibility(View.INVISIBLE);

        mCountdownView.restart();
        mCountdownPaused = false;
        mCountdownRunning = true;

        Drawable drawable = getContext().getResources().getDrawable(R.drawable.baseline_pause_white_24);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        mExerciseStart.setCompoundDrawables(drawable, null, null, null);
    }

    public void setExercise(TimeExercise exercise) {
        mExercise = exercise;
    }

    @Override
    public void onEnd(CountdownView cv) {
        if(mPreperationCountdown) {
            mPreperationCountdown = false;
            mProgressBar.setProgress(0);
            mCountdownView.start(mExercise.getTime() * 1000);
        } else {
            mExercise.complete();
        }
    }

    @Override
    public void onInterval(CountdownView cv, long remainTime) {
        int maxTime = mExercise.getTime();
        int passedTime = maxTime - (int)(remainTime/1000);
        int progressPercent = (passedTime * 100) / maxTime;
        mProgressBar.setProgress(progressPercent);
    }

    private int getPreperationTime() {
        return Integer.valueOf(Configuration.getString(mView.getContext(),
            PREPARATION_COUNTDOWN_TIME,
            PREPARATION_COUNTDOWN_TIME_DEFAULT));
    }
}
