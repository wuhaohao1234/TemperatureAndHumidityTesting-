package com.example.temperatureandhumiditytesting.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.temperatureandhumiditytesting.R;
import com.example.temperatureandhumiditytesting.bean.Word;


public class WordListAdapter extends BaseQuickAdapter<Word, BaseViewHolder> {
    public WordListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Word item) {
        helper.setText(R.id.tvName,item.getWord());
        helper.setText(R.id.tvTime,""+item.getCreatedAt()+"添加");
        helper.addOnClickListener(R.id.tvDelete);

    }
}
