package com.example.temperatureandhumiditytesting.activity.user;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.temperatureandhumiditytesting.R;
import com.example.temperatureandhumiditytesting.adapter.MyBasePagerAdapter;
import com.example.temperatureandhumiditytesting.base.BaseActivity;
import com.example.temperatureandhumiditytesting.utils.AppUtils;
import com.example.temperatureandhumiditytesting.view.NoScrollViewPager;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    public static MainActivity stance;
    @BindView(R.id.vp_main)
    NoScrollViewPager mVP_main;
    private long exitTime;

    @Override
    protected int initContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).statusBarDarkFont(true).init();

        stance = this;
    }
    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {


        MyBasePagerAdapter myBasePagerAdapter = new MyBasePagerAdapter(getSupportFragmentManager());
        mVP_main.setCurrentItem(0);
        mVP_main.setOffscreenPageLimit(10);
        mVP_main.setAdapter(myBasePagerAdapter);
    }

    public static void forward(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    //监听手机的物理按键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, "再点击一次退出APP", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {

            AppUtils.AppExit(MainActivity.this);
        }
    }

    @OnClick({R.id.rb_home, R.id.rb_three})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_home:
                mVP_main.setCurrentItem(0, false);
                break;
            case R.id.rb_three:
                mVP_main.setCurrentItem(1, false);
                break;

        }
    }
}