package com.example.temperatureandhumiditytesting.base;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import com.example.temperatureandhumiditytesting.bean.Word;
import com.example.temperatureandhumiditytesting.utils.SensitiveWordUtils;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class App extends Application {
    /**
     * 全局Context，方便引用
     */
    public static App application = null;
    public static Context context;
    public static Handler handler;
    public static int mNetWorkState;
    public static boolean is_debug = false;
    /* 文件缓存的目录 */
    public String mAppDir01;
    public String mPicturesDir01;
    public String mVoicesDir01;
    public String mVideosDir01;
    public String mFilesDir01;
    /* 文件缓存的目录 */
    public String mAppDir;
    public String mPicturesDir;
    public String mVoicesDir;
    public String mVideosDir;
    public String mFilesDir;


    public static final int DEFAULT_WORK_LENGTH = 25;
    public static final int DEFAULT_SHORT_BREAK = 5;
    public static final int DEFAULT_LONG_BREAK  = 20;
    public static final int DEFAULT_LONG_BREAK_FREQUENCY = 4; // 默认 4 次开始长休息

    // 场景
    public static final int SCENE_WORK = 0;
    public static final int SCENE_SHORT_BREAK = 1;
    public static final int SCENE_LONG_BREAK = 2;

    // 当前状态
    public static final int STATE_WAIT = 0;
    public static final int STATE_RUNNING = 1;
    public static final int STATE_PAUSE = 2;
    public static final int STATE_FINISH = 3;

    private long mStopTimeInFuture;
    private long mMillisInTotal;
    private long mMillisUntilFinished;

    private int mTimes;
    private int mState;

    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mState = STATE_WAIT;
        application = this;
        mContext = this;
        context = getApplicationContext();
        handler = new Handler();
        Bmob.initialize(this, "64fa63c1d511adf9b6ec85f39b144eba");
        // 初始化App目录

        initAppDir();
        initAppDirsecond();

        BmobQuery<Word> query = new BmobQuery<>();
        query.order("-updatedAt");
        //包含作者信息
        query.include("author");
        query.findObjects(new FindListener<Word>() {

            @Override
            public void done(List<Word> object, BmobException e) {
                if (e == null) {
                    if (object.size()>0) {
                        Set<String> hashSet = new HashSet<>();
                        for (int i = 0; i < object.size(); i++) {
                            hashSet.add(object.get(i).getWord());
                            SensitiveWordUtils.init(hashSet);
                        }
                    }else {
                        Set<String> hashSet = new HashSet<>();
                        hashSet.add("");
                        SensitiveWordUtils.init(hashSet);
                    }


                }
            }

        });
    }

    public void start() {
        setState(STATE_RUNNING);
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInTotal;
    }


    public void resume() {
        setState(STATE_RUNNING);
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisUntilFinished;
    }
    public int getState() {
        return mState;
    }

    public void setState(int state) {
        mState = state;
    }

    private void setTimes() {
        mTimes++; // 注意这里不能在 activity 中使用, 如果睡眠中就不能保证会运行
    }



    public long getMillisInTotal() {
        return mMillisInTotal;
    }

    public void setMillisUntilFinished(long millisUntilFinished) {
        mMillisUntilFinished = millisUntilFinished;
    }

    public long getMillisUntilFinished() {
        if (mState == STATE_RUNNING) {
            return mStopTimeInFuture - SystemClock.elapsedRealtime();
        }

        return mMillisUntilFinished;
    }

    private SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }
    public static Context getContext() {
        return mContext;
    }
    public static App getInstance() {
        return application;
    }

    private void initAppDirsecond() {
        File file = getExternalFilesDir(null);

        if (file == null) {
            return;
        }

        if (!file.exists()) {
            file.mkdirs();
        }
        mAppDir01 = file.getAbsolutePath();

        file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!file.exists()) {
            file.mkdirs();
        }
        mPicturesDir01 = file.getAbsolutePath();

        file = getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        if (!file.exists()) {
            file.mkdirs();
        }
        mVoicesDir01 = file.getAbsolutePath();

        file = getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        if (!file.exists()) {
            file.mkdirs();
        }
        mVideosDir01 = file.getAbsolutePath();

        file = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (!file.exists()) {
            file.mkdirs();
        }
        mFilesDir01 = file.getAbsolutePath();
    }

    private void initAppDir() {
        File file = getExternalFilesDir(null);
        if (file != null && !file.exists()) {
            file.mkdirs();
        }
        if (file != null) {
            mAppDir = file.getAbsolutePath();
        }

        file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (file != null && !file.exists()) {
            file.mkdirs();
        }
        if (file != null) {
            mPicturesDir = file.getAbsolutePath();
        }

        file = getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        if (file != null && !file.exists()) {
            file.mkdirs();
        }
        if (file != null) {
            mVoicesDir = file.getAbsolutePath();
        }

        file = getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        if (file != null && !file.exists()) {
            file.mkdirs();
        }
        if (file != null) {
            mVideosDir = file.getAbsolutePath();
        }

        file = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (file != null && !file.exists()) {
            file.mkdirs();
        }
        if (file != null) {
            mFilesDir = file.getAbsolutePath();
        }
    }



}
