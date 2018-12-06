package com.example.ppolova.defender;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ScoreManager extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "ScoreTable";
    private static final String COL_NAME = "Name";
    private static final String COL_SCORE = "Score";
    private static final String COL_ID = "ID";

    public ScoreManager(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAME + " TEXT, " + COL_SCORE + " INT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String name, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        System.out.println("v manageru pridavam " + name + " " + score);
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_SCORE, score);

        Log.d("TABLE", "adddata");

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) return false;
        else return true;
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}
