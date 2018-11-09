package com.steinbacher.jumpstar.util;

import android.content.Context;

import org.json.JSONArray;
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
    private JSONObject mTraingsPlanSortOrder;

    private static final JSONHolder ourInstance = new JSONHolder();
    public static JSONHolder getInstance() {
        return ourInstance;
    }
    private JSONHolder() {
    }

    public boolean loadAll(Context context) {
        return loadExercises(context) &&
                loadTrainingsPlans(context) &&
                loadEquipment(context) &&
                loadTrainingsplanSortOrder(context);
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

    private boolean loadTrainingsplanSortOrder(Context context) {
        mTraingsPlanSortOrder = JSONLoader.get(context, R.raw.trainingsplan_display_order);

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

    JSONObject getExercises() {
        return mExercises;
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


    JSONArray getTrainingsplanSortOrder(String sortCategory) {
        try {
            return mTraingsPlanSortOrder.getJSONArray(sortCategory);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
