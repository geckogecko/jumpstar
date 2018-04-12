package georg.steinbacher.community_jump_trainer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import georg.steinbacher.community_jump_trainer.core.Exercise;

public class StandardExerciseFragment extends Fragment {
    private Exercise mExercise;
    private View mView;
    private int mProgress;
    private int mMaxProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_standard_exercise, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mView = view;

        //Name
        TextView textView = mView.findViewById(R.id.exercise_name);
        textView.setText(mExercise.getName());

        mView.findViewById(R.id.complete_exercise).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExercise.complete();
            }
        });

        //Progress
        ProgressBar progressBar = mView.findViewById(R.id.progress_bar);
        progressBar.setMax(mMaxProgress);
        progressBar.setProgress(mProgress);
    }

    public void setExercise(Exercise exercise) {
        mExercise = exercise;

    }

    public void setProgress(int progress, int maxProgress) {
        mProgress = progress;
        mMaxProgress = maxProgress;
    }
}
