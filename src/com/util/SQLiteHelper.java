package com.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {

    public final static int DATABASE_VERSION = 1;
    public final static String DATABASE_NAME = "scc.db";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

    /*public static void dropDataBase(Context context){
        //File  mDatabaseFile = context.getDatabasePath(DATABASE_NAME);
		//context.deleteDatabase("scc.db");
		//SQLiteDatabase.deleteDatabase(new File(mDatabaseFile.getParent(),"ESS.db"));
		//SQLiteDatabase.deleteDatabase(new File(mDatabaseFile.getParent(),"ESS.db-journal"));
	}*/

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
