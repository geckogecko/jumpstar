package com.steinbacher.jumpstar.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Georg Steinbacher on 23.04.18.
 */

public class VerticalHeightReader {
    private SQLiteDatabase mDb;

    public VerticalHeightReader(Context context) {
        mDb = new DbHelper(context).getReadableDatabase();
    }

    private static final String SELECT_ALL = "SELECT " + VerticalHeightContract.VerticalHeightEntry.COLUMN_NAME_HEIGHT + ", " +
            VerticalHeightContract.VerticalHeightEntry.COLUMN_NAME_DATE+ " FROM " +
            VerticalHeightContract.VerticalHeightEntry.TABLE_NAME;
    public Cursor getAll() {
        return mDb.rawQuery(SELECT_ALL, null);
    }
}
