package com.fdev.todoapps.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.fdev.todoapps.R;
import com.fdev.todoapps.model.ToDo;
import com.fdev.todoapps.util.DatabaseUtil;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {


    private final Context context;

    public DatabaseHandler(@Nullable Context context) {
        super(context, DatabaseUtil.DATABASE_NAME, null, DatabaseUtil.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("Cinta");

        String CREATE_TODO_TABLE = "CREATE TABLE " + DatabaseUtil.TABLE_NAME + "("
                + DatabaseUtil.KEY_ID + " INTEGER PRIMARY KEY,"
                + DatabaseUtil.KEY_TITLE + " TEXT,"
                + DatabaseUtil.KEY_ADDED_DATE + " LONG,"
                + DatabaseUtil.KEY_DEADLINE_DATE + " LONG,"
                + DatabaseUtil.KEY_HOUR + " INTEGER,"
                + DatabaseUtil.KEY_MIN + " INTEGER,"
                + DatabaseUtil.KEY_URGENT_LEVEL + " TEXT"
                + ")";

        String CREATE_INDEXT_DATE = "CREATE INDEX " + DatabaseUtil.INDEXT_DATE + " ON "
                + DatabaseUtil.TABLE_NAME + " (" + DatabaseUtil.KEY_DEADLINE_DATE + ")";
        Log.d("Database Made", "Database");

        db.execSQL(CREATE_TODO_TABLE);
        db.execSQL(CREATE_INDEXT_DATE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseUtil.TABLE_NAME);
        Log.d("Database Made", "Database");


        //Create a Table Again
        onCreate(db);
    }



    public boolean addToDo(ToDo todo){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseUtil.KEY_TITLE,todo.getTitle());
        values.put(DatabaseUtil.KEY_ADDED_DATE,System.currentTimeMillis());
        values.put(DatabaseUtil.KEY_DEADLINE_DATE,todo.getDeadlineDate());
        values.put(DatabaseUtil.KEY_HOUR,todo.getHour());
        values.put(DatabaseUtil.KEY_MIN,todo.getMinute());
        values.put(DatabaseUtil.KEY_URGENT_LEVEL,todo.getUrgentLevel());

        Long addedCheck= Long.valueOf(-1);
        try{
            addedCheck = db.insert(DatabaseUtil.TABLE_NAME,null,values);
        }catch (Exception e){
            e.printStackTrace();

        }finally {
            db.close();
            if(addedCheck==-1){
                return false;
            }
            return true;
        }

    }

    public ToDo getToDo(int id){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DatabaseUtil.TABLE_NAME,new String[]{DatabaseUtil.KEY_ID,
                        DatabaseUtil.KEY_TITLE,
                        DatabaseUtil.KEY_ADDED_DATE, DatabaseUtil.KEY_DEADLINE_DATE,
                        DatabaseUtil.KEY_HOUR , DatabaseUtil.KEY_MIN,
                        DatabaseUtil.KEY_URGENT_LEVEL}
                ,DatabaseUtil.KEY_ID + "=?" ,
                new String[]{String.valueOf(id)} ,
                null , null , null);
        if(cursor != null){
            cursor.moveToFirst();

            ToDo todo = new ToDo();
            todo.setId(cursor.getInt(0));
            todo.setTitle(cursor.getString(1));
            todo.setAddedDate(cursor.getLong(2));
            todo.setDeadlineDate(cursor.getLong(3));
            todo.setHour(cursor.getInt(4));
            todo.setMinute(cursor.getInt(5));
            todo.setUrgentLevel(cursor.getString(6));


            return todo;
        }else{
            return null;
        }
    }

    public List<ToDo> getToDosByDate(long date){
        List<ToDo> toDoList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DatabaseUtil.TABLE_NAME,new String[]{DatabaseUtil.KEY_ID,
                        DatabaseUtil.KEY_TITLE,
                        DatabaseUtil.KEY_ADDED_DATE, DatabaseUtil.KEY_DEADLINE_DATE,
                        DatabaseUtil.KEY_HOUR , DatabaseUtil.KEY_MIN,
                        DatabaseUtil.KEY_URGENT_LEVEL}
                ,DatabaseUtil.KEY_DEADLINE_DATE + "=?" ,
                new String[]{String.valueOf(date)} ,
                null , null , DatabaseUtil.KEY_ADDED_DATE);

        if(cursor.moveToFirst()){
            do{
                try{

                    ToDo todo = new ToDo();
                    todo.setId(cursor.getInt(0));
                    todo.setTitle(cursor.getString(1));
                    todo.setAddedDate(cursor.getLong(2));
                    todo.setDeadlineDate(cursor.getLong(3));
                    todo.setHour(cursor.getInt(4));
                    todo.setMinute(cursor.getInt(5));
                    todo.setUrgentLevel(cursor.getString(6));


                    toDoList.add(todo);
                }catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
            }while(cursor.moveToNext());
        }
        return toDoList;

    }

    public List<ToDo> getAllToDos(){
        List<ToDo> toDoList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = "SELECT * FROM " + DatabaseUtil.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll,null);

        if(cursor.moveToFirst()){
            do{
                try{

                    ToDo todo = new ToDo();
                    todo.setId(cursor.getInt(0));
                    todo.setTitle(cursor.getString(1));
                    todo.setAddedDate(cursor.getLong(2));
                    todo.setDeadlineDate(cursor.getLong(3));
                    todo.setHour(cursor.getInt(4));
                    todo.setMinute(cursor.getInt(5));
                    todo.setUrgentLevel(cursor.getString(6));


                    toDoList.add(todo);
                }catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
            }while(cursor.moveToNext());
        }
        return toDoList;

    }

    public int updateToDo(ToDo todo){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseUtil.KEY_TITLE,todo.getTitle());
        values.put(DatabaseUtil.KEY_ADDED_DATE,System.currentTimeMillis());
        values.put(DatabaseUtil.KEY_DEADLINE_DATE,todo.getDeadlineDate());
        values.put(DatabaseUtil.KEY_HOUR,todo.getHour());
        values.put(DatabaseUtil.KEY_MIN,todo.getMinute());
        values.put(DatabaseUtil.KEY_URGENT_LEVEL,todo.getUrgentLevel());

        try{
            return db.update(DatabaseUtil.TABLE_NAME,values,DatabaseUtil.KEY_ID + "=?" ,
                    new String[]{String.valueOf(todo.getId())});
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public void deleteToDo(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(DatabaseUtil.TABLE_NAME, DatabaseUtil.KEY_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }
}
