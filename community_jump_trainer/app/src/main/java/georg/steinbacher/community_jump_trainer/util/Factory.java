package georg.steinbacher.community_jump_trainer.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import georg.steinbacher.community_jump_trainer.core.Difficulty;
import georg.steinbacher.community_jump_trainer.core.Equipment;
import georg.steinbacher.community_jump_trainer.core.Exercise;
import georg.steinbacher.community_jump_trainer.core.ExerciseDescription;
import georg.steinbacher.community_jump_trainer.core.ExerciseStep;
import georg.steinbacher.community_jump_trainer.core.StandardExercise;
import georg.steinbacher.community_jump_trainer.core.TimeExercise;
import georg.steinbacher.community_jump_trainer.core.TrainingsPlan;
import georg.steinbacher.community_jump_trainer.core.TrainingsPlanEntry;

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

                    Exercise ex;
                    Exercise.Type type = Exercise.Type.valueOf(current.getString("type"));
                    final int exerciseId = current.getInt("id");
                    JSONObject loaded = holder.getExerciseJSON(exerciseId, type);

                    //load the equipmentList
                    List<Equipment> equipmentList = new ArrayList<>();
                    if (loaded.has("equipment")) {
                        JSONArray equ = loaded.getJSONArray("equipment");
                        for (int j = 0; j < equ.length(); j++) {
                            final JSONObject loadEq = holder.getEquipment(equ.getInt(j));
                            final String name = loadEq.getString("name");
                            final Equipment.Type equType = Equipment.Type.valueOf(loadEq.getString("type"));
                            equipmentList.add(new Equipment(name, equType));
                        }
                    }

                    //load the description
                    List<ExerciseStep> exerciseSteps = new ArrayList<>();
                    if (loaded.has("steps")) {
                        JSONArray steps = loaded.getJSONArray("steps");
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
                                loaded.getString("name"),
                                exerciseDescription,
                                equipmentList,
                                new Difficulty(loaded.getInt("difficulty")),
                                null,
                                Exercise.TargetArea.valueOf(loaded.getString("targetArea")),
                                loaded.getInt("sets"),
                                Exercise.Category.valueOf(loaded.getString("category")),
                                jSonArray2IntArray(loaded.getJSONArray("repetitions")));
                        entries.add(ex);
                    } else if (type == Exercise.Type.TIME) {
                        ex = new TimeExercise(
                                exerciseId,
                                loaded.getString("name"),
                                exerciseDescription,
                                equipmentList,
                                new Difficulty(loaded.getInt("difficulty")),
                                null,
                                Exercise.TargetArea.valueOf(loaded.getString("targetArea")),
                                loaded.getInt("sets"),
                                Exercise.Category.valueOf(loaded.getString("category")),
                                loaded.getInt("time"));
                        entries.add(ex);
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
                    null);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
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

    private static int[] jSonArray2IntArray(JSONArray jsonArray){
        int[] intArray = new int[jsonArray.length()];
        for (int i = 0; i < intArray.length; ++i) {
            intArray[i] = jsonArray.optInt(i);
        }
        return intArray;
    }
}
