package com.example.temperatureandhumiditytesting.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class MyDataBaseOpenHelper extends SQLiteOpenHelper{

    public MyDataBaseOpenHelper(Context context) {
        super(context, "citylist.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table city (_id integer primary key autoincrement, name varchar(100), id varchar(100))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
