package georg.steinbacher.community_jump_trainer;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import georg.steinbacher.community_jump_trainer.core.TrainingsPlan;
import georg.steinbacher.community_jump_trainer.db.VerticalHeightReader;
import georg.steinbacher.community_jump_trainer.util.Factory;
import georg.steinbacher.community_jump_trainer.view.AddCurrentTrainingsPlanView;
import georg.steinbacher.community_jump_trainer.view.CurrentTrainingsPlanView;
import georg.steinbacher.community_jump_trainer.view.VerticalProgressInputView;
import georg.steinbacher.community_jump_trainer.view.VerticalProgressView;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment implements VerticalProgressInputView.IInputDoneListener, CurrentTrainingsPlanView.IViewRemovedListener{
    private Context mContext;
    private LinearLayoutCompat mLayout;
    private VerticalProgressView mVerticialProgressV;

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
        if(Configuration.isSet(getContext(), Configuration.CURRENT_TRAININGSPLANS_ID_KEY)) {
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
        if(Configuration.getBoolean(getContext(), Configuration.SHOW_VERTICAL_PROGRESS)) {
            mVerticialProgressV = createVerticalProgressView();

            if(mVerticialProgressV != null) {
                mVerticialProgressV.setData();
            }

            //Vertical Progress input
            VerticalProgressInputView vpiv = new VerticalProgressInputView(getContext());
            vpiv.setListener(this);
            mLayout.addView(vpiv);
        }
    }

    @Override
    public void onInputDone() {
        if(mVerticialProgressV == null &&
                Configuration.getBoolean(getContext(), Configuration.SHOW_VERTICAL_PROGRESS)) {
            mVerticialProgressV = createVerticalProgressView();
        }

        if(mVerticialProgressV != null) {
            mVerticialProgressV.setData();
        }
    }

    private VerticalProgressView createVerticalProgressView() {
        VerticalProgressView verticalProgress = null;
        VerticalHeightReader reader = new VerticalHeightReader(getContext());
        Cursor cursor = reader.getAll();

        if (cursor.getCount() > 0) {
            verticalProgress = new VerticalProgressView(mContext);
            mLayout.addView(verticalProgress, 0);
        } else {
            Log.w(TAG, "SHOW_VERTICAL_PROGRESS is true but there seems to be no data");
        }

        return verticalProgress;
    }

    @Override
    public void onRemoved() {
        ((MainActivity)getActivity()).changeContent(R.id.navigation_home, false);
    }
}
