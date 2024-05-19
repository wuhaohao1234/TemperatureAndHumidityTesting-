package com.example.temperatureandhumiditytesting.activity.user;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.baronzhang.android.widget.IndicatorView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.temperatureandhumiditytesting.R;
import com.example.temperatureandhumiditytesting.activity.common.CityActivity;
import com.example.temperatureandhumiditytesting.base.App;
import com.example.temperatureandhumiditytesting.base.BaseActivity;
import com.example.temperatureandhumiditytesting.bean.AirBean;
import com.example.temperatureandhumiditytesting.bean.LifeZhiShuBean;
import com.example.temperatureandhumiditytesting.bean.LiveBean;
import com.example.temperatureandhumiditytesting.bean.TongJi;
import com.example.temperatureandhumiditytesting.bean.User;
import com.example.temperatureandhumiditytesting.bean.Weather3DBean;
import com.example.temperatureandhumiditytesting.myhttp.MyHttpUtils;
import com.example.temperatureandhumiditytesting.myhttp.RequestCallBack;
import com.example.temperatureandhumiditytesting.utils.AppUtils;
import com.example.temperatureandhumiditytesting.utils.CityCodeManager;
import com.example.temperatureandhumiditytesting.utils.LocationManagerUtil;
import com.example.temperatureandhumiditytesting.utils.PrefUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class WeatherActivity extends BaseActivity {

    @BindView(R.id.publish_time_text_view)
    TextView publishTimeTextView;
    @BindView(R.id.temp_text_view)
    TextView tempTextView;
    @BindView(R.id.weather_text_view)
    TextView weatherTextView;
    @BindView(R.id.weather_icon_image_view)
    ImageView weatherIconImageView;
    @BindView(R.id.tvLocation)
    TextView tvLocation;
    @BindView(R.id.backdrop)
    RelativeLayout backdrop;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tvOne)
    TextView tvOne;
    @BindView(R.id.tvTwo)
    TextView tvTwo;
    @BindView(R.id.tvThree)
    TextView tvThree;
    @BindView(R.id.tvFour)
    TextView tvFour;
    @BindView(R.id.tvFive)
    TextView tvFive;
    @BindView(R.id.tvSix)
    TextView tvSix;
    private DetailAdapter detailAdapter;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.tv_aqi)
    TextView tvAqi;
    @BindView(R.id.tv_quality)
    TextView tvQuality;
    @BindView(R.id.tvShaiXuan)
    TextView tvShaiXuan;
    @BindView(R.id.tv_pm)
    TextView tv_pm;
    @BindView(R.id.indicator_view_aqi)
    IndicatorView indicatorViewAqi;
    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView1;
    private LiveAdapter liveAdapter;

    @Override
    protected int initContentView() {
        return R.layout.activity_weather;
    }

    public static void forward(Context context) {
        Intent intent = new Intent(App.context, WeatherActivity.class);
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
        getInfo();
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();
        recyclerView.setLayoutManager(new LinearLayoutManager(WeatherActivity.this));
        detailAdapter = new DetailAdapter(R.layout.item_forecast);
        recyclerView.setAdapter(detailAdapter);

        recyclerView1.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        liveAdapter = new LiveAdapter(R.layout.item_life_index);
        recyclerView1.setAdapter(liveAdapter);
        tvTitle.setText("天气查询");
    }

    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getInfo();
                refreshLayout.finishRefresh();
            }
        });
        tvShaiXuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityActivity.forward(WeatherActivity.this);
            }
        });

    }

    @Override
    protected void initData() {

    }

    private void getInfo() {
        String location = PrefUtils.getParameter("location");
        if (!TextUtils.isEmpty(location)) {
            tvShaiXuan.setText(location);
        }
        if (TextUtils.isEmpty(PrefUtils.getParameter("location"))) {
            LocationManagerUtil.getInstance().startSingleLocation(new LocationManagerUtil.LocationManagerListener() {
                @Override
                public void onSuccessLocationListener(LocationManager manager, Location location, double longitude, double latitude, String locationAddr) {
                    if (!TextUtils.isEmpty(locationAddr)) {
                        String substring = locationAddr.substring(0, locationAddr.length() - 1);
                        int i = CityCodeManager.CityCodeManager(substring);
                        PrefUtils.putParameter("location", substring);
                        PrefUtils.putParameter("location_id", "" + i);
                        common("" + i, substring);
                    }

                }

                @Override
                public void onFailureLocationListener() {

                }
            });

        } else {
            common("" + PrefUtils.getParameter("location_id"), PrefUtils.getParameter("location"));
        }

    }

    private void saveInfo(String type, String num) {
        TongJi tongJi = new TongJi();
        tongJi.setUser(BmobUser.getCurrentUser(User.class));
        tongJi.setType(type);
        tongJi.setCity(PrefUtils.getParameter("location"));
        tongJi.setShuzhi("" + num);
        tongJi.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {

            }
        });
    }

    private void common(String cityid, String name) {
        if (AppUtils.IsHaveInternet(WeatherActivity.this)) {
            MyHttpUtils.getHttpMessage("https://devapi.qweather.com/v7/weather/now?location=" + cityid + "&key=c547fb8a4b8445ddb0c91e5efd33a352", LiveBean.class, new RequestCallBack<LiveBean>() {
                @Override
                public void requestSuccess(LiveBean json) {
                    if (json.getCode().equals("200")) {
                        LiveBean.NowBean now = json.getNow();
                        tvLocation.setText(name);
                        tempTextView.setText(now.getTemp() + "℃");
                        weatherTextView.setText(now.getText());
                        publishTimeTextView.setText("发布时间" + json.getUpdateTime());
                        tvOne.setText(now.getFeelsLike() + "℃");
                        tvTwo.setText("" + now.getHumidity() + "%");
                        tvThree.setText("" + now.getWindScale() + "级");
                        tvFour.setText("" + now.getPrecip() + "毫米");
                        tvFive.setText("" + now.getPressure() + "百帕");
                        tvSix.setText("" + now.getVis() + "km");
                        saveInfo("tiganwendu", "" + now.getFeelsLike());
                        saveInfo("shidu", "" + now.getHumidity());
                        saveInfo("fengli", "" + now.getWindScale());
                        saveInfo("jiangshuiliang", "" + now.getPrecip());
                        saveInfo("daqiyaqiang", "" + now.getPressure());
                        saveInfo("nengjiandu", "" + now.getVis());
                    } else {
                        Toast.makeText(WeatherActivity.this, "获取失败，请刷新", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void requestError(String errorMsg, int errorType) {

                }
            });
            MyHttpUtils.getHttpMessage("https://devapi.qweather.com/v7/weather/3d?location=" + cityid + "&key=c547fb8a4b8445ddb0c91e5efd33a352", Weather3DBean.class, new RequestCallBack<Weather3DBean>() {
                @Override
                public void requestSuccess(Weather3DBean json) {
                    if (json.getCode().equals("200")) {
                        List<Weather3DBean.DailyBean> daily = json.getDaily();
                        detailAdapter.setNewData(daily);
                    } else {
                        Toast.makeText(WeatherActivity.this, "获取失败，请刷新", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void requestError(String errorMsg, int errorType) {

                }
            });
            MyHttpUtils.getHttpMessage("https://devapi.qweather.com/v7/indices/1d?type=1,2,3,4,5,6,7,8,9,10&location=" + cityid + "&key=c547fb8a4b8445ddb0c91e5efd33a352", LifeZhiShuBean.class, new RequestCallBack<LifeZhiShuBean>() {
                @Override
                public void requestSuccess(LifeZhiShuBean json) {
                    if (json.getCode().equals("200")) {
                        List<LifeZhiShuBean.DailyBean> daily = json.getDaily();
                        liveAdapter.setNewData(daily);
                    } else {
                        Toast.makeText(WeatherActivity.this, "获取信息失败！", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void requestError(String errorMsg, int errorType) {

                }
            });
            MyHttpUtils.getHttpMessage("https://devapi.qweather.com/v7/air/now?location=" + cityid + "&key=c547fb8a4b8445ddb0c91e5efd33a352", AirBean.class, new RequestCallBack<AirBean>() {
                @Override
                public void requestSuccess(AirBean json) {
                    if (json.getCode().equals("200")) {
                        tvAqi.setText(json.getNow().getAqi());
                        tvQuality.setText(json.getNow().getCategory());
                        tv_pm.setText("pm" + json.getNow().getPm2p5());
                        indicatorViewAqi.setIndicatorValue(Integer.parseInt(json.getNow().getAqi()));

                    } else {
                        Toast.makeText(WeatherActivity.this, "获取信息失败！", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void requestError(String errorMsg, int errorType) {

                }
            });
        } else {
            Toast.makeText(WeatherActivity.this, "获取天气信息失败！", Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick({R.id.ll_common_back, R.id.llOne, R.id.llTwo, R.id.llThree, R.id.llFour, R.id.llFive, R.id.llSix})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.llOne:

                InfoContentActivity.forward(WeatherActivity.this, "tiganwendu", tvOne.getText().toString());
                break;
            case R.id.llTwo:
                InfoContentActivity.forward(WeatherActivity.this, "shidu", tvTwo.getText().toString());
                break;
            case R.id.llThree:
                InfoContentActivity.forward(WeatherActivity.this, "fengli", tvThree.getText().toString());
                break;
            case R.id.llFour:
                InfoContentActivity.forward(WeatherActivity.this, "jiangshuiliang", tvFour.getText().toString());
                break;
            case R.id.llFive:
                InfoContentActivity.forward(WeatherActivity.this, "daqiyaqiang", tvFive.getText().toString());
                break;
            case R.id.llSix:
                InfoContentActivity.forward(WeatherActivity.this, "nengjiandu", tvSix.getText().toString());
                break;

        }
    }


    /**
     * 天晴详情的适配器
     */
    public class DetailAdapter extends BaseQuickAdapter<Weather3DBean.DailyBean, BaseViewHolder> {
        public DetailAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, Weather3DBean.DailyBean item) {
            helper.setText(R.id.date_text_view, item.getFxDate());
            helper.setText(R.id.weather_text_view, "日间：" + item.getTextDay() + " 夜间：" + item.getTextNight());
            helper.setText(R.id.temp_min_text_view, "" + item.getTempMin() + "℃/" + item.getTempMax() + "℃");


        }
    }

    public class LiveAdapter extends BaseQuickAdapter<LifeZhiShuBean.DailyBean, BaseViewHolder> {
        public LiveAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, LifeZhiShuBean.DailyBean item) {
            ImageView index_icon_image_view = helper.getView(R.id.index_icon_image_view);
            switch (helper.getAdapterPosition()) {
                case 0:
                    index_icon_image_view.setImageResource(R.drawable.ic_index_sport);
                    break;
                case 1:
                    index_icon_image_view.setImageResource(R.drawable.ic_index_car_wash);
                    break;
                case 2:
                    index_icon_image_view.setImageResource(R.drawable.ic_index_dress);
                    break;
                case 3:
                    index_icon_image_view.setImageResource(R.drawable.ic_index_sun_cure);
                    break;
                case 4:
                    index_icon_image_view.setImageResource(R.drawable.ic_index_sunscreen);
                    break;
                case 5:
                    index_icon_image_view.setImageResource(R.drawable.ic_index_shopping);
                    break;
                case 6:
                    index_icon_image_view.setImageResource(R.drawable.ic_index_clod);
                    break;
                case 7:
                    index_icon_image_view.setImageResource(R.drawable.ic_index_dance);
                    break;
                case 8:
                    index_icon_image_view.setImageResource(R.drawable.ic_index_sunscreen);
                    break;
                case 9:
                    index_icon_image_view.setImageResource(R.drawable.ic_index_clod);
                    break;
            }
            helper.setText(R.id.index_level_text_view, item.getCategory());
            helper.setText(R.id.index_name_text_view, item.getName());
            helper.setText(R.id.index_name_text_jianyi, item.getText());


        }
    }
}