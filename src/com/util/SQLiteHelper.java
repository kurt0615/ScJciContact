package com.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {

    public final static int DATABASE_VERSION = 1;
    public final static String DATABASE_NAME = "scc.db";
    private static SQLiteHelper instance = null;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized SQLiteHelper getInstance(Context context){
        if(instance == null){
            instance = new SQLiteHelper(context);
        }
        return instance;
    }

    private static void createContactTable(SQLiteDatabase db) {
        String TABLE_CREATE = "create table if not exists CONTACT ("
                // + "_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "USER_ID TEXT PRIMARY KEY,"
                + "NAME TEXT,"
                + "COUPLE TEXT,"
                + "JOB TEXT,"
                + "AVATARPATH TEXT,"
                + "PHONE TEXT,"
                + "ADDRESS TEXT,"
                + "MAIL TEXT,"
                + "DATE TEXT,"
                + "WEBSITE TEXT,"
                + "OTHER TEXT"
                + ");";

        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("onCreate", "OO");
        createContactTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        Log.i("onUpgrade", "OO");

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        super.onOpen(db);
        Log.i("onOpen", "OO");
    }
}
