package com.steinbacher.jumpstar.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.steinbacher.jumpstar.core.Difficulty;
import com.steinbacher.jumpstar.core.Equipment;
import com.steinbacher.jumpstar.core.Exercise;
import com.steinbacher.jumpstar.core.ExerciseDescription;
import com.steinbacher.jumpstar.core.ExerciseStep;
import com.steinbacher.jumpstar.core.StandardExercise;
import com.steinbacher.jumpstar.core.TimeExercise;
import com.steinbacher.jumpstar.core.TrainingsPlan;
import com.steinbacher.jumpstar.core.TrainingsPlanEntry;

/**
 * Created by stge on 13.04.18.
 */

public class Factory {
    private static final String TAG = "Factory";

    public static TrainingsPlan createTraingsPlan(int trainingsPlanId) {
        JSONHolder holder = JSONHolder.getInstance();
        Log.i(TAG, "createTraingsPlan: " + trainingsPlanId);
        JSONObject loadedTraingsPlan = holder.getTraingsPlan(trainingsPlanId);

        //Load the entries
        try {
            List<TrainingsPlanEntry> entries = new ArrayList<>();
            if(loadedTraingsPlan.has("exercises")) {
                JSONArray exercisesJSONArray = loadedTraingsPlan.getJSONArray("exercises");
                for(int i=0; i<exercisesJSONArray.length(); i++) {
                    JSONObject current = exercisesJSONArray.getJSONObject(i);

                    Exercise.Type type = Exercise.Type.valueOf(current.getString("type"));
                    final int exerciseId = current.getInt("id");
                    JSONObject loaded = holder.getExerciseJSON(exerciseId, type);

                    Exercise currentExercise = buildExercise(loaded, type, exerciseId);
                    if(currentExercise != null) {
                        entries.add(currentExercise);
                    } else {
                        Log.e(TAG, "createTraingsPlan: Type not found!");
                    }
                }
            } else if(loadedTraingsPlan.has("trainingsplans")){
                JSONArray trainingsplansJSONArray = loadedTraingsPlan.getJSONArray("trainingsplans");
                for(int i=0; i<trainingsplansJSONArray.length(); i++) {
                    JSONObject current = trainingsplansJSONArray.getJSONObject(i);
                    entries.add(createTraingsPlan(current.getInt("id")));
                }
            }

            return new TrainingsPlan(trainingsPlanId,
                    loadedTraingsPlan.getString("name"),
                    loadedTraingsPlan.getString("description"),
                    entries,
                    loadedTraingsPlan.getLong("creationDate"),
                    null,
                    loadedTraingsPlan.getLong("estimatedTime"),
                    loadedTraingsPlan.getBoolean("isPremium"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Exercise> getAllExercises() {
        JSONObject exeObj = JSONHolder.getInstance().getExercises();

        List<Exercise> exercises = new ArrayList<>();

        try {
            JSONObject standardEx = exeObj.getJSONObject("STANDARD");
            for (int i = 0; i < standardEx.length(); i++) {
                JSONObject jsonObject = standardEx.getJSONObject(Integer.toString(i+1));
                Exercise exercise = buildExercise(jsonObject, Exercise.Type.STANDARD, i+1);
                if(exercise != null) {
                    exercises.add(exercise);
                }
            }

            JSONObject timeEx = exeObj.getJSONObject("TIME");
            for (int i = 0; i < timeEx.length(); i++) {
                JSONObject jsonObject = timeEx.getJSONObject(Integer.toString(i+1));
                Exercise exercise = buildExercise(jsonObject, Exercise.Type.TIME, i+1);
                if(exercise != null) {
                    exercises.add(exercise);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "getAllExercises: ", e);
        }

        return exercises;
    }

    public static List<TrainingsPlan> getAllTrainingsPlans() {
        JSONObject trainingsPlansJSON = JSONHolder.getInstance().getTrainingsPlans();

        List<TrainingsPlan> trainingsPlans = new ArrayList<>();
        for (int i = 1; i <= trainingsPlansJSON.length(); i++) {
            TrainingsPlan trainingsPlan = createTraingsPlan(i);

            if(trainingsPlan != null) {
                trainingsPlans.add(trainingsPlan);
            } else {
                Log.e(TAG, "getAllTrainingsPlans: createTraingsPlan() returned null. Traininsplan not added!");
            }
        }

        return trainingsPlans;
    }

    public static Exercise getExercise(Exercise.Type type, int exerciseId) {
        try {
            return buildExercise(JSONHolder.getInstance().getExercises().getJSONObject(Integer.toString(exerciseId)), type, exerciseId);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Exercise buildExercise(JSONObject exerciseJSON, Exercise.Type type, int exerciseId) throws JSONException {
        JSONHolder holder = JSONHolder.getInstance();
        Exercise ex = null;
        //load the equipmentList
        List<Equipment> equipmentList = new ArrayList<>();
        if (exerciseJSON.has("equipment")) {
            JSONArray equ = exerciseJSON.getJSONArray("equipment");
            for (int j = 0; j < equ.length(); j++) {
                final JSONObject loadEq = holder.getEquipment(equ.getInt(j));
                final String name = loadEq.getString("name");
                final Equipment.Type equType = Equipment.Type.valueOf(loadEq.getString("type"));
                equipmentList.add(new Equipment(name, equType));
            }
        }

        //load the description
        List<ExerciseStep> exerciseSteps = new ArrayList<>();
        if (exerciseJSON.has("steps")) {
            JSONArray steps = exerciseJSON.getJSONArray("steps");
            for (int j = 0; j < steps.length(); j++) {
                final JSONObject loadedStep = steps.getJSONObject(j);
                final int stepNr = Integer.valueOf(loadedStep.getString("nr"));
                final String desc = loadedStep.getString("description");
                exerciseSteps.add(new ExerciseStep(stepNr, desc));
            }
        }
        ExerciseDescription exerciseDescription = null;
        try {
            exerciseDescription = new ExerciseDescription(exerciseSteps);
        } catch (ExerciseDescription.MissingExerciseStepException e) {
            e.printStackTrace();
        }

        if (type == Exercise.Type.STANDARD) {
            ex = new StandardExercise(
                    exerciseId,
                    exerciseJSON.getString("name"),
                    exerciseDescription,
                    equipmentList,
                    new Difficulty(exerciseJSON.getInt("difficulty")),
                    null,
                    Exercise.TargetArea.valueOf(exerciseJSON.getString("targetArea")),
                    exerciseJSON.getInt("sets"),
                    Exercise.Category.valueOf(exerciseJSON.getString("category")),
                    jSonArray2IntArray(exerciseJSON.getJSONArray("repetitions")));
        } else if (type == Exercise.Type.TIME) {
            ex = new TimeExercise(
                    exerciseId,
                    exerciseJSON.getString("name"),
                    exerciseDescription,
                    equipmentList,
                    new Difficulty(exerciseJSON.getInt("difficulty")),
                    null,
                    Exercise.TargetArea.valueOf(exerciseJSON.getString("targetArea")),
                    exerciseJSON.getInt("sets"),
                    Exercise.Category.valueOf(exerciseJSON.getString("category")),
                    exerciseJSON.getInt("time"));

            //overwrite params
            if (exerciseJSON.has("sets")) {
                ex.setSets(exerciseJSON.getInt("sets"));
            }
        }

        return ex;
    }

    private static int[] jSonArray2IntArray(JSONArray jsonArray){
        int[] intArray = new int[jsonArray.length()];
        for (int i = 0; i < intArray.length; ++i) {
            intArray[i] = jsonArray.optInt(i);
        }
        return intArray;
    }
}
