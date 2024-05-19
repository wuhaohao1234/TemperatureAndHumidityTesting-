package com.example.temperatureandhumiditytesting.activity.common;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.temperatureandhumiditytesting.R;
import com.example.temperatureandhumiditytesting.activity.user.MainActivity;
import com.example.temperatureandhumiditytesting.base.App;
import com.example.temperatureandhumiditytesting.base.BaseActivity;
import com.example.temperatureandhumiditytesting.bean.User;
import com.example.temperatureandhumiditytesting.utils.DialogNewUtils;
import com.example.temperatureandhumiditytesting.utils.PrefUtils;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;


public class SettingActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.btExit)
    Button btExit;
    public static SettingActivity stance;

    @Override
    protected int initContentView() {
        return R.layout.activity_setting;
    }

    public static void forward(Context context) {
        Intent intent = new Intent(App.context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();
        tvTitle.setText("设置");
        stance = this;
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        int chooseColor = PrefUtils.getInt(App.context, "choose_color", 0);
        if (chooseColor==0) {
            rlTitle.setBackgroundColor(getResources().getColor(R.color.text4));
            btExit.setBackgroundColor(getResources().getColor(R.color.text4));
        }else {
            rlTitle.setBackgroundColor(chooseColor);
            btExit.setBackgroundColor(chooseColor);
        }
    }

    @OnClick({R.id.ll_common_back, R.id.llChangePsw, R.id.llChangeColor, R.id.llBanQuan, R.id.llYongHu, R.id.llYinSi, R.id.llZhuXiao, R.id.btExit})
    public void onClick(View view) {
        Intent intent = new Intent(App.context, CommonWebActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("url", "https://www.baidu.com/");
        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.llChangePsw:
                ChangePswActivity.forward(SettingActivity.this);
                break;
            case R.id.llChangeColor:
                ChangeColorActivity.forward(SettingActivity.this);
                break;
            case R.id.llBanQuan:
                banquan();
                break;
            case R.id.llYongHu:
                intent.putExtra("type", "yonghuxieyi");
                startActivity(intent);
                break;
            case R.id.llYinSi:
                intent.putExtra("type", "yinsizhengce");
                startActivity(intent);
                break;
            case R.id.llZhuXiao:
                tuichu("是否注销账号？", "注销");
                break;
            case R.id.btExit:
                tuichu("是否退出登录？", "退出");
                break;
        }
    }

    private void tuichu(String title, String button) {
        new DialogNewUtils.Builder(SettingActivity.this, false, false, title,
                button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LoginActivity.forward(SettingActivity.this);
                PrefUtils.putParameter("isLogin", "0");
                finish();
                User currentUser = BmobUser.getCurrentUser(User.class);
                String type = currentUser.getType();

                    MainActivity.stance.finish();

                BmobUser.logOut();
                dialogInterface.dismiss();
            }
        }, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create(0).show();
    }

    public void banquan() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.banquan_layout, null);
        TextView tv_queren = view.findViewById(R.id.tv_queren);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Dialog).setView(view);
        final AlertDialog dialog01 = builder.show();
        //设置弹窗的宽度，高度
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        WindowManager.LayoutParams params =
                dialog01.getWindow().getAttributes();//获取dialog信息
        params.width = screenWidth - 300;
        params.height = screenHeigh / 2;
        dialog01.getWindow().setAttributes(params);//设置大小
        tv_queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog01.dismiss();
            }
        });
        //点击空白处弹框不消失
        dialog01.setCancelable(false);
        dialog01.show();
    }
}
