package georg.steinbacher.community_jump_trainer;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.iwgang.countdownview.CountdownView;
import georg.steinbacher.community_jump_trainer.core.Exercise;
import georg.steinbacher.community_jump_trainer.core.TimeExercise;

public class TimeExerciseFragment extends Fragment implements CountdownView.OnCountdownEndListener{
    private TimeExercise mExercise;
    private View mView;
    private CountdownView mCountdownView;

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

        //Name
        TextView textView = mView.findViewById(R.id.exercise_name);
        textView.setText(mExercise.getName());

        //Countdown
        mCountdownView = mView.findViewById(R.id.exercise_countdown);
        mCountdownView.setOnCountdownEndListener(this);

        //Button
        Button exerciseStart = mView.findViewById(R.id.exercise_start_button);
        exerciseStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCountdownView.start(mExercise.getTime() * 1000);
            }
        });
    }

    public void setExercise(TimeExercise exercise) {
        mExercise = exercise;
    }

    @Override
    public void onEnd(CountdownView cv) {

    }
}
