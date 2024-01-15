package com.example.app1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class db extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "TaskManager.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "my_library";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_Task = "TaskObject";
    private static final String COLUMN_DayOfWeek = "DayOfWeek";
    private static final String COLUMN_wtd = "whatToDo";


    public db(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_Task + " TEXT, " +
                COLUMN_DayOfWeek + " TEXT, " +
                COLUMN_wtd + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addTask(String Task, String dayOfWeek, String wtd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_Task, Task);
        cv.put(COLUMN_DayOfWeek, dayOfWeek);
        cv.put(COLUMN_wtd, wtd);

        return db.insert(TABLE_NAME, null, cv);
    }


    public Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
          return cursor;
    }

    public Cursor getTaskDetailsById(String taskId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
        return db.rawQuery(query, new String[]{taskId});
    }
    public boolean deleteTaskById(String taskId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COLUMN_ID + " = ?";
        String[] whereArgs = {taskId};

        int rowsDeleted = db.delete(TABLE_NAME, whereClause, whereArgs);

        Log.d("db", "Rows deleted: " + rowsDeleted);

        // If rowsDeleted is greater than 0, the task was deleted successfully
        return rowsDeleted > 0;
    }
    public Cursor getTasksByDay(String dayOfWeek) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_DayOfWeek + " = ?";
        return db.rawQuery(query, new String[]{dayOfWeek});
    }
}
