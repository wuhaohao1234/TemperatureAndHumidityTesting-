package com.example.temperatureandhumiditytesting.activity.common;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.temperatureandhumiditytesting.R;
import com.example.temperatureandhumiditytesting.base.App;
import com.example.temperatureandhumiditytesting.base.BaseActivity;
import com.example.temperatureandhumiditytesting.bean.User;
import com.example.temperatureandhumiditytesting.utils.PrefUtils;
import com.example.temperatureandhumiditytesting.view.Interpolator;
import com.google.android.material.textfield.TextInputEditText;
import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BaseActivity {


    @BindView(R.id.etPhone)
    TextInputEditText etPhone;
    @BindView(R.id.etPsw)
    TextInputEditText etPsw;
    @BindView(R.id.etPswOnce)
    TextInputEditText etPswOnce;
    @BindView(R.id.etRegisterMa)
    TextInputEditText etRegisterMa;
    @BindView(R.id.btRegister)
    Button btRegister;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.login_layout_progress)
    View progress;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.img_inbetweening)
    ImageView imgPic;
    @BindView(R.id.login_input_layout)
    LinearLayout mInputLayout;
    private String type = "";
    private String zhucema = "1234567890";

    @Override
    protected int initContentView() {
        return R.layout.activity_register;
    }

    public static void forward(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        int chooseColor = PrefUtils.getInt(App.context, "choose_color", 0);
        if (chooseColor == 0) {
            btRegister.setBackgroundColor(getResources().getColor(R.color.text4));
            rlTitle.setBackgroundColor(getResources().getColor(R.color.text4));
        } else {
            btRegister.setBackgroundColor(chooseColor);
            rlTitle.setBackgroundColor(chooseColor);
        }
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();
        tvTitle.setText("注册");
    }

    @Override
    protected void initListener() {
        btRegister.setOnClickListener(new animationOnClickListener(RegisterActivity.this,btRegister));
    }

    @Override
    protected void initData() {

        Glide.with(this).load(R.mipmap.logo).into(imgPic);

        // 渐变动画
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(2000);
        imgPic.startAnimation(animation);
    }


    @OnClick({R.id.btRegister, R.id.ll_common_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
        }

    }
    private float mWidth, mHeight;
    private void inputAnimator(final View view, float w, float h) {

        AnimatorSet set = new AnimatorSet();

        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
                        .getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout,
                "scaleX", 1f, 0.5f);
        set.setDuration(1000);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);
                mInputLayout.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });

    }
    class animationOnClickListener implements View.OnClickListener{
        private Context context;
        private TextView btnLogin;


        public animationOnClickListener(Context context, Button btnLogin){
            this.btnLogin = btnLogin;
            this.context = context;
        }

        @Override
        public void onClick(View view){

            if(btnLogin.getVisibility() == View.VISIBLE){

                btnLogin.setVisibility(View.GONE);

                // 计算出控件的高与宽
                mWidth = btnLogin.getMeasuredWidth();
                mHeight = btnLogin.getMeasuredHeight();
                // 隐藏输入框
                etPhone.setVisibility(View.INVISIBLE);
                etPsw.setVisibility(View.INVISIBLE);
                etPswOnce.setVisibility(View.INVISIBLE);

                inputAnimator(mInputLayout, mWidth, mHeight);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String phone = etPhone.getText().toString();
                        String psw = etPsw.getText().toString();
                        String psw_once = etPswOnce.getText().toString();
                        if (TextUtils.isEmpty(phone)) {
                            recovery();
                            ToastUtils.showToast(RegisterActivity.this, "请输入学生学号！");
                            return;
                        }
                        if (TextUtils.isEmpty(type)) {
                            recovery();
                            ToastUtils.showToast(RegisterActivity.this, "请选择用户类型！");
                            return;
                        }
                        if (TextUtils.isEmpty(psw)) {
                            recovery();
                            ToastUtils.showToast(RegisterActivity.this, "请输入密码！");
                            return;
                        }
                        if (TextUtils.isEmpty(psw_once)) {
                            recovery();
                            ToastUtils.showToast(RegisterActivity.this, "请再次输入密码！");
                            return;
                        }
                        if (!psw.equals(psw_once)) {
                            recovery();
                            ToastUtils.showToast(RegisterActivity.this, "两次输入的密码竟然不一样呢！");
                            return;
                        }
                        User p2 = new User();
                        p2.setUsername(phone);
                        p2.setPassword(psw);
                        p2.setPsw(psw);
                        p2.setType(type);
                        p2.setGender(2);
                        p2.setAge(0);
                        p2.signUp(new SaveListener<User>() {
                            @Override
                            public void done(User user, BmobException e) {
                                if (e == null) {
                                    ToastUtils.showToast(RegisterActivity.this, "注册成功，用该账号登录去吧！");
                                    finish();
                                } else {
                                    ToastUtils.showToast(RegisterActivity.this, "注册失败：" + e.getMessage());
                                }
                                recovery();
                            }
                        });



                    }
                }, 2000);

            }

        }
    }
    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(1000);
        animator3.setInterpolator(new Interpolator());
        animator3.start();

    }
    /**
     * 恢复初始状态
     */
    private void recovery() {

        progress.setVisibility(View.GONE);
        btRegister.setVisibility(View.VISIBLE);
        mInputLayout.setVisibility(View.VISIBLE);
        etPhone.setVisibility(View.VISIBLE);
        etPsw.setVisibility(View.VISIBLE);
        etPswOnce.setVisibility(View.VISIBLE);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mInputLayout.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 0;
        mInputLayout.setLayoutParams(params);


        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 0.5f,1f );
        animator2.setDuration(500);
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.start();
    }

}
