package com.steinbacher.jumpstar.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Georg Steinbacher on 06.10.18.
 */

public class PlanReader {
    private static final String TAG = "PlanReader";
    private SQLiteDatabase mDb;

    public PlanReader(Context context) {
        mDb = new DbHelper(context).getReadableDatabase();
    }

    private static final String SELECT_ALL = "SELECT " + PlanContract.PlanContractEntry.COLUMN_NAME_ID + ", " +
            PlanContract.PlanContractEntry.COLUMN_NAME_PLAN_NANE + ", " +
            PlanContract.PlanContractEntry.COLUMN_NAME_EXERCISES + ", " +
            PlanContract.PlanContractEntry.COLUMN_NAME_DATE+ " FROM " +
            PlanContract.PlanContractEntry.TABLE_NAME;

    private static final String SELECT_BY_ID = "SELECT " + PlanContract.PlanContractEntry.COLUMN_NAME_ID + ", " +
            PlanContract.PlanContractEntry.COLUMN_NAME_PLAN_NANE + ", " +
            PlanContract.PlanContractEntry.COLUMN_NAME_EXERCISES + ", " +
            PlanContract.PlanContractEntry.COLUMN_NAME_DATE+ " FROM " +
            PlanContract.PlanContractEntry.TABLE_NAME + " WHERE " + PlanContract.PlanContractEntry.COLUMN_NAME_ID + " = ?";

    public Cursor getAll() {
        return mDb.rawQuery(SELECT_ALL, null);
    }

    public Cursor getById(final int id) {
        String[] params = new String[]{ Integer.toString(id) };

        return mDb.rawQuery(SELECT_BY_ID, params);
    }
}
