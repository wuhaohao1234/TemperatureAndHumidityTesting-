package com.example.temperatureandhumiditytesting.fragment;

import android.Manifest;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.baronzhang.android.widget.IndicatorView;
import com.example.temperatureandhumiditytesting.R;
import com.example.temperatureandhumiditytesting.activity.user.HaiBaPhoneActivity;
import com.example.temperatureandhumiditytesting.activity.user.TongJiActivity;
import com.example.temperatureandhumiditytesting.activity.user.WeatherActivity;
import com.example.temperatureandhumiditytesting.activity.user.WenDuPhoneActivity;
import com.example.temperatureandhumiditytesting.base.App;
import com.example.temperatureandhumiditytesting.base.BaseFragment;
import com.example.temperatureandhumiditytesting.bean.AirBean;
import com.example.temperatureandhumiditytesting.bean.LiveBean;
import com.example.temperatureandhumiditytesting.bean.NumberBean;
import com.example.temperatureandhumiditytesting.bean.TongJi;
import com.example.temperatureandhumiditytesting.bean.User;
import com.example.temperatureandhumiditytesting.myhttp.MyHttpUtils;
import com.example.temperatureandhumiditytesting.myhttp.RequestCallBack;
import com.example.temperatureandhumiditytesting.utils.CityCodeManager;
import com.example.temperatureandhumiditytesting.utils.LocationManagerUtil;
import com.example.temperatureandhumiditytesting.utils.MyEvent;
import com.example.temperatureandhumiditytesting.utils.PrefUtils;
import com.example.temperatureandhumiditytesting.view.RiseNumberTextView;
import com.gyf.immersionbar.ImmersionBar;
import com.permissionx.guolindev.PermissionX;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class HomeFragment extends BaseFragment {
    @BindView(R.id.iv_common_back)
    ImageView ivCommonBack;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.publish_time_text_view)
    TextView publishTimeTextView;
    @BindView(R.id.temp_text_view)
    TextView tempTextView;
    @BindView(R.id.tv_aqi)
    RiseNumberTextView tvAqi;
    @BindView(R.id.tvShaiXuan)
    TextView tvShaiXuan;
    @BindView(R.id.tv_quality)
    TextView tvQuality;
    @BindView(R.id.indicator_view_aqi)
    IndicatorView indicatorViewAqi;
    @BindView(R.id.weather_text_view)
    TextView weatherTextView;


    @Override
    protected void lazyLoad() {

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
    protected View setInitView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null);
        ButterKnife.bind(HomeFragment.this, view);
        ivCommonBack.setVisibility(View.GONE);
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();
        tvTitle.setText("首页");
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        PermissionX.init(getActivity()).permissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_CALENDAR,
                        Manifest.permission.BATTERY_STATS,
                        Manifest.permission.BODY_SENSORS,
                        Manifest.permission.READ_CALENDAR,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                )
                .explainReasonBeforeRequest()
                .onExplainRequestReason((scope, deniedList) ->
                        scope.showRequestReasonDialog(deniedList, "为了获取当地天气,需要申请定位权限。您可以通过系统\"设置\"进行权限管理。", "允许", "拒绝")
                )
                .onForwardToSettings((scope, deniedList) ->
                        scope.showForwardToSettingsDialog(deniedList, "去设置中获取使用权限", "允许", "拒绝")
                )
                .request((allGranted, grantedList, deniedList) ->
                        {
                            getInfo();
                        }
                );


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getInfo();
                refreshLayout.finishRefresh();
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe
    public void onEventMainThread(MyEvent event) {
        String msg = event.getMsg();

    }


    private void getInfo() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        publishTimeTextView.setText("" + simpleDateFormat.format(date));
        Typeface tfRegular = Typeface.createFromAsset(mContext.getAssets(), "fonts/中国龙新草体.ttf");//初始化字体
        publishTimeTextView.setTypeface(tfRegular);
        Typeface tfRegular1 = Typeface.createFromAsset(mContext.getAssets(), "fonts/王漢宗海報體一半天水.ttf");//初始化字体
        tvAqi.setTypeface(tfRegular1);
        LocationManagerUtil.getInstance().startSingleLocation(new LocationManagerUtil.LocationManagerListener() {
            @Override
            public void onSuccessLocationListener(LocationManager manager, Location location, double longitude, double latitude, String locationAddr) {
                if (!TextUtils.isEmpty(locationAddr)) {
                    String substring = locationAddr.substring(0, locationAddr.length() - 1);
                    int i = CityCodeManager.CityCodeManager(substring);
                    PrefUtils.putParameter("location", substring);
                    PrefUtils.putParameter("location_id", "" + i);
                    MyHttpUtils.getHttpMessage("https://devapi.qweather.com/v7/weather/now?location=" + i + "&key=c547fb8a4b8445ddb0c91e5efd33a352", LiveBean.class, new RequestCallBack<LiveBean>() {
                        @Override
                        public void requestSuccess(LiveBean json) {
                            if (json.getCode().equals("200")) {
                                LiveBean.NowBean now = json.getNow();
                                tvAqi.setText(json.getNow().getHumidity());

                                int indicatorValue = Integer.parseInt(json.getNow().getHumidity());
                                tvAqi.withNumber(indicatorValue);
                                tvAqi.setDuration(1000);
                                tvAqi.start();
                                tvShaiXuan.setText(substring);
                                TongJi tongJi = new TongJi();
                                tongJi.setUser(BmobUser.getCurrentUser(User.class));
                                tongJi.setType("wendu");
                                tongJi.setShuzhi(""+now.getTemp());
                                tongJi.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {

                                    }
                                });


                                TongJi tongJi1 = new TongJi();
                                tongJi1.setUser(BmobUser.getCurrentUser(User.class));
                                tongJi1.setType("shidu");
                                tongJi1.setShuzhi(""+now.getHumidity());
                                tongJi1.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {

                                    }
                                });
                                tempTextView.setText(now.getTemp() + "℃");
                                weatherTextView.setText(now.getText());

                            } else {
                                Toast.makeText(mContext, "获取失败，请刷新", Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void requestError(String errorMsg, int errorType) {

                        }
                    });
                    MyHttpUtils.getHttpMessage("https://devapi.qweather.com/v7/air/now?location=" + i + "&key=c547fb8a4b8445ddb0c91e5efd33a352", AirBean.class, new RequestCallBack<AirBean>() {
                        @Override
                        public void requestSuccess(AirBean json) {
                            if (json.getCode().equals("200")) {

//                                if (indicatorValue <= 50) {
//                                    tvQuality.setText("今天环境挺好的哦，适合出门，放心");
//                                    tvAqi.setTextColor(getContext().getResources().getColor(R.color.indicator_color_1));
//                                    tvQuality.setTextColor(getContext().getResources().getColor(R.color.indicator_color_1));
//                                } else if (indicatorValue > 50 && indicatorValue <= 100) {
//                                    tvQuality.setText("今天环境比较好，适合出门，放心");
//                                    tvAqi.setTextColor(getContext().getResources().getColor(R.color.indicator_color_2));
//                                    tvQuality.setTextColor(getContext().getResources().getColor(R.color.indicator_color_2));
//                                } else if (indicatorValue > 100 && indicatorValue <= 150) {
//                                    tvQuality.setText("今天环境一般好，适合出门，放心");
//                                    tvAqi.setTextColor(getContext().getResources().getColor(R.color.indicator_color_3));
//                                    tvQuality.setTextColor(getContext().getResources().getColor(R.color.indicator_color_3));
//                                } else if (indicatorValue > 150 && indicatorValue <= 200) {
//                                    tvQuality.setText("今天环境较差，没什么就在屋里呆着吧");
//                                    tvAqi.setTextColor(getContext().getResources().getColor(R.color.indicator_color_4));
//                                    tvQuality.setTextColor(getContext().getResources().getColor(R.color.indicator_color_4));
//                                } else if (indicatorValue > 200 && indicatorValue <= 300) {
//                                    tvQuality.setText("今天环境很差，没什么就在屋里呆着吧");
//                                    tvAqi.setTextColor(getContext().getResources().getColor(R.color.indicator_color_5));
//                                    tvQuality.setTextColor(getContext().getResources().getColor(R.color.indicator_color_5));
//                                } else {
//                                    tvQuality.setText("今天环境相当差，污染太严重了，睡觉吧");
//                                    tvAqi.setTextColor(getContext().getResources().getColor(R.color.indicator_color_6));
//                                    tvQuality.setTextColor(getContext().getResources().getColor(R.color.indicator_color_6));
//                                }
                                indicatorViewAqi.setIndicatorValue(Integer.parseInt(json.getNow().getAqi()));

                            } else {
                                Toast.makeText(mContext, "获取信息失败！", Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void requestError(String errorMsg, int errorType) {

                        }
                    });
                }

            }

            @Override
            public void onFailureLocationListener() {

            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @OnClick({R.id.tvFanYi, R.id.tvAlarm, R.id.tvBeiWangLu, R.id.iv_common_back, R.id.tvTongJi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvFanYi:
                WeatherActivity.forward(getActivity());
                break;

            case R.id.tvAlarm:
                HaiBaPhoneActivity.forward(getActivity());
                break;
            case R.id.tvBeiWangLu:
                WenDuPhoneActivity.forward(getActivity());
                break;
            case R.id.tvTongJi:
                showProgressDialog(getActivity(), false);
                NumberBean numberBean = new NumberBean();
                BmobQuery<TongJi> query = new BmobQuery<>();
                query.addWhereEqualTo("type", "fanyi");
                query.addWhereEqualTo("user", BmobUser.getCurrentUser(User.class));
                query.order("-updatedAt");
                //包含作者信息
                query.include("author");
                query.findObjects(new FindListener<TongJi>() {
                    @Override
                    public void done(List<TongJi> object, BmobException e) {
                        if (e == null) {
                            numberBean.setFanyi(object.size());
                            BmobQuery<TongJi> query1 = new BmobQuery<>();
                            query1.addWhereEqualTo("type", "naoling");
                            query1.addWhereEqualTo("user", BmobUser.getCurrentUser(User.class));
                            query1.order("-updatedAt");
                            //包含作者信息
                            query1.include("author");
                            query1.findObjects(new FindListener<TongJi>() {
                                @Override
                                public void done(List<TongJi> object, BmobException e) {
                                    if (e == null) {
                                        numberBean.setNaoling(object.size());

                                        BmobQuery<TongJi> query2 = new BmobQuery<>();
                                        query2.addWhereEqualTo("type", "beiwanglu");
                                        query2.addWhereEqualTo("user", BmobUser.getCurrentUser(User.class));
                                        query2.order("-updatedAt");
                                        //包含作者信息
                                        query2.include("author");
                                        query2.findObjects(new FindListener<TongJi>() {
                                            @Override
                                            public void done(List<TongJi> object, BmobException e) {
                                                if (e == null) {
                                                    numberBean.setBeiwanglu(object.size());
                                                    BmobQuery<TongJi> query3 = new BmobQuery<>();
                                                    query3.addWhereEqualTo("type", "luntan");
                                                    query3.addWhereEqualTo("user", BmobUser.getCurrentUser(User.class));
                                                    query3.order("-updatedAt");
                                                    //包含作者信息
                                                    query3.include("author");
                                                    query3.findObjects(new FindListener<TongJi>() {
                                                        @Override
                                                        public void done(List<TongJi> object, BmobException e) {
                                                            if (e == null) {
                                                                numberBean.setLuntan(object.size());
                                                                BmobQuery<TongJi> query4 = new BmobQuery<>();
                                                                query4.addWhereEqualTo("type", "zudui");
                                                                query4.addWhereEqualTo("user", BmobUser.getCurrentUser(User.class));
                                                                query4.order("-updatedAt");
                                                                //包含作者信息
                                                                query4.include("author");
                                                                query4.findObjects(new FindListener<TongJi>() {
                                                                    @Override
                                                                    public void done(List<TongJi> object, BmobException e) {
                                                                        if (e == null) {
                                                                            numberBean.setZudui(object.size());
                                                                            BmobQuery<TongJi> query5 = new BmobQuery<>();
                                                                            query5.addWhereEqualTo("type", "video");
                                                                            query5.addWhereEqualTo("user", BmobUser.getCurrentUser(User.class));
                                                                            query5.order("-updatedAt");
                                                                            //包含作者信息
                                                                            query5.include("author");
                                                                            query5.findObjects(new FindListener<TongJi>() {
                                                                                @Override
                                                                                public void done(List<TongJi> object, BmobException e) {
                                                                                    if (e == null) {
                                                                                        stopProgressDialog();
                                                                                        numberBean.setVideo(object.size());
                                                                                        TongJiActivity.forward(getActivity(), numberBean);
                                                                                    }
                                                                                }

                                                                            });
                                                                        }
                                                                    }

                                                                });
                                                            }
                                                        }

                                                    });
                                                }
                                            }

                                        });
                                    }
                                }

                            });
                        }
                    }

                });


                break;
        }

    }


}
