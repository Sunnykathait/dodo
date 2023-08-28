package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseForTasks extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TaskDB";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TASKS = "tasks";
    private static final String COLUMN_TASK_NAME = "task_name";
    private static final String COLUMN_TASK_DESCRIPTION = "task_description";

    private static final String COLUMN_TASK_COMPLETED = "task_progress";

    public DatabaseForTasks(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS +
                "(" +
                COLUMN_TASK_NAME + " TEXT," +
                COLUMN_TASK_DESCRIPTION + " TEXT," +
                COLUMN_TASK_COMPLETED + " TEXT)";

        sqLiteDatabase.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(sqLiteDatabase);
    }

    void AddTaskInDB(TaskClass taskObject){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_NAME,taskObject.getTaskName());
        values.put(COLUMN_TASK_DESCRIPTION,taskObject.getTaskDescription());
        values.put(COLUMN_TASK_COMPLETED,taskObject.getIsCompleted());

        db.insert(TABLE_TASKS,null,values);

        db.close();
    }

    public ArrayList<TaskClass> showTasks(){
        ArrayList<TaskClass> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            String[] projection = {
                    COLUMN_TASK_NAME,
                    COLUMN_TASK_DESCRIPTION,
                    COLUMN_TASK_COMPLETED
            };


            Cursor cursor = db.query(
                    TABLE_TASKS,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null
                    );

            while (cursor.moveToNext()){
                String taskName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK_NAME));
                String taskDescription = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK_DESCRIPTION));
                String taskProgress = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK_COMPLETED));

                TaskClass taskClass = new TaskClass(taskName,taskDescription,taskProgress);
                list.add(taskClass);

            }

            cursor.close();

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            db.close();
        }

        return list;

    }

    public void deleteTask(String taskName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS,COLUMN_TASK_NAME+" = ?",new String[]{taskName});
        db.close();
    }

    public void updateTask(String taskName, String newTasName, String newTaskDesc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_NAME,newTasName);
        values.put(COLUMN_TASK_DESCRIPTION, newTaskDesc);

        db.update(TABLE_TASKS, values, COLUMN_TASK_NAME + " = ?", new String[]{taskName});
        db.close();
    }

    public void updateProgress(String taskName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_COMPLETED,"Completed");
        db.update(TABLE_TASKS, values, COLUMN_TASK_NAME + " = ?", new String[]{taskName});
        db.close();
    }

    public void undoProgress(String taskName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_COMPLETED,"Pending");
        db.update(TABLE_TASKS, values, COLUMN_TASK_NAME + " = ?", new String[]{taskName});
        db.close();
    }


}
