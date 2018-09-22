package com.logistics.alucard.sqliteimgapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    public static final int VERSION = 2;
    public static final String Database_Name = "names.db";
    public static final String Table_Name = "mytable";
    public DatabaseHelper(Context context)
    {
        super(context, Database_Name, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.d(TAG, "onCreate: table created");
        db.execSQL("CREATE TABLE "+ Table_Name + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, IMAGE blob)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.d(TAG, "onUpgrade: table updated to new version");
        if(oldVersion < 2)
        db.execSQL("DROP TABLE IF EXISTS " +Table_Name);
        onCreate(db);

    }
    public boolean addData(String name, byte[] img)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("image", img);
        long result = db.insert(Table_Name,null,contentValues);
        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }

    }
    public Cursor getdata()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from " + Table_Name;
        Cursor data = db.rawQuery(query,null);
        return data;
    }
}
