package com.example.temperatureandhumiditytesting.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.temperatureandhumiditytesting.bean.CityListBean;

import java.util.ArrayList;
import java.util.List;


public class CityDao {

    private final MyDataBaseOpenHelper helper;

    /**
     * 只有一个有参的构造方法,要求必须传入上下文
     *
     * @param context
     */
    public CityDao(Context context) {
        helper = new MyDataBaseOpenHelper(context);

    }


    /**
     * 添加
     */
    public void add(String id, String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into city (id,name) values (?,?)",
                new Object[]{id, name});
        db.close();//释放资源
    }



    /**
     * 删除
     *
     */
    public void delete(String id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from city where id=?", new Object[]{id});
        db.close();//释放资源
    }

    /**
     * 获取
     *
     * @return
     */
    public List<CityListBean> findAll() {
        List<CityListBean> result = new ArrayList<CityListBean>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from city", null);
        CityListBean que;
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            que = new CityListBean();
            que.setId(id);
            que.setName(name);
            result.add(que);
        }
        cursor.close();
        db.close();
        return result;
    }


    @SuppressLint("Range")
    public boolean isAdd(String qid,String name) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String name1 = "xyc";
        Cursor cursor = db.rawQuery("select * from city where id=?", new String[]{qid});
        if (cursor.moveToNext()) {
            name1 = cursor.getString(cursor.getColumnIndex("name"));
        }
        if (name1.equals(name)) {
            return true;
        }
            return false;

    }

}
