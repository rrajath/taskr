package com.example.rrajath.todo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.rrajath.todo.data.TaskItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by rrajath on 12/24/14.
 */
public class TasksDatasource {

    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private String[] allColumns = {
            DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_TASK_NAME,
            DatabaseHelper.COLUMN_TASK_DESC,
            DatabaseHelper.COLUMN_TASK_DATE,
            DatabaseHelper.COLUMN_TASK_COMPLETE
    };

    public TasksDatasource(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
    }

    public TaskItem createTask(TaskItem item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COLUMN_TASK_NAME, item.getTaskName());
        contentValues.put(DatabaseHelper.COLUMN_TASK_DESC, item.getTaskDescription());
        contentValues.put(DatabaseHelper.COLUMN_TASK_DATE, new Date().getTime());
        contentValues.put(DatabaseHelper.COLUMN_TASK_COMPLETE, "N");

        Cursor cursor;
        try {
            database.beginTransaction();
            long insertId = database.insert(DatabaseHelper.TABLE_TASKS, null, contentValues);

            cursor = database.query(DatabaseHelper.TABLE_TASKS, allColumns, DatabaseHelper.COLUMN_ID + "=" + insertId,
                    null, null, null, null);

            cursor.moveToFirst();
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
        TaskItem newTaskItem = cursorToTaskItem(cursor);
        cursor.close();
        return newTaskItem;
    }

    public void deleteTaskItem(TaskItem taskItem) {
        long id = taskItem.getId();
        System.out.println("Task to be deleted");
        try {
            database.beginTransaction();
            database.delete(DatabaseHelper.TABLE_TASKS, DatabaseHelper.COLUMN_ID + "=" + id, null);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    public void deleteCompletedTasks() {
        String sql = String.format("DELETE FROM %s WHERE %s = 'Y'",
                DatabaseHelper.TABLE_TASKS,
                DatabaseHelper.COLUMN_TASK_COMPLETE);

        try {
            database.beginTransaction();
            database.execSQL(sql);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    public int updateTaskItem(TaskItem taskItem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COLUMN_TASK_NAME, taskItem.getTaskName());
        contentValues.put(DatabaseHelper.COLUMN_TASK_DESC, taskItem.getTaskDescription());
        contentValues.put(DatabaseHelper.COLUMN_TASK_COMPLETE, taskItem.isTaskComplete() ? "Y" : "N");

        String selection = DatabaseHelper.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(taskItem.getId())};

        int rowsUpdated;
        try {
            database.beginTransaction();
            rowsUpdated = database.update(DatabaseHelper.TABLE_TASKS, contentValues, selection, selectionArgs);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }

        return rowsUpdated;
    }

    public List<TaskItem> getAllTasks() {
        List<TaskItem> tasks = new ArrayList<>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_TASKS, allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            TaskItem taskItem = cursorToTaskItem(cursor);
            tasks.add(taskItem);
            cursor.moveToNext();
        }

        cursor.close();
        return tasks;
    }

    private TaskItem cursorToTaskItem(Cursor cursor) {
        TaskItem taskItem = new TaskItem();
        if (cursor.getCount() > 0) {
            taskItem.setId(cursor.getInt(0));
            taskItem.setTaskName(cursor.getString(1));
            taskItem.setTaskDescription(cursor.getString(2));
            taskItem.setCreationTime(cursor.getLong(3));
            taskItem.setTaskComplete(cursor.getString(4).equalsIgnoreCase("Y"));
        }
        return taskItem;
    }
}
