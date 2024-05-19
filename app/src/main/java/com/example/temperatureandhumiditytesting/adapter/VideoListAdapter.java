package com.example.temperatureandhumiditytesting.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.temperatureandhumiditytesting.R;
import com.example.temperatureandhumiditytesting.bean.Video;
import com.example.temperatureandhumiditytesting.view.CustomShapeImageView;
import com.squareup.picasso.Picasso;

public class VideoListAdapter extends BaseQuickAdapter<Video, BaseViewHolder> {
    public VideoListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Video item) {
        CustomShapeImageView iv_fengmian = helper.getView(R.id.iv_fengmian);
        helper.setText(R.id.tvTime,"发布于："+item.getCreatedAt());
        helper.setText(R.id.tvTitle,item.getTitle());
        helper.setText(R.id.tvContent,item.getContent());
        Picasso.with(mContext).load(item.getImg_url()).placeholder(R.mipmap.bg_zhanweitu)
                .error(R.mipmap.bg_zhanweitu).into(iv_fengmian);
    }
}
