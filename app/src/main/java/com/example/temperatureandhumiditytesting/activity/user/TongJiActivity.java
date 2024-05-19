package com.example.temperatureandhumiditytesting.activity.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.temperatureandhumiditytesting.R;
import com.example.temperatureandhumiditytesting.base.App;
import com.example.temperatureandhumiditytesting.base.BaseActivity;
import com.example.temperatureandhumiditytesting.bean.NumberBean;
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


public class TongJiActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.chart)
    LineChart chart;
    @BindView(R.id.chart1)
    LineChart chart1;
    @BindView(R.id.chart2)
    LineChart chart2;
    @BindView(R.id.chart3)
    LineChart chart3;


    @Override
    protected int initContentView() {
        return R.layout.activity_tongji;
    }

    public static void forward(Context context, NumberBean numberBean) {
        Intent intent = new Intent(App.context, TongJiActivity.class);
        intent.putExtra("number", numberBean);
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

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();
        tvTitle.setText("数据统计");
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        setData(1);
        setData1(1);
        setData2(1);
        setData3(1);
    }

    private void setData1(float range) {
        chart1.getDescription().setEnabled(false);
        // enable scaling and dragging
        chart1.setDragEnabled(true);
        chart1.setScaleEnabled(true);
        chart1.setDrawGridBackground(false);
        chart1.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart1.setPinchZoom(true);

        // set an alternative background color
        chart1.setBackgroundColor(Color.LTGRAY);
        // enable touch gestures
        chart1.setTouchEnabled(true);

        chart1.setDragDecelerationFrictionCoef(0.9f);
        chart1.animateX(1500);

        // get the legend (only possible after setting data)
        Legend l = chart1.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTextSize(11f);
        l.setTextColor(Color.WHITE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
//        l.setYOffset(11f);

        XAxis xAxis = chart1.getXAxis();
        xAxis.setTextSize(11f);
        xAxis.setTextColor(getResources().getColor(R.color.indicator_color_3));
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);

        YAxis leftAxis = chart1.getAxisLeft();
        leftAxis.setTextColor(getResources().getColor(R.color.indicator_color_2));
        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);

        YAxis rightAxis = chart1.getAxisRight();
        rightAxis.setTextColor(Color.RED);
        rightAxis.setAxisMaximum(100f);
        rightAxis.setAxisMinimum(0f);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);
        ArrayList<Entry> values1 = new ArrayList<>();
        User currentUser = BmobUser.getCurrentUser(User.class);
        BmobQuery<TongJi> query = new BmobQuery<>();
        query.addWhereEqualTo("type", "shidu");
        query.addWhereEqualTo("user", currentUser);
        query.findObjects(new FindListener<TongJi>() {
            @Override
            public void done(List<TongJi> list, BmobException e) {
                for (int i = 0; i < list.size(); i++) {
                    values1.add(new Entry(i + 1, Float.parseFloat(list.get(i).getShuzhi())));
                }
                LineDataSet set1;

                if (chart1.getData() != null &&
                        chart1.getData().getDataSetCount() > 0) {
                    set1 = (LineDataSet) chart1.getData().getDataSetByIndex(0);
                    set1.setValues(values1);
                    chart1.getData().notifyDataChanged();
                    chart1.notifyDataSetChanged();
                } else {
                    // create a dataset and give it a type
                    set1 = new LineDataSet(values1, "湿度变化（单位%）");

                    set1.setAxisDependency(YAxis.AxisDependency.LEFT);
                    set1.setColor(getResources().getColor(R.color.colorAccent));
                    set1.setCircleColor(getResources().getColor(R.color.colorAccent));
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
                    chart1.setData(data);
                }
            }
        });

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

    private void setData(float range) {
        chart.getDescription().setEnabled(false);
        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);
        chart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true);

        // set an alternative background color
        chart.setBackgroundColor(Color.LTGRAY);
        // enable touch gestures
        chart.setTouchEnabled(true);

        chart.setDragDecelerationFrictionCoef(0.9f);
        chart.animateX(1500);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTextSize(11f);
        l.setTextColor(Color.WHITE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
//        l.setYOffset(11f);

        XAxis xAxis = chart.getXAxis();
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.RED);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMaximum(40f);
        leftAxis.setAxisMinimum(-40f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setTextColor(Color.RED);
        rightAxis.setAxisMaximum(40f);
        rightAxis.setAxisMinimum(-40f);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);
        ArrayList<Entry> values1 = new ArrayList<>();
        User currentUser = BmobUser.getCurrentUser(User.class);
        BmobQuery<TongJi> query = new BmobQuery<>();
        query.addWhereEqualTo("type", "wendu");
        query.addWhereEqualTo("user", currentUser);
        query.findObjects(new FindListener<TongJi>() {
            @Override
            public void done(List<TongJi> list, BmobException e) {
                for (int i = 0; i < list.size(); i++) {
                    values1.add(new Entry(i + 1, Float.parseFloat(list.get(i).getShuzhi())));
                }
                LineDataSet set1;

                if (chart.getData() != null &&
                        chart.getData().getDataSetCount() > 0) {
                    set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
                    set1.setValues(values1);
                    chart.getData().notifyDataChanged();
                    chart.notifyDataSetChanged();
                } else {
                    // create a dataset and give it a type
                    set1 = new LineDataSet(values1, "温度变化（单位 ℃）");

                    set1.setAxisDependency(YAxis.AxisDependency.LEFT);
                    set1.setColor(ColorTemplate.getHoloBlue());
                    set1.setCircleColor(ColorTemplate.getHoloBlue());
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
                    chart.setData(data);
                }
            }
        });

    }


    @OnClick(R.id.ll_common_back)
    public void onClick() {
        finish();
    }
}
