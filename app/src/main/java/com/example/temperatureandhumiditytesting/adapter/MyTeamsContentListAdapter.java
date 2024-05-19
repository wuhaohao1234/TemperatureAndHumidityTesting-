package com.example.temperatureandhumiditytesting.adapter;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.temperatureandhumiditytesting.R;
import com.example.temperatureandhumiditytesting.bean.TeamsContent;
import com.example.temperatureandhumiditytesting.bean.User;
import com.example.temperatureandhumiditytesting.view.CircleImageView;
import com.squareup.picasso.Picasso;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class MyTeamsContentListAdapter extends BaseQuickAdapter<TeamsContent, BaseViewHolder> {
    public MyTeamsContentListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, TeamsContent item) {
        CircleImageView ivHead = helper.getView(R.id.ivHead);
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(item.getAuthor().getObjectId(), new QueryListener<User>() {
            @Override
            public void done(User category, BmobException e) {
                if (e == null) {
                    Picasso.with(mContext).load(category.getHead()).placeholder(R.mipmap.bg_zhanweitu)
                            .error(R.mipmap.bg_zhanweitu).into(ivHead);
                    helper.setText(R.id.tvName, TextUtils.isEmpty(category.getNickname())?category.getObjectId()+"    联系方式："+item.getGameID():category.getNickname()+"    游戏ID："+item.getGameID());
                }
            }
        });
        String state = item.getState();
        helper.setText(R.id.tvContent,"备注："+item.getRemark());
        if ("1".equals(state)) {
            helper.setText(R.id.tvTime,"申请加入");
        }
        helper.addOnClickListener(R.id.ivHead,R.id.tv_quxiao,R.id.tv_queren);
    }
}
