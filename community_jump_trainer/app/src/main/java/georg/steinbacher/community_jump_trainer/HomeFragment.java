package georg.steinbacher.community_jump_trainer;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

import georg.steinbacher.community_jump_trainer.view.CurrentTrainingsPlanView;
import georg.steinbacher.community_jump_trainer.view.TrainingsPlanHistoryView;
import georg.steinbacher.community_jump_trainer.view.VerticalProgressView;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Is a current trainingsPlan set?
        CurrentTrainingsPlanView currentPlan = view.findViewById(R.id.current_trainingsPlan);
        if(Configuration.isSet(getContext(), Configuration.CURREN_TRAININGSPLAN_ID_KEY)) {
            currentPlan.setTitle(Configuration.getString(getContext(), Configuration.CURREN_TRAININGSPLAN_ID_KEY)); //TODO change to name
        } else {
            currentPlan.setVisibility(View.GONE);
        }

        //Is vertical progress set?
        VerticalProgressView verticalProgress = view.findViewById(R.id.vertical_progress);
        //TODO add logic to hide/show the verticalProgress if we dont have any vertical progress data

        //Load the history views
        //TODO load from db and add dynamicaly
        TrainingsPlanHistoryView trainingsPlanHistoryView = view.findViewById(R.id.trainings_plan_history_test);
        trainingsPlanHistoryView.setTitle("History");
        trainingsPlanHistoryView.setDate(Calendar.getInstance().getTime());
    }
}
