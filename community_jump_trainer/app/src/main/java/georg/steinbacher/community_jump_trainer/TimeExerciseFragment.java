package georg.steinbacher.community_jump_trainer;


import android.os.Bundle;
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
import java.util.List;

import cn.iwgang.countdownview.CountdownView;
import georg.steinbacher.community_jump_trainer.core.Equipment;
import georg.steinbacher.community_jump_trainer.core.TimeExercise;
import georg.steinbacher.community_jump_trainer.drawables.CategoryPaints;

import static android.content.ContentValues.TAG;
import static georg.steinbacher.community_jump_trainer.Configuration.PREPARATION_COUNTDOWN_TIME;
import static georg.steinbacher.community_jump_trainer.Configuration.PREPARATION_COUNTDOWN_TIME_DEFAULT;

public class TimeExerciseFragment extends Fragment implements CountdownView.OnCountdownEndListener, CountdownView.OnCountdownIntervalListener{
    private TimeExercise mExercise;
    private View mView;
    private CountdownView mCountdownView;
    private ProgressBar mProgressBar;
    private TextView mSets;
    private TextView mHoldTime;
    private boolean mPreperationCountdown;
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

        //background color
        mView.setBackgroundColor(CategoryPaints.getSecondaryColor(getContext(), mExercise.getCategory()).getColor());

        //Name
        TextView textView = mView.findViewById(R.id.exercise_name);
        textView.setText(mExercise.getName());

        //Countdown
        mCountdownView = mView.findViewById(R.id.exercise_countdown);
        mCountdownView.setOnCountdownEndListener(this);
        mCountdownView.setOnCountdownIntervalListener(1000, this); //trigger every second

        //Button
        mExerciseStart = mView.findViewById(R.id.exercise_start_button);
        mExerciseStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPreperationCountdown = true;
                mCountdownView.start(getMaxTime(mPreperationCountdown) * 1000);
                mExerciseStart.setEnabled(false);
            }
        });

        //ProgressBar
        mProgressBar = mView.findViewById(R.id.time_exercise_progress_bar);

        //Sets
        mSets = mView.findViewById(R.id.exercise_sets);
        mSets.setText(getString(R.string.exercise_sets, Integer.toString(mExercise.getSets())));

        //Hold time
        final int seconds = mExercise.getTime();
        final int minutes = seconds / 60;
        final int restSeconds = seconds - (minutes*60);
        mHoldTime = mView.findViewById(R.id.exercise_hold_time);
        mHoldTime.setText(getString(R.string.time_exercise_hold_time, minutes + ":" + restSeconds));

        //Equipment
        //TODO add an icon for every equipment
        LinearLayoutCompat equipmentViewHolder= mView.findViewById(R.id.equipment_icon_container);
        List<Equipment> equipmentList = mExercise.getNeededEquipment();
        for (Equipment equipment: equipmentList) {
            ImageView imageView = new ImageView(getContext());
            final int drawableId = getContext().getResources().getIdentifier(equipment.getName(),
                    "drawable", getContext().getPackageName());
            if(drawableId != 0) {
                imageView.setImageDrawable(getResources().getDrawable(drawableId));
            } else {
                Log.e(TAG, "No drawable found for equipment: " + equipment.getName());
                imageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher_round));
            }
            equipmentViewHolder.addView(imageView);
        }
        //TODO show a 'no needed equipment icon' if there is no needed equipment
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
        int maxTime = getMaxTime(mPreperationCountdown);
        int passedTime = maxTime - (int)(remainTime/1000);
        int progressPercent = (passedTime * 100) / maxTime;
        mProgressBar.setProgress(progressPercent);
    }

    private int getMaxTime(boolean preparation) {
        if(preparation) {
            return Integer.valueOf(Configuration.getString(mView.getContext(),
                    PREPARATION_COUNTDOWN_TIME,
                    PREPARATION_COUNTDOWN_TIME_DEFAULT));
        } else {
            return mExercise.getTime();
        }
    }
}
