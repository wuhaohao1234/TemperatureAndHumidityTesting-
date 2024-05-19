package com.example.temperatureandhumiditytesting.activity.common;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.temperatureandhumiditytesting.R;
import com.example.temperatureandhumiditytesting.activity.user.MainActivity;
import com.example.temperatureandhumiditytesting.base.App;
import com.example.temperatureandhumiditytesting.base.BaseActivity;
import com.example.temperatureandhumiditytesting.bean.User;
import com.example.temperatureandhumiditytesting.utils.AppUtils;
import com.example.temperatureandhumiditytesting.utils.PrefUtils;
import com.example.temperatureandhumiditytesting.view.Interpolator;
import com.google.android.material.textfield.TextInputEditText;
import com.luck.picture.lib.utils.ToastUtils;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.etPhone)
    TextInputEditText etPhone;
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.tvRegister)
    TextView tvRegister;
    @BindView(R.id.etPsw)
    TextInputEditText etPsw;
    @BindView(R.id.btLogin)
    Button btLogin;
    @BindView(R.id.img_inbetweening)
    ImageView imgPic;
    @BindView(R.id.login_input_layout)
    LinearLayout mInputLayout;
    @BindView(R.id.login_layout_progress)
    View progress;
    private long exitTime;

    @Override
    protected int initContentView() {
        return R.layout.activity_login;
    }

    public static void forward(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        int chooseColor = PrefUtils.getInt(App.context, "choose_color", 0);
        if (chooseColor == 0) {
            btLogin.setBackgroundColor(getResources().getColor(R.color.text4));
            tvRegister.setTextColor(getResources().getColor(R.color.text4));
        } else {
            btLogin.setBackgroundColor(chooseColor);
            tvRegister.setTextColor(chooseColor);
        }
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initListener() {
        btLogin.setOnClickListener(new animationOnClickListener(LoginActivity.this,btLogin));

    }

    @Override
    protected void initData() {
        String[] array = getResources().getStringArray(R.array.content);
        String randomStr = array[new Random().nextInt(array.length)];
        Typeface tfRegular1 = Typeface.createFromAsset(getAssets(), randomStr);//初始化字体
        tvContent.setTypeface(tfRegular1);
        Glide.with(this).load(R.mipmap.logo).into(imgPic);

        // 渐变动画
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(2000);
        imgPic.startAnimation(animation);
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

            AppUtils.AppExit(LoginActivity.this);
        }
    }

    @OnClick({R.id.btLogin, R.id.tvRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btLogin:

                break;
            case R.id.tvRegister:
                RegisterActivity.forward(LoginActivity.this);
                break;
        }
    }

    private void login() {
        /**
         * 3:管理员
         * 4：普通用户
         */
        String phone = etPhone.getText().toString();
        String psw = etPsw.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            recovery();
            ToastUtils.showToast(LoginActivity.this, "请输入学生学号！");
            return;
        }
        if (TextUtils.isEmpty(psw)) {
            recovery();
            ToastUtils.showToast(LoginActivity.this, "请输入密码！");
            return;
        }
        final User user = new User();
        //此处替换为你的用户名
        user.setUsername(phone);
        //此处替换为你的密码
        user.setPassword(psw);
        user.login(new SaveListener<User>() {
            @Override
            public void done(User bmobUser, BmobException e) {
                if (e == null) {
                        ToastUtils.showToast(LoginActivity.this, "登录成功！");
                        PrefUtils.putParameter("user_id", "" + user.getObjectId());
                        PrefUtils.putParameter("type", "2");
                        PrefUtils.putString(LoginActivity.this, "isLogin", "1");
                        MainActivity.forward(LoginActivity.this);
                        finish();

                } else {
                    ToastUtils.showToast(LoginActivity.this, "登录失败" + e.getMessage());
                }
                recovery();
            }
        });
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
                tvRegister.setVisibility(View.INVISIBLE);

                inputAnimator(mInputLayout, mWidth, mHeight);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        login();

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
        btLogin.setVisibility(View.VISIBLE);
        mInputLayout.setVisibility(View.VISIBLE);
        etPhone.setVisibility(View.VISIBLE);
        etPsw.setVisibility(View.VISIBLE);
        tvRegister.setVisibility(View.VISIBLE);

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
