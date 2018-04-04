package georg.steinbacher.community_jump_trainer.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by georg on 18.03.18.
 */

public class SharedPreferencesManager {
    private static final String TAG = "SharedPreferencesManage";

    private static final String PREFERENCES_FILE_KEY = "defaultPrefs";

    public static void clear(Context context) {
        getSharedPreferencesEditor(context).clear().apply();
    }

    public static void writeInt(Context context, String key, int value) {
        getSharedPreferencesEditor(context).putInt(key, value).apply();
    }

    public static int getInt(Context context, String key, int defaultValue) {
        return context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE).getInt(key, defaultValue);
    }

    public static void writeBoolean(Context context, String key, boolean value) {
        getSharedPreferencesEditor(context).putBoolean(key, value).apply();
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE).getBoolean(key, defaultValue);
    }

    public static void writeDouble(Context context, String key, double value) {
        getSharedPreferencesEditor(context).putLong(key, Double.doubleToRawLongBits(value)).apply();
    }

    public static double getDouble(Context context, String key, double defaultValue) {
        return Double.longBitsToDouble(context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
                .getLong(key, Double.doubleToLongBits(defaultValue)));
    }

    private static SharedPreferences.Editor getSharedPreferencesEditor(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE);
        return sharedPref.edit();
    }
}
