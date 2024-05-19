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


public class ChangeNameActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.tvTitle)
    TextView tvTitle1;
    @BindView(R.id.etContent)
    EditText etContent;
    @BindView(R.id.btSubmit)
    Button btSubmit;
    private String type;

    @Override
    protected int initContentView() {
        return R.layout.activity_change_name;
    }

    public static void forward(Context context, String type) {
        Intent intent = new Intent(App.context, ChangeNameActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        int chooseColor = PrefUtils.getInt(App.context, "choose_color", 0);
        if (chooseColor == 0) {
            rlTitle.setBackgroundColor(getResources().getColor(R.color.text4));
            btSubmit.setBackgroundColor(getResources().getColor(R.color.text4));
        } else {
            rlTitle.setBackgroundColor(chooseColor);
            btSubmit.setBackgroundColor(chooseColor);
        }
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();
        type = getIntent().getStringExtra("type");
        User user = BmobUser.getCurrentUser(User.class);
        String username = user.getUsername();
        String nickname = user.getNickname();
        String jianjie = user.getJianjie();
        if ("0".equals(type)) {
            tvTitle.setText("修改昵称");
            tvTitle1.setText("昵称");
            if (!TextUtils.isEmpty(nickname)) {
                etContent.setText("" + nickname);
            } else {
                etContent.setText("" + username);
            }
            etContent.setHint("请输入昵称");

        } else {
            tvTitle.setText("修改简介");
            tvTitle1.setText("简介");
            if (!TextUtils.isEmpty(jianjie)) {
                etContent.setText("" + jianjie);
            }
            etContent.setHint("请输入简介");
        }
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.ll_common_back, R.id.btSubmit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.btSubmit:
                if ("0".equals(type)) {
                    String nickname = etContent.getText().toString();
                    if (TextUtils.isEmpty(nickname)) {
                        ToastUtils.showToast(ChangeNameActivity.this, "请填写昵称！");
                        return;
                    }
                    final User user = BmobUser.getCurrentUser(User.class);
                    user.setNickname(nickname);
                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                ToastUtils.showToast(ChangeNameActivity.this, "修改成功！");
                                finish();
                            } else {
                                ToastUtils.showToast(ChangeNameActivity.this, "修改失败！" + e.getMessage());
                            }
                        }
                    });

                } else {
                    String nickname = etContent.getText().toString();
                    if (TextUtils.isEmpty(nickname)) {
                        ToastUtils.showToast(ChangeNameActivity.this, "请填写简介！");
                        return;
                    }
                    final User user = BmobUser.getCurrentUser(User.class);
                    user.setJianjie(nickname);
                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                ToastUtils.showToast(ChangeNameActivity.this, "修改成功！");
                                finish();
                            } else {
                                ToastUtils.showToast(ChangeNameActivity.this, "修改失败！" + e.getMessage());
                            }
                        }
                    });


                }
                break;
        }
    }
}
