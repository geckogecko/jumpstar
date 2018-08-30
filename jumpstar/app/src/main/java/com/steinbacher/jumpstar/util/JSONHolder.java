package com.steinbacher.jumpstar.util;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import com.steinbacher.jumpstar.R;
import com.steinbacher.jumpstar.core.Exercise;

/**
 * Created by stge on 13.04.18.
 */

public class JSONHolder {

    private JSONObject mExercises;
    private JSONObject mTraingsPlans;
    private JSONObject mEquipment;

    private static final JSONHolder ourInstance = new JSONHolder();
    public static JSONHolder getInstance() {
        return ourInstance;
    }
    private JSONHolder() {
    }

    public boolean loadAll(Context context) {
        return loadExercises(context) && loadTrainingsPlans(context) && loadEquipment(context);
    }

    private boolean loadExercises(Context context) {
        mExercises = JSONLoader.get(context, R.raw.exercises);

        return mExercises != null;
    }

    private boolean loadTrainingsPlans(Context context) {
        mTraingsPlans = JSONLoader.get(context, R.raw.trainingsplans);

        return mTraingsPlans != null;
    }

    private boolean loadEquipment(Context context) {
        mEquipment = JSONLoader.get(context, R.raw.equipment);

        return mEquipment != null;
    }

    JSONObject getExerciseJSON(int id, Exercise.Type type) {
        try {
            JSONObject exerciseTye = mExercises.getJSONObject(type.name());
            return exerciseTye.getJSONObject(Integer.toString(id));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    JSONObject getTraingsPlan(int id) {
        try {
            return mTraingsPlans.getJSONObject(Integer.toString(id));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    JSONObject getTrainingsPlans() {
        return mTraingsPlans;
    }

    JSONObject getEquipment(int id) {
        try {
            return mEquipment.getJSONObject(Integer.toString(id));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    JSONObject getEquipments() {return mEquipment;}
}
