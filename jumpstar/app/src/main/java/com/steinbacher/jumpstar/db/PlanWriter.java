package com.steinbacher.jumpstar.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.steinbacher.jumpstar.core.Exercise;

import java.util.List;

/**
 * Created by Georg Steinbacher on 06.10.18.
 */

public class PlanWriter {
    private static final String TAG = "PlanWriter";
    private SQLiteDatabase mDb;

    public PlanWriter(Context context) {
        mDb = new DbHelper(context).getWritableDatabase();
    }

    public void add(String name, String description, List<Exercise> exercises) {

        String exercisesString = "";
        for(Exercise exercise : exercises) {
            String tempString = "";
            if(exercise.getType() == Exercise.Type.STANDARD) {
                tempString += "S:";
            } else if(exercise.getType() == Exercise.Type.TIME) {
                tempString += "T:";
            }
            tempString += exercise.getId();
            exercisesString += tempString + ";";
        }

        ContentValues value = new ContentValues();
        value.put(PlanContract.PlanContractEntry.COLUMN_NAME_DATE, 0); //TODO
        value.put(PlanContract.PlanContractEntry.COLUMN_NAME_PLAN_NANE, name);
        value.put(PlanContract.PlanContractEntry.COLUMN_NAME_EXERCISES, exercisesString);
        value.put(PlanContract.PlanContractEntry.COLUMN_NAME_PLAN_DESCRIPTION, description);

        mDb.insert(PlanContract.PlanContractEntry.TABLE_NAME, null, value);
    }

    private static final String DELETE_BY_ID = "DELETE FROM "+ PlanContract.PlanContractEntry.TABLE_NAME +
            " WHERE " + PlanContract.PlanContractEntry.COLUMN_NAME_ID + " = ?";

    public int dropById(int id) {
        String[] params = new String[]{ Integer.toString(id) };

        return mDb.delete(PlanContract.PlanContractEntry.TABLE_NAME, PlanContract.PlanContractEntry.COLUMN_NAME_ID + "=?", params);
    }
}
