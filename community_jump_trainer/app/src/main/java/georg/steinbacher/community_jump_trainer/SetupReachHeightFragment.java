package georg.steinbacher.community_jump_trainer;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import georg.steinbacher.community_jump_trainer.core.Configuration;


public class SetupReachHeightFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup_reach_height, container, false);

        TextInputEditText reachHeightInput = view.findViewById(R.id.setup_reach_height_input);
        String system = Configuration.getInstance().getUnitLocal() == Configuration.UnitLocal.METRIC
                ? getString(R.string.centimeters_short) : getString(R.string.inches_short);
        reachHeightInput.setHint(getString(R.string.setup_reach_height_input_hint, system));

        return view;
    }
}
