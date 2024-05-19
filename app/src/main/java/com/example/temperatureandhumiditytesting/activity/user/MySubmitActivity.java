package com.example.temperatureandhumiditytesting.activity.user;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.temperatureandhumiditytesting.R;
import com.example.temperatureandhumiditytesting.adapter.TeamsListAdapter;
import com.example.temperatureandhumiditytesting.base.App;
import com.example.temperatureandhumiditytesting.base.BaseActivity;
import com.example.temperatureandhumiditytesting.bean.Teams;
import com.example.temperatureandhumiditytesting.bean.User;
import com.example.temperatureandhumiditytesting.utils.DialogNewUtils;
import com.example.temperatureandhumiditytesting.utils.MyEvent;
import com.example.temperatureandhumiditytesting.utils.PrefUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.utils.ToastUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;


public class MySubmitActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private TeamsListAdapter videoListAdapter;

    @Override
    protected int initContentView() {
        return R.layout.fragment_two;
    }

    public static void forward(Context context) {
        Intent intent = new Intent(App.context, MySubmitActivity.class);
        context.startActivity(intent);
    }
    @Override
    public void onResume() {
        super.onResume();
        int chooseColor = PrefUtils.getInt(App.context, "choose_color", 0);
        if (chooseColor == 0) {
            rlTitle.setBackgroundColor(getResources().getColor(R.color.text4));
        } else {
            rlTitle.setBackgroundColor(chooseColor);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe
    public void onEventMainThread(MyEvent event) {
        String msg = event.getMsg();
        if (TextUtils.equals("刷新队伍", msg)) {
            getInfo();
        }


    }
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();
        tvTitle.setText("我的组队");
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        videoListAdapter= new TeamsListAdapter(R.layout.item_teams_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(MySubmitActivity.this));
        videoListAdapter.setEmptyView(LayoutInflater.from(MySubmitActivity.this).inflate(R.layout.empty_layout, null));
        recyclerView.setAdapter(videoListAdapter);
    }

    @Override
    protected void initListener() {
        videoListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Teams fitnessCircle = (Teams) adapter.getItem(position);
                if (fitnessCircle!=null) {
                    String objectId = fitnessCircle.getObjectId();
                    if (!TextUtils.isEmpty(objectId)) {
                    }
                }
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getInfo();
                refreshLayout.finishRefresh();
            }
        });
        videoListAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                Teams fitnessCircle = (Teams) adapter.getItem(position);
                if (fitnessCircle != null) {
                    String objectId = fitnessCircle.getObjectId();
                    if (!TextUtils.isEmpty(objectId)) {
                        delete(objectId);
                    }
                }

                return false;
            }
        });
    }
    private void delete(String id) {
        new DialogNewUtils.Builder(MySubmitActivity.this, false, false, "是否删除该组队？",
                "删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showProgressDialog(false);
                Teams fitnessCircle = new Teams();
                fitnessCircle.delete(id, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            ToastUtils.showToast(MySubmitActivity.this, "删除成功！");
                            getInfo();
                            dialogInterface.dismiss();

                        } else {
                            ToastUtils.showToast(MySubmitActivity.this, "删除失败！" + e.getMessage());
                        }
                        stopProgressDialog();

                    }
                });


            }
        }, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create(0).show();
    }
    @Override
    protected void initData() {
        getInfo();
    }

    private void getInfo() {
        BmobQuery<Teams> query = new BmobQuery<>();
        query.addWhereEqualTo("author", BmobUser.getCurrentUser(User.class));
        query.order("-updatedAt");
        //包含作者信息
        query.include("author");
        query.findObjects(new FindListener<Teams>() {

            @Override
            public void done(List<Teams> object, BmobException e) {
                if (e == null) {
                    videoListAdapter.setNewData(object);
                    videoListAdapter.notifyDataSetChanged();
                } else {
                    Log.e("BMOB", e.toString());
                }
            }
        });
    }

    @OnClick(R.id.ll_common_back)
    public void onClick() {
        finish();
    }
}
