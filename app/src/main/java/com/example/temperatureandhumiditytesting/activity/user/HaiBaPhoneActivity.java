package com.example.temperatureandhumiditytesting.activity.user;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.example.temperatureandhumiditytesting.R;
import com.example.temperatureandhumiditytesting.base.App;
import com.example.temperatureandhumiditytesting.base.BaseActivity;
import com.example.temperatureandhumiditytesting.bean.TongJi;
import com.example.temperatureandhumiditytesting.bean.User;
import com.example.temperatureandhumiditytesting.utils.LocationManagerUtil1;
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


public class HaiBaPhoneActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tvWenDu)
    TextView tvWenDu;
    @BindView(R.id.tvLat)
    TextView tvLat;
    @BindView(R.id.tvLon)
    TextView tvLon;
    @BindView(R.id.tvLocation)
    TextView tvLocation;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.chart2)
    LineChart chart2;


    @Override
    protected int initContentView() {
        return R.layout.activity_haiba;
    }

    public static void forward(Context context) {
        Intent intent = new Intent(App.context, HaiBaPhoneActivity.class);
        context.startActivity(intent);
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

    }
    private void setData2(float range) {
        chart2.getDescription().setEnabled(false);
        // enable scaling and dragging
        chart2.setDragEnabled(true);
        chart2.setScaleEnabled(true);
        chart2.setDrawGridBackground(false);
        chart2.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart2.setPinchZoom(true);

        // set an alternative background color
        chart2.setBackgroundColor(Color.LTGRAY);
        // enable touch gestures
        chart2.setTouchEnabled(true);

        chart2.setDragDecelerationFrictionCoef(0.9f);
        chart2.animateX(1500);

        // get the legend (only possible after setting data)
        Legend l = chart2.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTextSize(11f);
        l.setTextColor(Color.WHITE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
//        l.setYOffset(11f);

        XAxis xAxis = chart2.getXAxis();
        xAxis.setTextSize(11f);
        xAxis.setTextColor(getResources().getColor(R.color.purple_500));
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);

        YAxis leftAxis = chart2.getAxisLeft();
        leftAxis.setTextColor(getResources().getColor(R.color.purple_200));
        leftAxis.setAxisMaximum(3000f);
        leftAxis.setAxisMinimum(-500f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);

        YAxis rightAxis = chart2.getAxisRight();
        rightAxis.setTextColor(Color.RED);
        rightAxis.setAxisMaximum(3000f);
        rightAxis.setAxisMinimum(-500f);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);
        ArrayList<Entry> values1 = new ArrayList<>();
        User currentUser = BmobUser.getCurrentUser(User.class);
        BmobQuery<TongJi> query = new BmobQuery<>();
        query.addWhereEqualTo("type", "haiba");
        query.addWhereEqualTo("user", currentUser);
        query.findObjects(new FindListener<TongJi>() {
            @Override
            public void done(List<TongJi> list, BmobException e) {
                for (int i = 0; i < list.size(); i++) {
                    values1.add(new Entry(i + 1, Float.parseFloat(list.get(i).getShuzhi())));
                }
                LineDataSet set1;

                if (chart2.getData() != null &&
                        chart2.getData().getDataSetCount() > 0) {
                    set1 = (LineDataSet) chart2.getData().getDataSetByIndex(0);
                    set1.setValues(values1);
                    chart2.getData().notifyDataChanged();
                    chart2.notifyDataSetChanged();
                } else {
                    // create a dataset and give it a type
                    set1 = new LineDataSet(values1, "海拔变化（单位：m）");

                    set1.setAxisDependency(YAxis.AxisDependency.LEFT);
                    set1.setColor(getResources().getColor(R.color.colorPrimary));
                    set1.setCircleColor(getResources().getColor(R.color.colorPrimary));
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
                    chart2.setData(data);
                }
            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();
        tvTitle.setText("海拔高度");
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        setData2(1);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            double altitude = location.getAltitude();
            Log.e("海拔", "" + altitude);
            tvWenDu.setText("当前设备海拔高度：" + altitude + "m");
            TongJi tongJi1 = new TongJi();
            tongJi1.setUser(BmobUser.getCurrentUser(User.class));
            tongJi1.setType("haiba");
            tongJi1.setShuzhi("" + altitude);
            tongJi1.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {

                }
            });

        }
        LocationManagerUtil1.getInstance().startSingleLocation(new LocationManagerUtil1.LocationManagerListener() {
            @Override
            public void onSuccessLocationListener(LocationManager manager, Location location, double longitude, double latitude, String locationAddr) {
                if (!TextUtils.isEmpty(locationAddr)) {
                    tvLon.setText("当前经度："+longitude);
                    tvLat.setText("当前纬度："+latitude);
                    tvLocation.setText("当前位置："+locationAddr);
                }

            }

            @Override
            public void onFailureLocationListener() {

            }
        });
    }

    @Override
    protected void initListener() {


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
