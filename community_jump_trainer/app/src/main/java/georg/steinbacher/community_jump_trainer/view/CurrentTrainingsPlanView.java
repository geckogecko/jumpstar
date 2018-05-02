package georg.steinbacher.community_jump_trainer.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import georg.steinbacher.community_jump_trainer.Configuration;
import georg.steinbacher.community_jump_trainer.R;
import georg.steinbacher.community_jump_trainer.TrainingActivity;
import georg.steinbacher.community_jump_trainer.core.TrainingsPlan;

/**
 * Created by georg on 04.04.18.
 */

public class CurrentTrainingsPlanView extends CardView implements View.OnLongClickListener, PopupMenu.OnMenuItemClickListener{

    private View mRootView;
    private Context mContext;
    private TrainingsPlan mTrainingsplan;

    public CurrentTrainingsPlanView(Context context, TrainingsPlan trainingsPlan) {
        super(context);
        init(context, trainingsPlan);
    }

    private void init(Context context, TrainingsPlan trainingsPlan) {
        mContext = context;
        mTrainingsplan = trainingsPlan;
        mRootView = inflate(context, R.layout.view_current_trainings_plan, this);
        setOnLongClickListener(this);

        TextView txtView = mRootView.findViewById(R.id.name);
        txtView.setText(mTrainingsplan.getName());

        Button button = mRootView.findViewById(R.id.button_start);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TrainingActivity.class);
                intent.putExtra(TrainingActivity.TRAININGS_PLAN_ID, mTrainingsplan.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public boolean onLongClick(View v) {
        PopupMenu popup = new PopupMenu(mContext, v, Gravity.RIGHT);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.current_trainingsplan_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
        return false;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(item.getTitle().equals(mContext.getString(R.string.remove))) {
            mRootView.setVisibility(View.GONE);

            //remove from config
            int[] currentConfig = Configuration.getIntArray(mContext, Configuration.CURREN_TRAININGSPLAN_ID_KEY);
            int[] newConfig = new int[currentConfig.length-1];
            int j = 0;
            for (int i = 0; i < currentConfig.length; i++) {
                if(currentConfig[i] != mTrainingsplan.getId()) {
                    newConfig[j] = currentConfig[i];
                    j++;
                }
            }
            Configuration.set(mContext, Configuration.CURREN_TRAININGSPLAN_ID_KEY, newConfig);
        }
        return false;
    }
}
