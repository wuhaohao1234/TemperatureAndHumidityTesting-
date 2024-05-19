package com.example.temperatureandhumiditytesting.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.temperatureandhumiditytesting.R;
import com.example.temperatureandhumiditytesting.bean.Comment;
import com.example.temperatureandhumiditytesting.bean.User;
import com.example.temperatureandhumiditytesting.view.CircleImageView;
import com.squareup.picasso.Picasso;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;


public class ManageCommentListAdapter extends BaseQuickAdapter<Comment, BaseViewHolder> {
    public ManageCommentListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Comment item) {
        CircleImageView ivHead = helper.getView(R.id.ivHead);
        TextView tvDelete = helper.getView(R.id.tvDelete);
        tvDelete.setVisibility(View.VISIBLE);
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(item.getAuthor().getObjectId(), new QueryListener<User>() {
            @Override
            public void done(User category, BmobException e) {
                if (e == null) {
                    Picasso.with(mContext).load(category.getHead()).placeholder(R.mipmap.bg_zhanweitu)
                            .error(R.mipmap.bg_zhanweitu).into(ivHead);
                    helper.setText(R.id.tvName,TextUtils.isEmpty(category.getNickname())?category.getObjectId():category.getObjectId());
                }
            }
        });


        helper.setText(R.id.tvContent,item.getContent());
        helper.setText(R.id.tvTime,""+item.getCreatedAt()+"发布");
        helper.addOnClickListener(R.id.ivHead);
        helper.addOnClickListener(R.id.tvDelete);

    }
}
