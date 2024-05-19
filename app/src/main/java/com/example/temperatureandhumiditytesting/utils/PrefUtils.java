package com.example.temperatureandhumiditytesting.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.temperatureandhumiditytesting.base.App;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YJF on 2020/3/18.
 */
public class PrefUtils {

    private static final String SHARE_PREFS_NAME = "tata";

    public static void putBoolean(Context ctx, String key, boolean value) {
        SharedPreferences pref =  ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        pref.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context ctx, String key,
                                     boolean defaultValue) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        return pref.getBoolean(key, defaultValue);
    }
    public static void putString(Context ctx, String key, String value) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_MULTI_PROCESS);

        pref.edit().putString(key, value).commit();
    }

    public static String getString(Context ctx, String key, String defaultValue) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_MULTI_PROCESS);

        return pref.getString(key, defaultValue);
    }

    public static void putInt(Context ctx, String key, int value) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        pref.edit().putInt(key, value).commit();
    }

    public static int getInt(Context ctx, String key, int defaultValue) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        return pref.getInt(key, defaultValue);
    }

    public static String getParameter(String parameter) {
        String string = PrefUtils.getString(App.context, parameter, "");
        return string;
    }
    public static void putParameter(String key,String value) {
        PrefUtils.putString(App.context, key,value);
    }


    /**
     * 存储List<String>
     *
     * @param context
     * @param key
     *            List<String>对应的key
     * @param strList
     *            对应需要存储的List<String>
     */
    public static void putStrListValue(Context context, String key,
                                       List<String> strList) {
        if (null == strList) {
            return;
        }
        // 保存之前先清理已经存在的数据，保证数据的唯一性
        removeStrList(context, key);
        int size = strList.size();
        putInt(context, key + "size", size);
        for (int i = 0; i < size; i++) {
            putString(context, key + i, strList.get(i));
        }
    }

    /**
     * 取出List<String>
     *
     * @param context
     * @param key
     *            List<String> 对应的key
     * @return List<String>
     */
    public static List<String> getStrListValue(Context context, String key) {
        List<String> strList = new ArrayList<String>();
        int size = getInt(context, key + "size", 0);
        for (int i = 0; i < size; i++) {
            strList.add(getString(context, key + i, null));
        }
        return strList;
    }
    public static void removeStrList(Context context, String key) {
        int size = getInt(context, key + "size", 0);
        if (0 == size) {
            return;
        }
        remove(context, key + "size");
        for (int i = 0; i < size; i++) {
            remove(context, key + i);
        }
    }
    /**
     * 清空对应key数据
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences.Editor sp = context.getSharedPreferences(SHARE_PREFS_NAME, Context.MODE_PRIVATE)
                .edit();
        sp.remove(key);
        sp.commit();
    }

    /**
     * 清空所有数据
     *
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences.Editor sp = context.getSharedPreferences(SHARE_PREFS_NAME, Context.MODE_PRIVATE).edit();
        sp.clear();
        sp.commit();
    }


    /**
     * 用于保存集合
     *
     * @param key  key
     * @param list 集合数据
     * @return 保存结果
     */
    public static <T> boolean putListData( String key, ArrayList<T> list) {
        boolean result;
        SharedPreferences sp = App.context.getSharedPreferences(SHARE_PREFS_NAME, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sp.edit();
        JsonArray array = new JsonArray();
        if (list.size() <= 0) {
            editor.putString(key, array.toString());
            editor.apply();
            return false;
        }
        String type = list.get(0).getClass().getSimpleName();
        try {
            switch (type) {
                case "Boolean":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Boolean) list.get(i));
                    }
                    break;
                case "Long":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Long) list.get(i));
                    }
                    break;
                case "Float":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Float) list.get(i));
                    }
                    break;
                case "String":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((String) list.get(i));
                    }
                    break;
                case "Integer":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Integer) list.get(i));
                    }
                    break;
                default:
                    Gson gson = new Gson();
                    for (int i = 0; i < list.size(); i++) {
                        JsonElement obj = gson.toJsonTree(list.get(i));
                        array.add(obj);
                    }
                    break;
            }
            editor.putString(key, array.toString());
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        editor.apply();
        return result;
    }

    /**
     * 获取保存的List
     *
     * @param key key
     * @return 对应的Lis集合
     */
    public static <T> ArrayList<T> getListData(String key, Class<T> cls) {
        ArrayList<T> list = new ArrayList<>();
        SharedPreferences sp = App.context.getSharedPreferences(SHARE_PREFS_NAME, Context.MODE_MULTI_PROCESS);
        String json = sp.getString(key, "");
        if (!json.equals("") && json.length() > 0) {
            Gson gson = new Gson();
            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for (JsonElement elem : array) {
                list.add(gson.fromJson(elem, cls));
            }
        }
        return list;
    }

}
