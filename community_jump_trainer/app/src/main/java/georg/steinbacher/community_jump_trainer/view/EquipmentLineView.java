package georg.steinbacher.community_jump_trainer.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import georg.steinbacher.community_jump_trainer.R;
import georg.steinbacher.community_jump_trainer.core.Equipment;

public class EquipmentLineView extends LinearLayoutCompat {
    private static final String TAG = "EquipmentLineView";

    Context mContext;
    Equipment mEquipment;
    View mView;

    public EquipmentLineView(Context context) {
        super(context);
        init(context);
    }

    public EquipmentLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    void init(Context context) {
        mContext = context;
        mView = inflate(mContext, R.layout.view_equipment_line, this);
    }

    void setEquipment(Equipment equipment) {
        mEquipment = equipment;

        ImageView image = findViewById(R.id.equipment_image);
        final int drawableId = getContext().getResources().getIdentifier(equipment.getName().toLowerCase().replace(" ", "_").replace("-", "_"),
                "drawable", getContext().getPackageName());
        if (drawableId != 0) {
            image.setImageDrawable(getResources().getDrawable(drawableId));
        } else {
            Log.e(TAG, "No drawable found for equipment: " + equipment.getName());
            image.setImageDrawable(getResources().getDrawable(R.drawable.baseline_fitness_center_white_18));
        }

        TextView name = findViewById(R.id.equipment_name);
        name.setText(equipment.getName());
    }
}
