package com.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.HashMap;

public class SQLiteQueryHelper {

    private  SQLiteDatabase mSqliteDB = null;
    private  SQLiteHelper mSQLiteHelper = null;
    private  Context mContext = null;

    public SQLiteQueryHelper(Context context){
        this.mSQLiteHelper = SQLiteHelper.getInstance(context);
        this.mContext = context;
        try{
            this.mSqliteDB = this.mSQLiteHelper.getWritableDatabase();
        }catch(Exception e){
            Log.i("SQLiteQueryHelperInitError",e.toString());
        }
    }

    //select
    public void selectOperateView(String statement, String[] condition) {
        Cursor cursor = null;
        if(this.mSqliteDB != null){
            cursor =  this.mSqliteDB.rawQuery(statement,condition);
        }

        if (cursor != null) {
            try {
                if (cursor.getCount() > 0) {
                    int userId = cursor.getColumnIndex("USER_ID");
                    int name = cursor.getColumnIndex("NAME");
                    int phone = cursor.getColumnIndex("PHONE");
                    int addr = cursor.getColumnIndex("ADDRESS");
                    int mail = cursor.getColumnIndex("MAIL");
                    int date = cursor.getColumnIndex("DATE");
                    int site = cursor.getColumnIndex("WEBSITE");
                    int other = cursor.getColumnIndex("OTHER");
                    int avatarPath = cursor.getColumnIndex("AVATARPATH");

                    cursor.moveToFirst();
                    do {
                        Log.i("SVuserId", cursor.isNull(userId)?"NULL":cursor.getString(userId));
                        Log.i("SVname", cursor.isNull(name)?"NULL":cursor.getString(name));
                        Log.i("SVphone", cursor.isNull(phone)?"NULL":cursor.getString(phone));
                        Log.i("SVaddr", cursor.isNull(addr)?"NULL":cursor.getString(addr));
                        Log.i("SVdate", cursor.isNull(date)?"NULL":cursor.getString(date));
                        Log.i("SVmail", cursor.isNull(mail)?"NULL":cursor.getString(mail));
                        Log.i("SVsite", cursor.isNull(site)?"NULL":cursor.getString(site));
                        Log.i("SVother", cursor.isNull(other)?"NULL":cursor.getString(other));
                        Log.i("SVavatarPath", cursor.isNull(avatarPath)?"NULL":cursor.getString(avatarPath));
                    } while (cursor.moveToNext());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
    }

    //select
    public Cursor selectOperate(String statement, String[] condition) {
        Cursor c = null;
        if(this.mSqliteDB != null){
            c =  this.mSqliteDB.rawQuery(statement,condition);
        }
        return c;
    }

    //insert、update
    public void transactionOperate(String statement, Object[] condition) {
        if(this.mSqliteDB != null){
            this.mSqliteDB.beginTransaction();
            try {
                this.mSqliteDB.execSQL(statement, condition);
                this.mSqliteDB.setTransactionSuccessful();
            } catch (SQLiteException e) {
                Log.i("transactionOperateError",e.toString());
            } finally {
                this.mSqliteDB.endTransaction();
            }
        }
    }

    //delete
    public void transactionDeleteOperate(String table, String whereClause, String[] whereArgs) {
        if(this.mSqliteDB != null){
            this.mSqliteDB.beginTransaction();
            try {
                this.mSqliteDB.delete(table, null,null);
                this.mSqliteDB.setTransactionSuccessful();
            } catch (SQLiteException e) {
                Log.i("transactionDeleteOperate",e.toString());
            } finally {
                this.mSqliteDB.endTransaction();
            }
        }
    }
}
