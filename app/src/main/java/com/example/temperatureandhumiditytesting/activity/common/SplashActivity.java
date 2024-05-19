package com.example.temperatureandhumiditytesting.activity.common;

import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.temperatureandhumiditytesting.R;
import com.example.temperatureandhumiditytesting.activity.user.MainActivity;
import com.example.temperatureandhumiditytesting.base.App;
import com.example.temperatureandhumiditytesting.base.BaseActivity;
import com.example.temperatureandhumiditytesting.utils.AppUtils;
import com.example.temperatureandhumiditytesting.utils.PrefUtils;
import com.example.temperatureandhumiditytesting.utils.XieYiDialogUtils;
import com.example.temperatureandhumiditytesting.view.Couterdown;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.ibBg)
    ImageView ibBg;
    private CountDownTimer start;

    private void jump() {
        start = new Couterdown(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public String toClock(long millis) {
                return super.toClock(millis);
            }

            @Override
            public void onFinish() {

            }

        }.start();
    }

    /**
     * 腾讯要求 必须有协议提示
     */
    private void initDialogXieYi() {

        XieYiDialogUtils.Builder builder = new XieYiDialogUtils.Builder(SplashActivity.this, false, false, "",
                "同意",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PrefUtils.putString(SplashActivity.this, "isxieyi", "1");
//                        MobSDK.submitPolicyGrantResult(true);
                        animationConfig();
                        dialog.dismiss();

                    }
                }, "拒绝", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
                AppUtils.AppExit(SplashActivity.this);
            }
        });
        builder.create().show();

    }

    @Override
    protected void onDestroy() {
        if (start != null) {
            start.cancel();
        }
        super.onDestroy();
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        animationConfig();
    }

    @Override
    protected void initListener() {

    }

    private ScaleAnimation animation;

    // 缩放动画配置
    private void animationConfig() {
        // 从原图大小，放大到1.5倍
        animation = new ScaleAnimation(1, 1.5f, 1, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, 1, 0.5f);
        // 设置持续时间
        animation.setDuration(4000);
        // 设置动画结束之后的状态是否是动画的最终状态
        animation.setFillAfter(true);
        // 设置循环次数
        animation.setRepeatCount(0);
        // 设置动画结束后事件
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                String isGuide = PrefUtils.getParameter("isGuide");
                if ("1".equals(isGuide)) {
                    String isLogin = PrefUtils.getString(SplashActivity.this, "isLogin", "0");
                    if ("0".equals(isLogin)) {
                        LoginActivity.forward(SplashActivity.this);
                    } else {
                        String type = PrefUtils.getString(App.context, "type", "1");
                        if (!TextUtils.isEmpty(type)) {
                            MainActivity.forward(SplashActivity.this);
                        }

                    }

                } else {
                    GuideActivity.forward(SplashActivity.this);
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void initData() {
        String isxieyi = PrefUtils.getString(SplashActivity.this, "isxieyi", "0");
        if ("0".equals(isxieyi)) {
            initDialogXieYi();
        } else {
            ibBg.startAnimation(animation);
        }

    }
}
