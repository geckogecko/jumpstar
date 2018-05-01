package georg.steinbacher.community_jump_trainer.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Georg Steinbacher on 23.04.18.
 */

public class VerticalHeightWriter {

    private SQLiteDatabase mDb;

    public VerticalHeightWriter(Context context) {
        mDb = new VerticalHeightDbHelper(context).getWritableDatabase();
    }

    public void add(long timestamp, int verticalHeight) {
        ContentValues value = new ContentValues();
        value.put(VerticalHeightContract.VerticalHeightEntry.COLUMN_NAME_DATE, timestamp);
        value.put(VerticalHeightContract.VerticalHeightEntry.COLUMN_NAME_HEIGHT, verticalHeight);

        mDb.insert(VerticalHeightContract.VerticalHeightEntry.TABLE_NAME, null, value);
    }

    public void drop() {
        mDb.execSQL(VerticalHeightDbHelper.SQL_DROP_VERTICAL_HEIGHT);
    }
}
