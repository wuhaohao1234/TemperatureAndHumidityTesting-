package com.example.temperatureandhumiditytesting.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.temperatureandhumiditytesting.R;
import com.example.temperatureandhumiditytesting.bean.User;
import com.example.temperatureandhumiditytesting.view.CircleImageView;
import com.squareup.picasso.Picasso;

public class UserListAdapter extends BaseQuickAdapter<User, BaseViewHolder> {
    public UserListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, User item) {
        CircleImageView ivHead = helper.getView(R.id.ivHead);
        TextView tvSex = helper.getView(R.id.tvSex);
        String nickname = item.getNickname();
        int gender = item.getGender();
        if (!TextUtils.isEmpty(nickname)) {
            helper.setText(R.id.tvName,nickname);
        } else {
            helper.setText(R.id.tvName,item.getObjectId());
        }
        helper.setText(R.id.tvTime,item.getCreatedAt());
        helper.setText(R.id.tvAge,item.getAge()+ "岁");
        Picasso.with(mContext).load(item.getHead()).placeholder(R.mipmap.bg_zhanweitu)
                .error(R.mipmap.bg_zhanweitu).into(ivHead);
        if (gender == 1) {
            tvSex.setText("男");
        } else if (gender == 0) {
            tvSex.setText("女");
        } else {
            tvSex.setText("待定");
        }

    }
}
