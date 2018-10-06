package com.steinbacher.jumpstar.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.steinbacher.jumpstar.core.Exercise;

import java.util.List;

/**
 * Created by Georg Steinbacher on 06.10.18.
 */

public class PlanWriter {
    private SQLiteDatabase mDb;

    public PlanWriter(Context context) {
        mDb = new DbHelper(context).getWritableDatabase();
    }

    public void add(String name, List<Exercise> exercises) {

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

        mDb.insert(PlanContract.PlanContractEntry.TABLE_NAME, null, value);
    }
}
