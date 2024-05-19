package com.example.temperatureandhumiditytesting.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.temperatureandhumiditytesting.R;
import com.example.temperatureandhumiditytesting.bean.Teams;
import com.example.temperatureandhumiditytesting.view.CircleImageView;
import com.squareup.picasso.Picasso;


public class TeamsListAdapter extends BaseQuickAdapter<Teams, BaseViewHolder> {

    public TeamsListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Teams item) {
        CircleImageView cv_head = helper.getView(R.id.cv_head);
        TextView tvNum = helper.getView(R.id.tvNum);
        helper.setText(R.id.tvTitle, item.getName());
        helper.setText(R.id.tvContent, item.getContent());
        helper.setText(R.id.tv_game_name, item.getGamename());
        helper.setText(R.id.tv_release_time, "创建于：" + item.getCreatedAt());
        if (!TextUtils.isEmpty(item.getLogo())) {
            Picasso.with(mContext).load(item.getLogo()).placeholder(R.mipmap.bg_zhanweitu)
                    .error(R.mipmap.bg_zhanweitu).into(cv_head);
        }
        String state = item.getState();
        if (state.equals("1")) {
            tvNum.setText("进行中");
            tvNum.setTextColor(mContext.getResources().getColor(R.color.teal_200));
        }else {
            tvNum.setText("已结束");
            tvNum.setTextColor(mContext.getResources().getColor(R.color.gray3));
        }


    }
}
