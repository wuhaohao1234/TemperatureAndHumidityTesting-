package com.example.temperatureandhumiditytesting.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.temperatureandhumiditytesting.R;
import com.example.temperatureandhumiditytesting.activity.common.AboutActivity;
import com.example.temperatureandhumiditytesting.activity.common.ChangeBackImgActivity;
import com.example.temperatureandhumiditytesting.activity.common.EditInfoActivity;
import com.example.temperatureandhumiditytesting.activity.common.FeedBackActivity;
import com.example.temperatureandhumiditytesting.activity.common.SettingActivity;
import com.example.temperatureandhumiditytesting.base.BaseFragment;
import com.example.temperatureandhumiditytesting.bean.User;
import com.example.temperatureandhumiditytesting.utils.DialogNewUtils;
import com.example.temperatureandhumiditytesting.utils.DongTaiMoreActionUtils;
import com.example.temperatureandhumiditytesting.view.CustomShapeImageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

//我的页面
public class MineFragment extends BaseFragment {
    @BindView(R.id.ciBeiJng)
    CustomShapeImageView ciBeiJng;
    @BindView(R.id.ivHead)
    CustomShapeImageView ivHead;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvJianJie)
    TextView tvJianJie;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View setInitView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_three, null);
        ButterKnife.bind(MineFragment.this, view);
        initData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getInfo();
    }

    private void initData() {

        getInfo();
    }


    private void getInfo() {
        User user = BmobUser.getCurrentUser(User.class);
        String beijingtu = user.getBeijingtu();
        if (!TextUtils.isEmpty(beijingtu)) {
            Picasso.with(getActivity()).load(beijingtu)
                    .placeholder(R.mipmap.bg_zhanweitu).error(R.mipmap.bg_zhanweitu).into(ciBeiJng);
        }
        String username = user.getUsername();
        String nickname = user.getNickname();
        String jianjie = user.getJianjie();
        String head = user.getHead();
        if (!TextUtils.isEmpty(head)) {
            Picasso.with(getActivity()).load(head)
                    .placeholder(R.mipmap.bg_zhanweitu).error(R.mipmap.bg_zhanweitu).into(ivHead);
        }
        if (TextUtils.isEmpty(nickname)) {
            tvName.setText(username);
        } else {
            tvName.setText(nickname);
        }
        if (!TextUtils.isEmpty(jianjie)) {
            tvJianJie.setText(jianjie);
        }
    }

    @OnClick({R.id.ciBeiJng, R.id.ivHead, R.id.rlYiJian, R.id.rlSetting, R.id.rlCall, R.id.rlShare, R.id.rlAbout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ciBeiJng:
                //修改背景图
                ChangeBackImgActivity.forward(getActivity());
                break;
            case R.id.ivHead:
                //修改资料
                EditInfoActivity.forward(getActivity());
                break;
            case R.id.rlYiJian:
                //意见反馈
                FeedBackActivity.forward(getActivity());
                break;
            case R.id.rlSetting:
                //设置
                SettingActivity.forward(getActivity());
                break;
            case R.id.rlCall:
                //联系客服
                new DialogNewUtils.Builder(getActivity(), false, false, "是否拨打客服电话？",
                        "拨打", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri telUri = Uri.parse("tel:" + "1234567890");
                        Intent intent = new Intent(Intent.ACTION_DIAL, telUri);
                        startActivity(intent);
                        dialogInterface.dismiss();
                    }
                }, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create(0).show();
                break;
            case R.id.rlShare:
                //分享
                DongTaiMoreActionUtils.show(getActivity(), getActivity());
                break;
            case R.id.rlAbout:
                //关于
                AboutActivity.forward(getActivity());
                break;
        }
    }


}
