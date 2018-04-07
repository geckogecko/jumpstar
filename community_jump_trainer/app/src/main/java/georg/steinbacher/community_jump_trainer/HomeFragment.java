package georg.steinbacher.community_jump_trainer;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import georg.steinbacher.community_jump_trainer.view.CurrentTrainingsPlanView;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Is a current trainingsplan set?
        CurrentTrainingsPlanView currentPlan = view.findViewById(R.id.current_trainingsPlan);
        if(Configuration.isSet(getContext(), Configuration.CURREN_TRAININGSPLAN_ID_KEY)) {
            currentPlan.setName(Configuration.getString(getContext(), Configuration.CURREN_TRAININGSPLAN_ID_KEY)); //TODO change to name
        } else {
            currentPlan.setVisibility(View.GONE);
        }
    }
}
