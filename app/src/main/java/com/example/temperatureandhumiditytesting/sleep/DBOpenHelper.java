package com.example.temperatureandhumiditytesting.sleep;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.temperatureandhumiditytesting.base.App;

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = DBOpenHelper.class.getSimpleName();
    private static final int VERSION = 1;
    private static final String DB_NAME = "gText.db";

    public DBOpenHelper() {
        super(App.getContext(), DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*第一次初始化app，创建表结构 */
        db.execSQL("CREATE TABLE IF NOT EXISTS " + ColumnContacts.EVENT_TABLE_NAME + "( "
                    + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ColumnContacts.EVENT_TITLE_COLUMN + " text, "
                    + ColumnContacts.EVENT_CONTENT_COLUMN + " text, "
                    + ColumnContacts.EVENT_CREATED_TIME_COLUMN + " datetime, "
                    + ColumnContacts.EVENT_UPDATED_TIME_COLUMN + " datetime, "
                    + ColumnContacts.EVENT_REMIND_TIME_COLUMN + " datetime, "
                    + ColumnContacts.EVENT_IS_IMPORTANT_COLUMN + " INTEGER, "
                    + ColumnContacts.EVENT_IS_CLOCKED + " INTEGER"
        + ")");

        String sql = "INSERT INTO " + ColumnContacts.EVENT_TABLE_NAME + " VALUES(NULL, ?, ?, ?, ?, ?, ?, ?)";
        db.beginTransaction();
        db.execSQL(sql, new Object[]{"guoyr->gText",
                "gText是一个小巧方便带有闹铃功能的记事本app，主要使用butterknife和recycleview，clockmanager构建\n",
                "2021-03-25 17:28:23",
                "2021-03-25 17:28",
                "2021-03-25 17:28",
                0, 0});
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    /*版本更新时会执行该方法，如版本变更 => 2  */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Nothing to do
    }
}
