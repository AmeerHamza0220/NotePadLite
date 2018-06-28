package com.example.malikameer.notepadlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLDatabase extends SQLiteOpenHelper {
    private static String mDatabaseName="mNote.db";
    private String tableName="Notes";
    SQLiteDatabase db=this.getWritableDatabase();

    SQLDatabase(Context context) {
        super(context, mDatabaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+tableName+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, Title TEXT, Description TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void saveData(String mTitle,String mDescription){
        ContentValues contentValues=new ContentValues();
        contentValues.put("Title",mTitle);
        contentValues.put("Description",mDescription);
        db.insert(tableName,null,contentValues);
    }

    public Cursor retrieveData(){
        String query="SELECT * FROM "+tableName;
        return db.rawQuery(query,null);
    }
    public void deleteRecord(int id){
        db.delete(tableName, "ID ='"+ id+"'", null);
    }
}

