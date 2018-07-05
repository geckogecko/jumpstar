package georg.steinbacher.community_jump_trainer;


import android.os.Bundle;
import android.support.annotation.Nullable;


import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompat;

import java.util.Arrays;
import java.util.List;


public class PreferenceFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferencesFix(@Nullable Bundle savedInstanceState, String rootKey) {
        getPreferenceManager().setSharedPreferencesName(Configuration.getPrefName());
        addPreferencesFromResource(R.xml.preferences);
        init();
    }

    private void init() {
        // preferences_trainingsPlan_preparationTime
        /*
        ListPreference preparationTime = (ListPreference) findPreference("preferences_trainingsPlan_preparationTime");
        List<String> myOptions = Arrays.asList((getResources().getStringArray(R.array.preparation_times_values)));
        int index = myOptions.indexOf(Integer.toString(Configuration.getInt(getContext(),
                Configuration.PREPARATION_COUNTDOWN_TIME,
                Configuration.PREPARATION_COUNTDOWN_TIME_DEFAULT)));
        preparationTime.setValueIndex(index);
        */
    }
}
