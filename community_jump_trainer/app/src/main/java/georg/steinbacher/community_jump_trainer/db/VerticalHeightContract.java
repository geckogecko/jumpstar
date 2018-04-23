package georg.steinbacher.community_jump_trainer.db;

import android.provider.BaseColumns;

/**
 * Created by Georg Steinbacher on 23.04.18.
 */

public final class VerticalHeightContract {
    private VerticalHeightContract() {}

    public static class VerticalHeightEntry implements BaseColumns {
        public static final String TABLE_NAME = "vertical_height_entry";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_HEIGHT = "height"; // in cm
    }
}
