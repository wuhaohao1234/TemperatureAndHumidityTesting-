package com.example.temperatureandhumiditytesting.sleep;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.temperatureandhumiditytesting.R;


//闹铃提醒内容
public class AlarmActvity extends AppCompatActivity {
    //闹铃
    private MediaPlayer mediaPlayer;
    //震动
    private Vibrator mVibrator;
    private Event event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化数据
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.clock);
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        Intent intent = getIntent();
        //接受对象数据
        event = getIntent().getParcelableExtra(ClockService.EXTRA_EVENT);
        if (event == null) {
            finish();
        }

    }
    @Override
    protected void onStart() {
        super.onStart();
        clock();
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        clock();
    }

    private void clock() {
        mediaPlayer.start();
        long[] pattern = new long[]{1500, 1000};
        mVibrator.vibrate(pattern, 0);
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setIcon(R.mipmap.alarm);  //设置对话框图标
        alert.setTitle("学习提醒");
        alert.setMessage("开始学习了，不要偷懒啦！！");
        //添加确定按钮
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "知道啦", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mediaPlayer.stop();
                mVibrator.cancel();
                alert.dismiss();
                finish();
            }
        });
        alert.show();  //显示对话框
    }
}
