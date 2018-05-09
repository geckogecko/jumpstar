package georg.steinbacher.community_jump_trainer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import georg.steinbacher.community_jump_trainer.core.Equipment;
import georg.steinbacher.community_jump_trainer.core.Exercise;
import georg.steinbacher.community_jump_trainer.core.StandardExercise;

public class StandardExerciseFragment extends Fragment {
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

        //Name
        TextView nameTextView = mView.findViewById(R.id.exercise_name);
        nameTextView.setText(mExercise.getName());

        //Sets
        TextView setsTextView = mView.findViewById(R.id.exercise_sets);
        setsTextView.setText(getString(R.string.exercise_sets, Integer.toString(mExercise.getSets())));

        //Repetitions
        TextView repetitionsTextView = mView.findViewById(R.id.exercise_repetitions);
        repetitionsTextView.setText(getString(R.string.exercise_repetitions, Integer.toString(mExercise.getRepetitions())));

        //Equipment
        TextView equipmentTextView = mView.findViewById(R.id.exercise_equipment);
        List<Equipment> equipmentList = mExercise.getNeededEquipment();
        if(equipmentList.size() > 0) {
            String equipmentString = "";
            for (Equipment equipment : equipmentList) {
                equipmentString += equipment.getName() + ",";
            }
            equipmentString = equipmentString.substring(0, equipmentString.length() - 1);


            equipmentTextView.setText(getString(R.string.exercise_equipment, equipmentString));
        } else {
            equipmentTextView.setVisibility(View.GONE);
        }
    }

    public void setExercise(StandardExercise exercise) {
        mExercise = exercise;
    }
}
