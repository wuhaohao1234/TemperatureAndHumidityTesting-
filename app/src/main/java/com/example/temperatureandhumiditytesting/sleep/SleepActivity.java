package com.example.temperatureandhumiditytesting.sleep;

import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.temperatureandhumiditytesting.R;
import com.example.temperatureandhumiditytesting.base.App;
import com.example.temperatureandhumiditytesting.base.BaseActivity;
import com.example.temperatureandhumiditytesting.utils.PrefUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.utils.ToastUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SleepActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.set)
    Button set;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    private EventManager mEventManager = EventManager.getInstance();
    private ClockManager mClockManager = ClockManager.getInstance();
    public static final String EXTRA_IS_ADD_EVENT = "extra.is.create.event";
    //从主屏进来的操作是不是添加操作
    private boolean isAddEvent;

    @Override
    protected int initContentView() {
        return R.layout.activity_sleep;
    }

    public static void forward(Context context) {
        Intent intent = new Intent(App.context, SleepActivity.class);
        intent.putExtra(SleepActivity.EXTRA_IS_ADD_EVENT, true);
        context.startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        int chooseColor = PrefUtils.getInt(App.context, "choose_color", 0);
        if (chooseColor == 0) {
            rlTitle.setBackgroundColor(getResources().getColor(R.color.text4));
            set.setBackgroundColor(getResources().getColor(R.color.text4));
        } else {
            rlTitle.setBackgroundColor(chooseColor);
            set.setBackgroundColor(chooseColor);
        }
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();
        tvTitle.setText("学习闹铃");
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        String[] array = getResources().getStringArray(R.array.content);
        String[] array1 = getResources().getStringArray(R.array.contentxianshi);
        String randomStr = array[new Random().nextInt(array.length)];
        String randomStr1 = array1[new Random().nextInt(array1.length)];
        Typeface tfRegular1 = Typeface.createFromAsset(getAssets(), randomStr);//初始化字体
        tvContent.setTypeface(tfRegular1);
        tvTime.setTypeface(tfRegular1);
        tvContent.setText(randomStr1);
        Button button1 = (Button) findViewById(R.id.set);  //获取"设置闹钟"按钮
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(SleepActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {
                        TimePickerDialog timePickerDialog = new TimePickerDialog(SleepActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String time = year + "-" + StringUtil.getLocalMonth(month) + "-" + StringUtil.getMultiNumber(dayOfMonth) + " " + StringUtil.getMultiNumber(hourOfDay) + ":" + StringUtil.getMultiNumber(minute);
                                tvTime.setText(time);
                            }
                        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
                        timePickerDialog.show();
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                dialog.show();
            }
        });
        //为设置闹钟按钮添加单击事件监听
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = tvTime.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    ToastUtils.showToast(SleepActivity.this, "您也没设置时间啊");
                    return;
                }
                //更新
                Event event = buildEvent();
                if (mEventManager.saveOrUpdate(event)) {
                    event.setmId(mEventManager.getLatestEventId());
                    //添加闹钟
                    mClockManager.addAlarm(buildIntent(event.getmId()), DateTimeUtil.str2Date(event.getmRemindTime()));
                    mEventManager.flushData();
                }
                finish();
                Toast.makeText(SleepActivity.this, "闹钟设置成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private PendingIntent buildIntent(int id) {
        Intent intent = new Intent();
        intent.putExtra(ClockReceiver.EXTRA_EVENT_ID, id);
        intent.setClass(this, ClockService.class);

        return PendingIntent.getService(this, 0x001, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static final String EXTRA_EVENT_DATA = "extra.event.data";

    @NonNull
    private Event buildEvent() {
        Event event = new Event();
        event.setmId(123456);
        event.setmCreatedTime(tvTime.getText().toString());
        event.setmRemindTime(tvTime.getText().toString());
        event.setmTitle("edTitle.getText().toString()");
        event.setmIsImportant(Constants.EventFlag.IMPORTANT);
        event.setmContent("fs");
        event.setmUpdatedTime(DateTimeUtil.dateToStr(new Date()));
        return event;
    }

    @OnClick(R.id.ll_common_back)
    public void onClick() {
        finish();
    }
}
