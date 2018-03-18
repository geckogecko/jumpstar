package georg.steinbacher.community_jump_trainer.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by georg on 18.03.18.
 */

class SharedPreferencesManager {
    private static final String TAG = "SharedPreferencesManage";

    private static final String PREFERENCES_FILE_KEY = "defaultPrefs";

    public static <T> void writeInt(Context context, String key, int value) {
        getSharedPreferencesEditor(context).putInt(key,value).commit();
    }

    private static SharedPreferences.Editor getSharedPreferencesEditor(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE);
        return sharedPref.edit();
    }
}
