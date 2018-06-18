package georg.steinbacher.community_jump_trainer;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class SetupEquipmentFragment extends Fragment implements CheckBox.OnCheckedChangeListener{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup_equipment, container, false);

        //home
        CheckBox equip_home = view.findViewById(R.id.chb_equipment_home);
        equip_home.setChecked(Configuration.getBoolean(getContext(), Configuration.EQUIPMENT_HOME, true));
        equip_home.setOnCheckedChangeListener(this);

        //gym
        CheckBox equip_gym = view.findViewById(R.id.chb_equipment_gym);
        equip_gym.setChecked(Configuration.getBoolean(getContext(), Configuration.EQUIPMENT_GYM, true));
        equip_gym.setOnCheckedChangeListener(this);

        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getId() == R.id.chb_equipment_home) {
            Configuration.set(getContext(), Configuration.EQUIPMENT_HOME, isChecked);
        } else if(buttonView.getId() == R.id.chb_equipment_gym) {
            Configuration.set(getContext(), Configuration.EQUIPMENT_GYM, isChecked);
        }
    }
}
