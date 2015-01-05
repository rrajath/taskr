package com.example.rrajath.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by rrajath on 12/24/14.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_TASKS = "tasks";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TASK_NAME = "taskname";
    public static final String COLUMN_TASK_DESC = "taskdesc";
    public static final String COLUMN_TASK_DATE = "taskcreated";
    public static final String COLUMN_TASK_COMPLETE = "taskcomplete";

    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table " + TABLE_TASKS + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TASK_NAME + " text not null, "
            + COLUMN_TASK_DESC + " text null, "
            + COLUMN_TASK_DATE + " integer not null, "
            + COLUMN_TASK_COMPLETE + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }
}
