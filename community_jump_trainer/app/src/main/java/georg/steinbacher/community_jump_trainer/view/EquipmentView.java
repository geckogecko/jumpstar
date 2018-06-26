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
        if(equipmentList.size() != 0) {
            LinearLayoutCompat holder = mView.findViewById(R.id.holder);
            for (Equipment equipment : equipmentList) {
                EquipmentLineView equipmentLineView = new EquipmentLineView(mContext);
                equipmentLineView.setEquipment(equipment);
                holder.addView(equipmentLineView);
            }
        }
    }
}
