package com.example.temperatureandhumiditytesting.activity.user;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.biansemao.widget.ThermometerView;
import com.example.temperatureandhumiditytesting.R;
import com.example.temperatureandhumiditytesting.base.App;
import com.example.temperatureandhumiditytesting.base.BaseActivity;
import com.example.temperatureandhumiditytesting.bean.TongJi;
import com.example.temperatureandhumiditytesting.bean.User;
import com.example.temperatureandhumiditytesting.utils.PrefUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


public class WenDuPhoneActivity extends BaseActivity {

    @BindView(R.id.chart3)
    LineChart chart3;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tvWenDu)
    TextView tvWenDu;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.tv_thermometer)
    ThermometerView thermometerTv;
    private SensorManager sensorManager;
    private Sensor temperatureSensor;
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float temperature = event.values[0];
            // 处理温度数据
            Log.e("Temperature", "当前温度: " + temperature + "°C");
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // 当精度改变时调用
        }
    };

    @Override
    protected int initContentView() {
        return R.layout.activity_wendu;
    }

    public static void forward(Context context) {
        Intent intent = new Intent(App.context, WenDuPhoneActivity.class);
        context.startActivity(intent);
    }
    private void setData3(float range) {
        chart3.getDescription().setEnabled(false);
        // enable scaling and dragging
        chart3.setDragEnabled(true);
        chart3.setScaleEnabled(true);
        chart3.setDrawGridBackground(false);
        chart3.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart3.setPinchZoom(true);

        // set an alternative background color
        chart3.setBackgroundColor(Color.LTGRAY);
        // enable touch gestures
        chart3.setTouchEnabled(true);

        chart3.setDragDecelerationFrictionCoef(0.9f);
        chart3.animateX(1500);

        // get the legend (only possible after setting data)
        Legend l = chart3.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTextSize(11f);
        l.setTextColor(Color.WHITE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
//        l.setYOffset(11f);

        XAxis xAxis = chart3.getXAxis();
        xAxis.setTextSize(11f);
        xAxis.setTextColor(getResources().getColor(R.color.indicator_color_3));
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);

        YAxis leftAxis = chart3.getAxisLeft();
        leftAxis.setTextColor(getResources().getColor(R.color.indicator_color_2));
        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);

        YAxis rightAxis = chart3.getAxisRight();
        rightAxis.setTextColor(Color.RED);
        rightAxis.setAxisMaximum(100f);
        rightAxis.setAxisMinimum(0f);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);
        ArrayList<Entry> values1 = new ArrayList<>();
        User currentUser = BmobUser.getCurrentUser(User.class);
        BmobQuery<TongJi> query = new BmobQuery<>();
        query.addWhereEqualTo("type", "shoujiwendu");
        query.addWhereEqualTo("user", currentUser);
        query.findObjects(new FindListener<TongJi>() {
            @Override
            public void done(List<TongJi> list, BmobException e) {
                for (int i = 0; i < list.size(); i++) {
                    values1.add(new Entry(i + 1, Float.parseFloat(list.get(i).getShuzhi())));
                }
                LineDataSet set1;

                if (chart3.getData() != null &&
                        chart3.getData().getDataSetCount() > 0) {
                    set1 = (LineDataSet) chart3.getData().getDataSetByIndex(0);
                    set1.setValues(values1);
                    chart3.getData().notifyDataChanged();
                    chart3.notifyDataSetChanged();
                } else {
                    // create a dataset and give it a type
                    set1 = new LineDataSet(values1, "手机温度变化（单位：℃）");

                    set1.setAxisDependency(YAxis.AxisDependency.LEFT);
                    set1.setColor(getResources().getColor(R.color.indicator_color_4));
                    set1.setCircleColor(getResources().getColor(R.color.indicator_color_4));
                    set1.setLineWidth(2f);
                    set1.setCircleRadius(3f);
                    set1.setFillAlpha(65);
                    set1.setFillColor(ColorTemplate.getHoloBlue());
                    set1.setHighLightColor(Color.rgb(244, 117, 117));
                    set1.setDrawCircleHole(false);


                    // create a data object with the data sets
                    LineData data = new LineData(set1);
                    data.setValueTextColor(Color.WHITE);
                    data.setValueTextSize(9f);

                    // set data
                    chart3.setData(data);
                }
            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
        int chooseColor = PrefUtils.getInt(App.context, "choose_color", 0);
        if (chooseColor == 0) {
            rlTitle.setBackgroundColor(getResources().getColor(R.color.text4));
        } else {
            rlTitle.setBackgroundColor(chooseColor);
        }
        sensorManager.registerListener(sensorEventListener, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();
        tvTitle.setText("设备温度");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        setData3(1);
    }

    @Override
    protected void initListener() {
        float temperature = getBatteryTemperature(this);
        thermometerTv.setValueAndStartAnim(temperature);
        tvWenDu.setText("当前设备温度："+temperature+"℃");
        TongJi tongJi1 = new TongJi();
        tongJi1.setUser(BmobUser.getCurrentUser(User.class));
        tongJi1.setType("shoujiwendu");
        tongJi1.setShuzhi(""+temperature);
        tongJi1.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {

            }
        });

    }

    private float getBatteryTemperature(Context context) {
        Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        float temp = (float) intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10;
        return temp;
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.ll_common_back)
    public void onClick() {
        finish();
    }
}
