package georg.steinbacher.community_jump_trainer;

import android.content.Context;

import java.util.Locale;

import georg.steinbacher.community_jump_trainer.util.SharedPreferencesManager;

/**
 * Created by georg on 06.04.18.
 */

public class Configuration {
    public static final String SETUP_COMPLETED_KEY = "setup_completed";
    public static final String REACHED_HEIGHT_KEY = "reached_height";
    public static final String UNIT_LOCAL_KEY = "reached_height";
    public static final String CURREN_TRAININGSPLAN_ID_KEY = "current_trainingsPlan";

    public enum UnitLocal {
        METRIC,
        IMPERIAL
    }

    public static boolean isSet(Context context, String key) {
        return SharedPreferencesManager.contains(context, key);
    }

    //Boolean
    public static void set(Context context, String key, boolean value) {
        SharedPreferencesManager.writeBoolean(context, key, value);
    }

    public static boolean getBoolean(Context context, String key) {
        return SharedPreferencesManager.getBoolean(context, key, false);
    }

    //Double
    public static void set(Context context, String key, double value) {
        SharedPreferencesManager.writeDouble(context, key, value);
    }

    public static double getDouble(Context context, String key) {
        return SharedPreferencesManager.getDouble(context, key, -1);
    }

    //Int
    public static void set(Context context, String key, int value) {
        SharedPreferencesManager.writeInt(context, key, value);
    }

    public static int getInt(Context context, String key) {
        return SharedPreferencesManager.getInt(context, key, -1);
    }

    public static void set(Context context, String key, int[] value) {
        String string = "";
        for (int i = 0; i < value.length; i++) {
            string += Integer.toString(value[i]);

            if(i != value.length -1) {
                string += ",";
            }
        }

        SharedPreferencesManager.writeString(context, key, string);
    }

    public static int[] getIntArray(Context context, String key) {
        String string = SharedPreferencesManager.getString(context, key, "");
        String[] splited = string.split(",");

        int[] numbers = new int[splited.length];
        for (int i = 0; i < splited.length; i++) {
            numbers[i] = Integer.parseInt(splited[i]);
        }

        return numbers;
    }

    //String
    public static void set(Context context, String key, String value) {
        SharedPreferencesManager.writeString(context, key, value);
    }

    public static String getString(Context context, String key) {
        return SharedPreferencesManager.getString(context, key, "");
    }

    //UnitLocal
    public static void setUnitLocal(Context context, Locale locale) {
        String countryCode = locale.getCountry();
        switch (countryCode) {
            case "US":
            case "LR":
            case "MM":
                set(context, UNIT_LOCAL_KEY, UnitLocal.IMPERIAL.name());
                break;
            default:
                set(context, UNIT_LOCAL_KEY, UnitLocal.METRIC.name());
        }
    }

    public static UnitLocal getUnitLocal(Context context) {
        String unitLocalString = SharedPreferencesManager.getString(context, UNIT_LOCAL_KEY, "");

        if(unitLocalString.equals(UnitLocal.IMPERIAL.name())) {
            return UnitLocal.IMPERIAL;
        }

        if(unitLocalString.equals(UnitLocal.METRIC.name())) {
            return UnitLocal.METRIC;
        }

        return UnitLocal.METRIC;
    }


}
