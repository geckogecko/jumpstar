package georg.steinbacher.community_jump_trainer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import georg.steinbacher.community_jump_trainer.core.Equipment;
import georg.steinbacher.community_jump_trainer.core.Exercise;
import georg.steinbacher.community_jump_trainer.core.ExerciseDescription;
import georg.steinbacher.community_jump_trainer.core.ExerciseStep;
import georg.steinbacher.community_jump_trainer.core.StandardExercise;
import georg.steinbacher.community_jump_trainer.drawables.CategoryPaints;
import georg.steinbacher.community_jump_trainer.view.ExerciseStepsView;

public class StandardExerciseFragment extends Fragment {
    private static final String TAG = "StandardExerciseFragmen";

    private StandardExercise mExercise;
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_standard_exercise, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mView = view;

        //background color
        mView.setBackgroundColor(CategoryPaints.getSecondaryColor(getContext(), mExercise.getCategory()).getColor());

        //Name
        TextView nameTextView = mView.findViewById(R.id.exercise_name);
        nameTextView.setText(mExercise.getName());

        //ExerciseSteps
        ExerciseStepsView steps = mView.findViewById(R.id.exercise_step_view);
        steps.setTrainingsplan(mExercise);

        //Sets
        TextView setsTextView = mView.findViewById(R.id.exercise_sets);
        setsTextView.setText(getString(R.string.exercise_sets, Integer.toString(mExercise.getSets())));

        //Repetitions
        TextView repetitionsTextView = mView.findViewById(R.id.exercise_repetitions);
        repetitionsTextView.setText(getString(R.string.exercise_repetitions, Integer.toString(mExercise.getRepetitions())));

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

        //description
        TextView descriptionTextView = mView.findViewById(R.id.exercise_description);
        ExerciseDescription description = mExercise.getDescription();
        if(description != null) {
            List<ExerciseStep> exerciseSteps = mExercise.getDescription().getSteps();
            String descString = "";
            for (ExerciseStep step : exerciseSteps) {
                descString += step.getStepNr() + ": " + step.getDescription() + "\n";
            }
            descriptionTextView.setText(descString);
        } else {
            descriptionTextView.setVisibility(View.GONE);
        }

    }

    public void setExercise(StandardExercise exercise) {
        mExercise = exercise;
    }
}
