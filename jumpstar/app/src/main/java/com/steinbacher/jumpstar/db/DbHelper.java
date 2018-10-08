package com.steinbacher.jumpstar.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Georg Steinbacher on 23.04.18.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "vertical_height_db";
    private static final int DATABASE_VERSION = 2;

    private static final String SQL_CREATE_VERTICAL_HEIGHT =
            "CREATE TABLE " + VerticalHeightContract.VerticalHeightEntry.TABLE_NAME + " (" +
                    VerticalHeightContract.VerticalHeightEntry.COLUMN_NAME_DATE + " INTEGER," +
                    VerticalHeightContract.VerticalHeightEntry.COLUMN_NAME_HEIGHT + " INTEGER)";

    public static final String SQL_DROP_VERTICAL_HEIGHT =
            "DROP TABLE IF EXISTS " + VerticalHeightContract.VerticalHeightEntry.TABLE_NAME;

    private static final String SQL_CREATE_PLANS =
            "CREATE TABLE " + PlanContract.PlanContractEntry.TABLE_NAME + " (" +
                    PlanContract.PlanContractEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    PlanContract.PlanContractEntry.COLUMN_NAME_DATE + " INTEGER," +
                    PlanContract.PlanContractEntry.COLUMN_NAME_PLAN_NANE + " TEXT," +
                    PlanContract.PlanContractEntry.COLUMN_NAME_EXERCISES + " TEXT)";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_VERTICAL_HEIGHT);
        db.execSQL(SQL_CREATE_PLANS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch(oldVersion) {
            case 1:
                //upgrade logic from version 1 to 2
                db.execSQL(SQL_CREATE_PLANS);
            case 2:
                //upgrade logic from version 2 to 3
            case 3:
                //upgrade logic from version 3 to 4
                break;
            default:
                throw new IllegalStateException(
                        "onUpgrade() with unknown oldVersion " + oldVersion);
        }
    }
}
