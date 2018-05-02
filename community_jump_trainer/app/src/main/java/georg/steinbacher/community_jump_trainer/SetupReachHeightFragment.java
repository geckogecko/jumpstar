package georg.steinbacher.community_jump_trainer;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import georg.steinbacher.community_jump_trainer.db.VerticalHeightWriter;
import georg.steinbacher.community_jump_trainer.util.SharedPreferencesManager;


public class SetupReachHeightFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup_reach_height, container, false);

        TextInputEditText reachHeightInput = view.findViewById(R.id.setup_reach_height_input);
        String system = Configuration.getUnitLocal(getContext()) == Configuration.UnitLocal.METRIC
                ? getString(R.string.centimeters_short) : getString(R.string.inches_short);
        reachHeightInput.setHint(getString(R.string.setup_reach_height_input_hint, system));

        reachHeightInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    TextInputEditText reachHeightInput = v.findViewById(R.id.setup_reach_height_input);
                    try {
                        double parsedValue = Double.parseDouble(reachHeightInput.getText().toString());
                        VerticalHeightWriter writer = new VerticalHeightWriter(getContext());
                        writer.add(System.currentTimeMillis(), parsedValue);
                        Configuration.set(getContext(), Configuration.SHOW_VERTICAL_PROGRESS, true);
                    } catch (NumberFormatException e) {
                        Snackbar.make(v, R.string.setup_reach_height_input_error, Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }
            }
        });
        return view;
    }
}
