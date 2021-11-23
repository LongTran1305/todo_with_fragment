package com.longtran.todo_java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TodoTest{
    private TodoHelper todoHelper;
    private SQLiteDatabase db;
    private static final String DATABASE_NAME = "TODO_DATABASE";
    private static final String TABLE_NAME = "TODO_TABLE";
    private static final String COLUMN_1 = "ID";
    private static final String COLUMN_2 = "TITLE";
    private static final String COLUMN_3 = "IS_CHECKED";

    @Before
    public void setUp(){
        todoHelper = new TodoHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        todoHelper.getWritableDatabase();
    }

    @After
    public void finish(){
        todoHelper.close();
    }

    @Test
    public void testPreConditions(){
        assertNotNull(todoHelper);
    }

    @Test
    public void testAddTodo() throws SQLException {
        db = todoHelper.getWritableDatabase();
        TodoModel todoModel = new TodoModel();
        ContentValues values = new ContentValues();
        values.put(COLUMN_2,todoModel.getTitle());
        values.put(COLUMN_3,0);
        db.insert(TABLE_NAME,null,values);
    }

    @Test
    public void testCreateDB() throws SQLException{
        db =todoHelper.getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , TITLE TEXT , IS_CHECKED INTEGER)");
    }

    @Test
    public void testDropDB() throws SQLException{
        db =todoHelper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        todoHelper.onCreate(db);
    }

    @Test
    public void testUpdateIsChecked() throws SQLException{
        db = todoHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_3,1);
        db.update(TABLE_NAME,values, "ID=?",new String[]{String.valueOf(1)});
    }

    @Test
    public void getALlTodo(){
        db = todoHelper.getWritableDatabase();
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

    }
}
