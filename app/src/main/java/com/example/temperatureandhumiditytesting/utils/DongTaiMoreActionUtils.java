package com.example.temperatureandhumiditytesting.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.temperatureandhumiditytesting.R;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.OSUtils;

import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;


/**
 * * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #            2/20/16:01 .
 * #            com.example.book.fragment
 * #            APP
 */
public class DongTaiMoreActionUtils {


    public static void show(final Context context, Activity activity) {
        PopupWindow mPopupWindow = new PopupWindow(activity);
        View mPopupView = activity.getLayoutInflater().inflate(R.layout.shared_layout_new, null);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setContentView(mPopupView);
        //重点，此方法可以让布局延伸到状态栏和导航栏
        mPopupWindow.setClippingEnabled(false);
        //设置动画
        mPopupWindow.setAnimationStyle(R.style.BottomAnimation);
        //弹出
        mPopupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        //弹出后背景alpha值
        backgroundAlpha(activity, 0.5f);
        //消失后恢复背景alpha值
        mPopupWindow.setOnDismissListener(() -> backgroundAlpha(activity, 1f));
        //------------------------------
        final TextView tvQQ = mPopupView.findViewById(R.id.tvQQ);
        final TextView tvKongJian = mPopupView.findViewById(R.id.tvKongJian);
        final TextView tvQuXiao = mPopupView.findViewById(R.id.tvQuXiao);
        tvQuXiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        tvQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.showShareAppCommon(QQ.NAME,activity,"APP","https://www.baidu.com/","测试用的","https://www.baidu.com/");

                mPopupWindow.dismiss();
            }
        });
        tvKongJian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.showShareAppCommon(QZone.NAME,activity,"APP","https://www.baidu.com/","测试用的","https://www.baidu.com/");

                mPopupWindow.dismiss();
            }
        });
        //------------------------------


        //适配弹出popup后布局被状态栏和导航栏遮挡问题
//        updatePopupView(mPopupView, activity);
    }

    /**
     * 调整popupWindow里view的Margins值来适配布局被导航栏遮挡问题，因为要适配横竖屏切换，所以代码有点多
     * Update popup view.
     */
    private static void updatePopupView(View mPopupView, Activity activity) {
        int navigationBarHeight = ImmersionBar.getNavigationBarHeight(activity);
        int navigationBarWidth = ImmersionBar.getNavigationBarWidth(activity);
        if (mPopupView != null) {
            View rlContent = mPopupView.findViewById(R.id.rlContent);
            mPopupView.post(() -> {
                boolean isPortrait;
                boolean isLandscapeLeft;
                int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
                if (rotation == Surface.ROTATION_90) {
                    isPortrait = false;
                    isLandscapeLeft = true;
                } else if (rotation == Surface.ROTATION_270) {
                    isPortrait = false;
                    isLandscapeLeft = false;
                } else {
                    isPortrait = true;
                    isLandscapeLeft = false;
                }
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rlContent.getLayoutParams();

                if (isPortrait) {
                    layoutParams.setMargins(0, 0, 0, navigationBarHeight);
                } else {
                    if (isLandscapeLeft) {
                        layoutParams.setMargins(0, 0, navigationBarWidth, 0);
                    } else {
                        if (OSUtils.isEMUI3_x()) {
                            layoutParams.setMargins(0, 0, navigationBarWidth, 0);
                        } else {
                            layoutParams.setMargins(navigationBarWidth, 0, 0, 0);
                        }
                    }
                }

                rlContent.setLayoutParams(layoutParams);
            });
        }
    }

    /**
     * 设置弹出popup，背景alpha值
     *
     * @param bgAlpha 0f - 1f
     */
    public static void backgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }
}