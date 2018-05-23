package georg.steinbacher.community_jump_trainer;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class HomeFragment extends Fragment implements VerticalProgressInputView.IInputDoneListener{
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
                CurrentTrainingsPlanView ctpv = new CurrentTrainingsPlanView(view.getContext(), trainingsPlan);
                mLayout.addView(ctpv);
            }
        } else {
            AddCurrentTrainingsPlanView actpv = new AddCurrentTrainingsPlanView(mContext);
            mLayout.addView(actpv);
        }

        //Is vertical progress set?
        if(Configuration.isSet(getContext(), Configuration.SHOW_VERTICAL_PROGRESS) &&
            Configuration.getBoolean(getContext(), Configuration.SHOW_VERTICAL_PROGRESS)) {
            mVerticialProgressV = createVerticalProgressView();

            if(mVerticialProgressV != null) {
                mVerticialProgressV.setData();
            }
        }

        //Vertical Progress input
        VerticalProgressInputView vpiv = new VerticalProgressInputView(getContext());
        vpiv.setListener(this);
        mLayout.addView(vpiv);

        //Load the history views
        //TODO load from db and add dynamicaly or lets remove it ?
        /*
        TrainingsPlanHistoryView trainingsPlanHistoryView = view.findViewById(R.id.trainings_plan_history_test);
        trainingsPlanHistoryView.setTitle("History");
        trainingsPlanHistoryView.setDate(Calendar.getInstance().getTime());
        */
    }

    @Override
    public void onInputDone() {
        if(mVerticialProgressV == null &&
                Configuration.isSet(getContext(), Configuration.SHOW_VERTICAL_PROGRESS) &&
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
}
