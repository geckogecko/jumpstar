package georg.steinbacher.community_jump_trainer.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import georg.steinbacher.community_jump_trainer.R;
import georg.steinbacher.community_jump_trainer.core.Equipment;

import static android.content.ContentValues.TAG;

public class EquipmentView extends LinearLayoutCompat {
    private Context mContext;
    private LinearLayoutCompat mView;

    public EquipmentView(Context context) {
        super(context);
        init(context);
    }

    public EquipmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mView = (LinearLayoutCompat) inflate(mContext, R.layout.view_equipment, this);
    }

    public void setEquipment(List<Equipment> equipmentList) {
        for (Equipment equipment : equipmentList) {
            ImageView imageView = new ImageView(getContext());
            //TODO add an icon for every equipment
            final int drawableId = getContext().getResources().getIdentifier(equipment.getName().toLowerCase().replace(" ", "_").replace("-","_"),
                    "drawable", getContext().getPackageName());
            if (drawableId != 0) {
                imageView.setImageDrawable(getResources().getDrawable(drawableId));
            } else {
                Log.e(TAG, "No drawable found for equipment: " + equipment.getName());
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.baseline_fitness_center_white_18));
            }
            mView.addView(imageView);
        }
    }
    //TODO show a 'no needed equipment icon' if there is no needed equipment
}
