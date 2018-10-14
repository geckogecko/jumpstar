package com.steinbacher.jumpstar.db;

import android.provider.BaseColumns;

/**
 * Created by Georg Steinbacher on 06.10.18.
 */

public final class PlanContract {
    private PlanContract() {}

    public static class PlanContractEntry implements BaseColumns {
        public static final String TABLE_NAME = "plans";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_PLAN_NANE = "name";
        public static final String COLUMN_NAME_PLAN_DESCRIPTION = "description";
        public static final String COLUMN_NAME_EXERCISES = "exercises";
    }
}
