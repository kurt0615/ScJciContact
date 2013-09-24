package com.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.HashMap;

public class SQLiteQueryHelper {

    public SQLiteDatabase sqliteDB = null;
    private Context context = null;

    public SQLiteQueryHelper(Context context) {
        this.context = context;
    }

    public void close() {
        if (sqliteDB != null && this.sqliteDB.isOpen()) {
            this.sqliteDB.close();
        }
    }

    public void open() {
        if (sqliteDB == null || !sqliteDB.isOpen()) {
            this.sqliteDB = new SQLiteHelper(context).getWritableDatabase();
        }
    }

	/*public void dropDB(){
        SQLiteHelper.dropDataBase(context);
	}*/

    //select
    public void selectOperateView(String statement, String[] condition) {
        Cursor cursor = null;
        open();
        if(this.sqliteDB != null && this.sqliteDB.isOpen()){
            cursor =  this.sqliteDB.rawQuery(statement,condition);
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
        if(this.sqliteDB != null && this.sqliteDB.isOpen()){
            c =  this.sqliteDB.rawQuery(statement,condition);
        }
        return c;
    }

    //insert、update
    public void transactionOperate(String statement, Object[] condition) {
        if(this.sqliteDB != null && this.sqliteDB.isOpen()){
            this.sqliteDB.beginTransaction();
            try {
                this.sqliteDB.execSQL(statement, condition);
                this.sqliteDB.setTransactionSuccessful();
            } catch (SQLiteException e) {
                Log.i("transactionOperateError",e.toString());
            } finally {
                this.sqliteDB.endTransaction();
            }
        }
    }

    //delete
    public void transactionDeleteOperate(String table, String whereClause, String[] whereArgs) {
        if(this.sqliteDB != null && this.sqliteDB.isOpen()){
            this.sqliteDB.beginTransaction();
            try {
                this.sqliteDB.delete(table, null,null);
                this.sqliteDB.setTransactionSuccessful();
            } catch (SQLiteException e) {
                Log.i("transactionDeleteOperate",e.toString());
            } finally {
                this.sqliteDB.endTransaction();
            }
        }
    }
}
