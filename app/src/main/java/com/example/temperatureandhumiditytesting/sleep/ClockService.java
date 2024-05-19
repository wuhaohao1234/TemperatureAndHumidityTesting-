package com.example.temperatureandhumiditytesting.sleep;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.example.temperatureandhumiditytesting.R;
import com.example.temperatureandhumiditytesting.view.Couterdown;


/**
 * Service和Broadcast都行，此处选一个，service存活率更高1
 */
public class ClockService extends Service {
    private static final String TAG = "ClockService";
    public static final String EXTRA_EVENT_ID = "extra.event.id";
    public static final String EXTRA_EVENT_REMIND_TIME = "extra.event.remind.time";
    public static final String EXTRA_EVENT = "extra.event";
    private EventDao mEventDao = EventDao.getInstance();

    public ClockService() {
        Log.d(TAG, "ClockService: Constructor");
    }
    private MediaPlayer mediaPlayer;
    //震动
    private Vibrator mVibrator;
    private Event event;
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.clock);
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    }
    private void clock() {
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setIcon(R.mipmap.alarm);  //设置对话框图标
        alert.setTitle("学习提醒");
        alert.setMessage("开始学习了，不要偷懒啦！！");
        //添加确定按钮
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "知道啦", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                alert.dismiss();

            }
        });
        alert.show();  //显示对话框
    }
    private CountDownTimer start;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: onStartCommand");
        WakeLockUtil.wakeUpAndUnlock();

        postToClockActivity(getApplicationContext(), intent);
        mediaPlayer.start();
        start = new Couterdown(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public String toClock(long millis) {
                return super.toClock(millis);
            }

            @Override
            public void onFinish() {
                mediaPlayer.stop();
                mVibrator.cancel();
            }

        }.start();
        long[] pattern = new long[]{1500, 1000};
        mVibrator.vibrate(pattern, 0);
        return super.onStartCommand(intent, flags, startId);
    }

    private void postToClockActivity(Context context, Intent intent) {
        Intent i = new Intent();
        i.setClass(context, AlarmActvity.class);
        i.putExtra(EXTRA_EVENT_ID, intent.getIntExtra(EXTRA_EVENT_ID, -1));
        Event event = mEventDao.findById(intent.getIntExtra(EXTRA_EVENT_ID, -1));
        if (event == null) {
            return;
        }
        i.setAction("android.intent.action.MAIN");
        i.addCategory("android.intent.category.HOME");
        i.addCategory("android.intent.category.MONKEY");
        i.addCategory("android.intent.category.LAUNCHER");
        i.putExtra(EXTRA_EVENT_REMIND_TIME, intent.getStringExtra(EXTRA_EVENT_REMIND_TIME));
        i.putExtra(EXTRA_EVENT, event);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        mediaPlayer.stop();
        mVibrator.cancel();
        super.onDestroy();
    }
}
