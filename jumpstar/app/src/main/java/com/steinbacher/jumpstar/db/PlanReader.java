package com.steinbacher.jumpstar.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Georg Steinbacher on 06.10.18.
 */

public class PlanReader {
    private SQLiteDatabase mDb;

    public PlanReader(Context context) {
        mDb = new DbHelper(context).getReadableDatabase();
    }

    private static final String SELECT_ALL = "SELECT " + PlanContract.PlanContractEntry.COLUMN_NAME_PLAN_NANE + ", " +
            PlanContract.PlanContractEntry.COLUMN_NAME_EXERCISES + ", " +
            PlanContract.PlanContractEntry.COLUMN_NAME_DATE+ " FROM " +
            PlanContract.PlanContractEntry.TABLE_NAME;

    public Cursor getAll() {
        return mDb.rawQuery(SELECT_ALL, null);
    }
}
