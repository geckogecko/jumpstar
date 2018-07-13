package com.steinbacher.jumpstar;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.steinbacher.jumpstar.core.TrainingsPlan;
import com.steinbacher.jumpstar.util.Factory;
import com.steinbacher.jumpstar.view.AddCurrentTrainingsPlanView;
import com.steinbacher.jumpstar.view.CurrentTrainingsPlanView;
import com.steinbacher.jumpstar.view.VerticalProgressView;

public class HomeFragment extends Fragment implements CurrentTrainingsPlanView.IViewRemovedListener{
    private Context mContext;
    private LinearLayoutCompat mLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLayout = view.findViewById(R.id.home_fragment_layout);
        mContext = view.getContext();

        //Is a current trainingsPlan set?
        if(Configuration.isSet(getContext(), Configuration.CURRENT_TRAININGSPLANS_ID_KEY) &&
                Configuration.getIntArray(getContext(),Configuration.CURRENT_TRAININGSPLANS_ID_KEY).length > 0) {
            final int[] trainingsPlanIds = Configuration.getIntArray(getContext(), Configuration.CURRENT_TRAININGSPLANS_ID_KEY);
            for (int i = 0; i < trainingsPlanIds.length; i++) {
                final int id = trainingsPlanIds[i];
                final TrainingsPlan trainingsPlan = Factory.createTraingsPlan(id);
                CurrentTrainingsPlanView ctpv = new CurrentTrainingsPlanView(view.getContext(),this,  trainingsPlan);
                mLayout.addView(ctpv);
            }
        } else {
            AddCurrentTrainingsPlanView actpv = new AddCurrentTrainingsPlanView(mContext);
            actpv.setListener(new AddCurrentTrainingsPlanView.IAddCurrentTrainingsplanClickedListener() {
                @Override
                public void onAddCurrentTrainingsplanClicked() {
                    ((MainActivity)getActivity()).changeContent(R.id.navigation_trainingsPlanChooser, true);
                }
            });
            mLayout.addView(actpv);
        }

        //show vertical progress?
        if(Configuration.getBoolean(getContext(), Configuration.SHOW_VERTICAL_PROGRESS, true)) {
            VerticalProgressView verticalProgress = new VerticalProgressView(mContext);
            mLayout.addView(verticalProgress, 0);
        }
    }

    @Override
    public void onRemoved() {
        ((MainActivity)getActivity()).changeContent(R.id.navigation_home, false);
    }
}
