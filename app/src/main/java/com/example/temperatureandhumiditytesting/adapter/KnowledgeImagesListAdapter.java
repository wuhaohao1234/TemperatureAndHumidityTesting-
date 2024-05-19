package com.example.temperatureandhumiditytesting.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.temperatureandhumiditytesting.R;


public class KnowledgeImagesListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public KnowledgeImagesListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        ImageView imageView = helper.getView(R.id.ivHead);
        Glide.with(mContext).load(item).placeholder(R.mipmap.bg_zhanweitu).error(R.mipmap.bg_zhanweitu).into(imageView);
//        Picasso.with(mContext).load(item).placeholder(R.mipmap.bg_zhanweitu)
//                .error(R.mipmap.bg_zhanweitu).into(imageView);
    }
}
