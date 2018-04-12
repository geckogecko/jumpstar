package georg.steinbacher.community_jump_trainer;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import georg.steinbacher.community_jump_trainer.core.Exercise;

public class TimeExerciseFragment extends Fragment {
    private Exercise mExercise;
    private View mView;

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

        TextView textView = mView.findViewById(R.id.exercise_name);
        textView.setText(mExercise.getName());

        mView.findViewById(R.id.complete_exercise).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExercise.complete();
            }
        });

    }

    public void setExercise(Exercise exercise) {
        mExercise = exercise;

    }

}
