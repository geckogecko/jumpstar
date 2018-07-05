package georg.steinbacher.community_jump_trainer;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompat;
import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompatDividers;

import java.util.Arrays;
import java.util.List;


public class PreferenceFragment extends PreferenceFragmentCompatDividers {

    @Override
    public void onCreatePreferencesFix(@Nullable Bundle savedInstanceState, String rootKey) {
        getPreferenceManager().setSharedPreferencesName(Configuration.getPrefName());
        addPreferencesFromResource(R.xml.preferences);
        init();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            return super.onCreateView(inflater, container, savedInstanceState);
        } finally {
            setDividerPreferences(DIVIDER_PADDING_CHILD | DIVIDER_CATEGORY_AFTER_LAST | DIVIDER_CATEGORY_BETWEEN);
        }
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
