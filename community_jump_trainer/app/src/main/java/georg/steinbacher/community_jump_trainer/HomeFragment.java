package georg.steinbacher.community_jump_trainer;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

import georg.steinbacher.community_jump_trainer.core.TrainingsPlan;
import georg.steinbacher.community_jump_trainer.db.VerticalHeightReader;
import georg.steinbacher.community_jump_trainer.util.Factory;
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
        LinearLayoutCompat layout = view.findViewById(R.id.home_fragment_layout);

        //Is a current trainingsPlan set?
        if(Configuration.isSet(getContext(), Configuration.CURREN_TRAININGSPLAN_ID_KEY)) {
            final int[] trainingsPlanIds = Configuration.getIntArray(getContext(), Configuration.CURREN_TRAININGSPLAN_ID_KEY);
            for (int i = 0; i < trainingsPlanIds.length; i++) {
                final int id = trainingsPlanIds[i];
                final TrainingsPlan trainingsPlan = Factory.createTraingsPlan(id);
                CurrentTrainingsPlanView ctpv = new CurrentTrainingsPlanView(view.getContext(), trainingsPlan);
                layout.addView(ctpv);
            }
        }

        //Is vertical progress set?
        VerticalHeightReader reader = new VerticalHeightReader(getContext());
        Cursor cursor = reader.getAll();
        if(cursor.getCount() > 0) {
            VerticalProgressView vpv = new VerticalProgressView(view.getContext());
            layout.addView(vpv, 0);
        }

        //Load the history views
        //TODO load from db and add dynamicaly or lets remove it ?
        /*
        TrainingsPlanHistoryView trainingsPlanHistoryView = view.findViewById(R.id.trainings_plan_history_test);
        trainingsPlanHistoryView.setTitle("History");
        trainingsPlanHistoryView.setDate(Calendar.getInstance().getTime());
        */
    }
}
