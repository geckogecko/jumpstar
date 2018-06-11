package georg.steinbacher.community_jump_trainer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Georg Steinbacher on 23.04.18.
 */

public class VerticalHeightDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "vertical_height_db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_VERTICAL_HEIGHT =
            "CREATE TABLE " + VerticalHeightContract.VerticalHeightEntry.TABLE_NAME + " (" +
                    VerticalHeightContract.VerticalHeightEntry.COLUMN_NAME_DATE + " INTEGER," +
                    VerticalHeightContract.VerticalHeightEntry.COLUMN_NAME_HEIGHT + " INTEGER)";

    public static final String SQL_DROP_VERTICAL_HEIGHT =
            "DROP TABLE IF EXISTS " + VerticalHeightContract.VerticalHeightEntry.TABLE_NAME;

    public VerticalHeightDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_VERTICAL_HEIGHT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO implemend real upgrade stuff
        db.execSQL(SQL_CREATE_VERTICAL_HEIGHT);
    }
}
