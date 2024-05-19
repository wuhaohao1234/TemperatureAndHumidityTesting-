package com.example.temperatureandhumiditytesting.sleep;


import android.database.Cursor;

public interface DBCallback<T> {
    /**
     * Gets a instance of T by cursor
     */
    T cursorToInstance(Cursor cursor);
}
