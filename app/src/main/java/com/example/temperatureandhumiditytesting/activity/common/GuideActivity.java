package com.example.temperatureandhumiditytesting.activity.common;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.temperatureandhumiditytesting.R;
import com.example.temperatureandhumiditytesting.activity.user.MainActivity;
import com.example.temperatureandhumiditytesting.base.BaseActivity;
import com.example.temperatureandhumiditytesting.bean.TopBannerBean;
import com.example.temperatureandhumiditytesting.utils.PrefUtils;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
public class GuideActivity extends BaseActivity {
    @BindView(R.id.guide)
    XBanner xBanner;
    private List<TopBannerBean> images = new ArrayList<>();
    private int[] image = {R.mipmap.bg_guide_one, R.mipmap.bg_guide_two, R.mipmap.bg_guide_three};
    @Override
    protected int initContentView() {
        return R.layout.guide_activity;
    }
    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }
    @Override
    protected void initListener() {
    }
    @Override
    protected void initData() {
        for (int i = 0; i < image.length; i++) {
            TopBannerBean topBannerBean = new TopBannerBean(image[i]);
            images.add(topBannerBean);
        }
        xBanner.setBannerData(images);
        // 设置XBanner的页面切换特效
        xBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                //1、此处使用的Glide加载图片，可自行替换自己项目中的图片加载框架
                //2、返回的图片路径为Object类型，你只需要强转成你传输的类型就行，切记不要胡乱强转！
//                (ImageView)view.setBackgroundResource(images.get(position).get);
                Glide.with(GuideActivity.this).load(images.get(position).getXBannerUrl()).centerCrop().into((ImageView) view);
            }
        });
        xBanner.setPageTransformer(Transformer.Default);
    }

    public static void forward(Context context) {
        Intent intent = new Intent(context, GuideActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.scale)
    public void onClick() {
        String isLogin = PrefUtils.getString(GuideActivity.this, "isLogin", "0");
        PrefUtils.putString(GuideActivity.this, "isGuide", "1");
        if ("0".equals(isLogin)) {
            LoginActivity.forward(GuideActivity.this);
        }else {
            MainActivity.forward(GuideActivity.this);
        }

        finish();
    }
}
