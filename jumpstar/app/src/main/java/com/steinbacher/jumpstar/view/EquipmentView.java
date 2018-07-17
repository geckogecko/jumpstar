package com.steinbacher.jumpstar.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;

import java.util.List;

import com.steinbacher.jumpstar.R;
import com.steinbacher.jumpstar.core.Equipment;

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
        LinearLayoutCompat holder = mView.findViewById(R.id.holder);

        if(equipmentList.size() != 0) {
            for (Equipment equipment : equipmentList) {
                EquipmentLineView equipmentLineView = new EquipmentLineView(mContext);
                equipmentLineView.setEquipment(equipment);
                holder.addView(equipmentLineView);
            }
        } else {
            //add the none equipment
            EquipmentLineView equipmentLineView = new EquipmentLineView(mContext);
            equipmentLineView.setEquipment(null);
            holder.addView(equipmentLineView);
        }
    }
}
