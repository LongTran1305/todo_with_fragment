package com.longtran.todo_java;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TodoHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private static final String DATABASE_NAME = "TODO_DATABASE";
    private static final String TABLE_NAME = "TODO_TABLE";
    private static final String COLUMN_1 = "ID";
    private static final String COLUMN_2 = "TITLE";
    private static final String COLUMN_3 = "IS_CHECKED";

    public TodoHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , TITLE TEXT , IS_CHECKED INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertTodo(TodoModel todoModel){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_2,todoModel.getTitle());
        values.put(COLUMN_3,0);
        db.insert(TABLE_NAME,null,values);
    }

    public void updateIsChecked(int id,int isChecked){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_3,isChecked);
        db.update(TABLE_NAME,values, "ID=?",new String[]{String.valueOf(id)});
    }

    public List<TodoModel> getALlTodo(){
        db = this.getWritableDatabase();
        Cursor cursor = null;
        List<TodoModel> todoList = new ArrayList<>();
        db.beginTransaction();
        try{
            cursor = db.query(TABLE_NAME,null,null,null,null,null,null);
            if(cursor != null){
                if(cursor.moveToFirst()){
                    do{
                        TodoModel todo = new TodoModel();
                        todo.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_1)));
                        todo.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_2)));
                        todo.setIsChecked(cursor.getInt(cursor.getColumnIndex(COLUMN_3)));
                        todoList.add(todo);
                    }while (cursor.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            cursor.close();
        }
        return todoList;
    }

    public List<TodoModel> getTodoChecked(){
        db =this.getWritableDatabase();
        String where = COLUMN_3 + "=1";
        Cursor cursor = null;
        List<TodoModel> todoList = new ArrayList<>();
        db.beginTransaction();
        try{
            cursor = db.query(TABLE_NAME,null,where,null,null,null,null);
            if(cursor != null){
                if(cursor.moveToFirst()){
                    do{
                        TodoModel todo = new TodoModel();
                        todo.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_1)));
                        todo.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_2)));
                        todo.setIsChecked(cursor.getInt(cursor.getColumnIndex(COLUMN_3)));
                        todoList.add(todo);
                    }while (cursor.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            cursor.close();
        }
        return todoList;
    }

    public List<TodoModel> getTodoUnchecked(){
        db =this.getWritableDatabase();
        String where = COLUMN_3 + "=0";
        Cursor cursor = null;
        List<TodoModel> todoList = new ArrayList<>();
        db.beginTransaction();
        try{
            cursor = db.query(TABLE_NAME,null,where,null,null,null,null);
            if(cursor != null){
                if(cursor.moveToFirst()){
                    do{
                        TodoModel todo = new TodoModel();
                        todo.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_1)));
                        todo.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_2)));
                        todo.setIsChecked(cursor.getInt(cursor.getColumnIndex(COLUMN_3)));
                        todoList.add(todo);
                    }while (cursor.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            cursor.close();
        }
        return todoList;
    }

}
