package georg.steinbacher.community_jump_trainer.util;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import georg.steinbacher.community_jump_trainer.R;
import georg.steinbacher.community_jump_trainer.core.Exercise;

/**
 * Created by stge on 13.04.18.
 */

public class JSONHolder {

    private JSONObject mExercises;
    private JSONObject mTraingsPlans;

    private static final JSONHolder ourInstance = new JSONHolder();
    public static JSONHolder getInstance() {
        return ourInstance;
    }
    private JSONHolder() {
    }

    public void loadExercises(Context context) {
        mExercises = JSONLoader.get(context, R.raw.exercises);
    }

    public void loadTrainingsPlans(Context context) {
        mTraingsPlans = JSONLoader.get(context, R.raw.trainingsplans);
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
}
