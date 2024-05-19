package com.example.temperatureandhumiditytesting.activity.common;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.temperatureandhumiditytesting.R;
import com.example.temperatureandhumiditytesting.activity.user.MainActivity;
import com.example.temperatureandhumiditytesting.base.App;
import com.example.temperatureandhumiditytesting.base.BaseActivity;
import com.example.temperatureandhumiditytesting.bean.User;
import com.example.temperatureandhumiditytesting.utils.PrefUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


public class ChangePswActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.etOldPsw)
    EditText etOldPsw;
    @BindView(R.id.etPsw)
    EditText etPsw;
    @BindView(R.id.etPswOnce)
    EditText etPswOnce;
    @BindView(R.id.btChange)
    Button btChange;


    @Override
    protected int initContentView() {
        return R.layout.activity_change_psw;
    }

    public static void forward(Context context) {
        Intent intent = new Intent(App.context, ChangePswActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        int chooseColor = PrefUtils.getInt(App.context, "choose_color", 0);
        if (chooseColor == 0) {
            rlTitle.setBackgroundColor(getResources().getColor(R.color.text4));
            btChange.setBackgroundColor(getResources().getColor(R.color.text4));
        } else {
            rlTitle.setBackgroundColor(chooseColor);
            btChange.setBackgroundColor(chooseColor);
        }
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();
        tvTitle.setText("修改密码");
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.ll_common_back, R.id.btChange})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.btChange:
                change();
                break;
        }
    }

    private void change() {
        String oldPsw = etOldPsw.getText().toString();
        String psw = etPsw.getText().toString();
        String psw_once = etPswOnce.getText().toString();
        if (TextUtils.isEmpty(oldPsw)) {
            ToastUtils.showToast(ChangePswActivity.this, "请输入f旧密码！");
            return;
        }
        if (TextUtils.isEmpty(psw)) {
            ToastUtils.showToast(ChangePswActivity.this, "请输入新密码！");
            return;
        }
        if (TextUtils.isEmpty(psw_once)) {
            ToastUtils.showToast(ChangePswActivity.this, "请再次输入新密码！");
            return;
        }
        if (!psw.equals(psw_once)) {
            ToastUtils.showToast(ChangePswActivity.this, "两次输入的新密码竟然不一样呢！");
            return;
        }
        if (oldPsw.equals(psw)) {
            ToastUtils.showToast(ChangePswActivity.this, "您也没改密码呀！");
            return;
        }
        //TODO 此处替换为你的旧密码和新密码
        BmobUser.updateCurrentUserPassword(oldPsw, psw, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    BmobUser.logOut();
                    MainActivity.stance.finish();
                    User user = BmobUser.getCurrentUser(User.class);
                    user.setPsw(psw);
                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                ToastUtils.showToast(ChangePswActivity.this, "修改密码成功，请重新登录");
                            }
                        }
                    });
                    SettingActivity.stance.finish();
                    PrefUtils.putParameter("isLogin", "0");
                    finish();
                }
            }
        });

    }
}
