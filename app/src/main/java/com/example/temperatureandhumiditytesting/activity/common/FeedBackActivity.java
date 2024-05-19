package com.example.temperatureandhumiditytesting.activity.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.temperatureandhumiditytesting.R;
import com.example.temperatureandhumiditytesting.adapter.GridImageAdapter;
import com.example.temperatureandhumiditytesting.base.App;
import com.example.temperatureandhumiditytesting.base.AppUrl;
import com.example.temperatureandhumiditytesting.base.BaseActivity;
import com.example.temperatureandhumiditytesting.bean.FeedBack;
import com.example.temperatureandhumiditytesting.bean.TokenBean;
import com.example.temperatureandhumiditytesting.bean.User;
import com.example.temperatureandhumiditytesting.myhttp.MyHttpUtils;
import com.example.temperatureandhumiditytesting.myhttp.RequestCallBack;
import com.example.temperatureandhumiditytesting.utils.PrefUtils;
import com.example.temperatureandhumiditytesting.utils.ScreenUtils;
import com.example.temperatureandhumiditytesting.view.DragListener;
import com.example.temperatureandhumiditytesting.view.FullyGridLayoutManager;
import com.example.temperatureandhumiditytesting.view.GlideEngine;
import com.example.temperatureandhumiditytesting.view.MeSandboxFileEngine;
import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.config.SelectModeConfig;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnExternalPreviewEventListener;
import com.luck.picture.lib.style.PictureSelectorStyle;
import com.luck.picture.lib.utils.ToastUtils;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class FeedBackActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvCommonTitle;
    @BindView(R.id.tv_delete_text)
    TextView tvDeleteText;
    @BindView(R.id.etBiaoTi)
    EditText etBiaoTi;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tvFaBu)
    TextView tvFaBu;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    private UploadManager uploadManager;
    @Override
    protected int initContentView() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();
        uploadManager = new UploadManager();
        tvCommonTitle.setText("意见反馈");
        initRecycler();
    }

    public static void forward(Context context) {
        Intent intent = new Intent(App.context, FeedBackActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initListener() {

    }
    @Override
    public void onResume() {
        super.onResume();
        int chooseColor = PrefUtils.getInt(App.context, "choose_color", 0);
        if (chooseColor == 0) {
            rlTitle.setBackgroundColor(getResources().getColor(R.color.text4));
            tvFaBu.setBackgroundColor(getResources().getColor(R.color.text4));
        } else {
            rlTitle.setBackgroundColor(chooseColor);
            tvFaBu.setBackgroundColor(chooseColor);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    protected void initData() {
    }

    @OnClick({R.id.ll_common_back, R.id.tvFaBu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.tvFaBu:
                fabuPost();

                break;
        }
    }

    private void fabuPost() {
        String biaoti = etBiaoTi.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(biaoti)) {
            Toast.makeText(App.context, "请填写反馈意见！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(App.context, "请填写手机号！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectList.size() == 0) {
            Toast.makeText(App.context, "请至少添加一张图片！", Toast.LENGTH_SHORT).show();
            return;
        }

        getTokenFromService(biaoti,phone);

    }

    private ArrayList<LocalMedia> selectList = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (intent != null) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainSelectorList(intent);
                    mAdapter.setList(selectList);
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }

    }

    Map<Integer, String> pictureMap = new HashMap<>();  //图片上传的顺序


    private void upLoadPicToQiNiu(final String key, final int i, String content, String phone) {
        showProgressDialog(false, "正在发布...");
        Map<String, String> params = new HashMap<>();
        params.put("key", key);
        MyHttpUtils.postHttpMessage(AppUrl.TOUXIANG, params, TokenBean.class, new RequestCallBack<TokenBean>() {
            @Override
            public void requestSuccess(TokenBean json) {
                if (json.getCode() == 200) {
                    String token = json.getContent().getToken();
                    if (!TextUtils.isEmpty(token)) {
                        File fileCropUri;
                        if (TextUtils.isEmpty(selectList.get(i).getCompressPath())) {
                            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                                fileCropUri = new File(selectList.get(i).getSandboxPath());
                            } else {
                                fileCropUri = new File(selectList.get(i).getRealPath());
                            }
                        } else {
                            fileCropUri = new File(selectList.get(i).getCompressPath());
                        }
                        uploadManager.put(fileCropUri, key, token, new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject response) {
                                if (info.isOK()) {
                                    pictureMap.put(i, AppUrl.QiNiu + key);
                                    Log.e("qiniu", "Upload Success" + key);
                                    if (selectList.size() == pictureMap.size()) {
                                        String multi_graph = "";
                                        Iterator<Integer> iterator = pictureMap.keySet().iterator();
                                        while (iterator.hasNext()) {
                                            multi_graph += pictureMap.get(iterator.next()) + ",";
                                        }
                                        String substring = multi_graph.substring(0, multi_graph.length() - 1);
                                        //全部上传完了  发送数据
                                        save(content, phone, substring);
                                    }
                                } else {
                                    stopProgressDialog();
                                    String error = info.error;
                                    Log.e("qiniu", "Upload 失败" + key + "response" + response);
                                    showToastShort("上传失败,请重新尝试!");
                                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                                }
                            }

                        }, null);
                    }
                }

            }

            @Override
            public void requestError(String errorMsg, int errorType) {
                stopProgressDialog();
                Log.e("qiniu", "errorMsg 失败" + key + "   errorMsg   " + errorMsg);
//                String error = info.error;
                showToastShort("上传失败,请重新尝试2!");
            }
        });
    }

    /**
     * 新增一个对象
     */
    private void save(String content, String phone, String imgs) {
        FeedBack category = new FeedBack();
        category.setContent(content);
        category.setPhone(phone);
        category.setImgs(imgs);
        category.setAuthor(BmobUser.getCurrentUser(User.class));
        category.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    ToastUtils.showToast(FeedBackActivity.this, "提交成功！");
                    finish();
                }
            }
        });
    }

    private void getTokenFromService(String content, String phone) {
        for (int i = 0; i < selectList.size(); i++) {
            Calendar calendar = Calendar.getInstance();
            long timeInMillis = calendar.getTimeInMillis();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            upLoadPicToQiNiu("zhinengchuanda_pic_" + timeInMillis + ".jpg", i, content, phone);

        }
    }

    private boolean isUpward;
    private boolean needScaleBig = true;
    private boolean needScaleSmall = true;
    private GridImageAdapter mAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private DragListener mDragListener;


    /**
     * 外部预览监听事件
     */
    private static class MyExternalPreviewEventListener implements OnExternalPreviewEventListener {
        private final GridImageAdapter adapter;

        public MyExternalPreviewEventListener(GridImageAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public void onPreviewDelete(int position) {
            adapter.remove(position);
            adapter.notifyItemRemoved(position);
        }

        @Override
        public boolean onLongPressDownload(LocalMedia media) {
            return false;
        }
    }

    /**
     * 可以拖拽的 相册选择
     */
    private final List<LocalMedia> mData = new ArrayList<>();
    private void initRecycler() {
        RecyclerView mRecyclerView = findViewById(R.id.recycler);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                3, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3, ScreenUtils.dip2px(FeedBackActivity.this, 8), false));
        mAdapter = new GridImageAdapter(FeedBackActivity.this, mData);

        mAdapter.setSelectMax(9);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                // 预览图片、视频、音频
                PictureSelector.create(FeedBackActivity.this)
                        .openPreview()
                        .setImageEngine(GlideEngine.createGlideEngine())
                        .setSelectorUIStyle(selectorStyle)
                        .isPreviewFullScreenMode(true)
                        .setExternalPreviewEventListener(new MyExternalPreviewEventListener(mAdapter))
                        .startActivityPreview(position, true, mAdapter.getData());
            }

            @Override
            public void openPicture() {
                PictureSelector.create(FeedBackActivity.this)
                        .openGallery(SelectMimeType.ofImage())
                        .setImageEngine(GlideEngine.createGlideEngine())
                        .setSelectorUIStyle(selectorStyle)
                        .setSandboxFileEngine(new MeSandboxFileEngine())//沙盒
//                        .setCameraInterceptListener(new MeOnCameraInterceptListener())//自定义相机
                        .isAutoVideoPlay(true)//预览视频自动播放
                        .isLoopAutoVideoPlay(true)
                        .setSelectionMode(SelectModeConfig.MULTIPLE)//单选 or 多选
                        .isWithSelectVideoImage(false)//视频图片同选
                        .isPreviewFullScreenMode(true)
                        .isPreviewImage(true)
                        .isPreviewVideo(true)
                        .isDisplayCamera(true)  //显示拍摄
                        .isFastSlidingSelect(true) //滑动选择
                        .isMaxSelectEnabledMask(true)//达到最大数量蒙层
                        .isPageStrategy(true)
                        .setMaxSelectNum(9)
                        .setMaxVideoSelectNum(1)
                        .setSelectedData(mAdapter.getData())
                        .forResult(PictureConfig.CHOOSE_REQUEST);


            }
        });
        mAdapter.setItemLongClickListener((holder, position, v) -> {
            //如果item不是最后一个，则执行拖拽
            needScaleBig = true;
            needScaleSmall = true;
            int size = mAdapter.getData().size();
            if (size != 9) {
                mItemTouchHelper.startDrag(holder);
                return;
            }
            if (holder.getLayoutPosition() != size - 1) {
                mItemTouchHelper.startDrag(holder);
            }
        });

        mDragListener = new DragListener() {
            @Override
            public void deleteState(boolean isDelete) {
                if (isDelete) {
                    tvDeleteText.setText(getString(R.string.app_let_go_drag_delete));
                    tvDeleteText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ic_let_go_delete, 0, 0);
                } else {
                    tvDeleteText.setText(getString(R.string.app_drag_delete));
                    tvDeleteText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.picture_icon_delete, 0, 0);
                }

            }

            @Override
            public void dragState(boolean isStart) {
                int visibility = tvDeleteText.getVisibility();
                if (isStart) {
                    if (visibility == View.GONE) {
                        tvDeleteText.animate().alpha(1).setDuration(300).setInterpolator(new AccelerateInterpolator());
                        tvDeleteText.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (visibility == View.VISIBLE) {
                        tvDeleteText.animate().alpha(0).setDuration(300).setInterpolator(new AccelerateInterpolator());
                        tvDeleteText.setVisibility(View.GONE);
                    }
                }
            }
        };
        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            }

            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int itemViewType = viewHolder.getItemViewType();
                if (itemViewType != GridImageAdapter.TYPE_CAMERA) {
                    viewHolder.itemView.setAlpha(0.7f);
                }
                return makeMovementFlags(ItemTouchHelper.DOWN | ItemTouchHelper.UP
                        | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, 0);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                //得到item原来的position
                try {
                    int fromPosition = viewHolder.getAdapterPosition();
                    //得到目标position
                    int toPosition = target.getAdapterPosition();
                    int itemViewType = target.getItemViewType();
                    if (itemViewType != GridImageAdapter.TYPE_CAMERA) {
                        if (fromPosition < toPosition) {
                            for (int i = fromPosition; i < toPosition; i++) {
                                Collections.swap(mAdapter.getData(), i, i + 1);
                            }
                        } else {
                            for (int i = fromPosition; i > toPosition; i--) {
                                Collections.swap(mAdapter.getData(), i, i - 1);
                            }
                        }
                        mAdapter.notifyItemMoved(fromPosition, toPosition);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                    @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                int itemViewType = viewHolder.getItemViewType();
                if (itemViewType != GridImageAdapter.TYPE_CAMERA) {
                    if (null == mDragListener) {
                        return;
                    }
                    if (needScaleBig) {
                        //如果需要执行放大动画
                        viewHolder.itemView.animate().scaleXBy(0.1f).scaleYBy(0.1f).setDuration(100);
                        //执行完成放大动画,标记改掉
                        needScaleBig = false;
                        //默认不需要执行缩小动画，当执行完成放大 并且松手后才允许执行
                        needScaleSmall = false;
                    }
                    int sh = recyclerView.getHeight() + tvDeleteText.getHeight();
                    int ry = tvDeleteText.getBottom() - sh;
                    if (dY >= ry) {
                        //拖到删除处
                        mDragListener.deleteState(true);
                        if (isUpward) {
                            //在删除处放手，则删除item
                            viewHolder.itemView.setVisibility(View.INVISIBLE);
                            mAdapter.delete(viewHolder.getAdapterPosition());
                            resetState();
                            return;
                        }
                    } else {//没有到删除处
                        if (View.INVISIBLE == viewHolder.itemView.getVisibility()) {
                            //如果viewHolder不可见，则表示用户放手，重置删除区域状态
                            mDragListener.dragState(false);
                        }
                        if (needScaleSmall) {//需要松手后才能执行
                            viewHolder.itemView.animate().scaleXBy(1f).scaleYBy(1f).setDuration(100);
                        }
                        mDragListener.deleteState(false);
                    }
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                int itemViewType = viewHolder != null ? viewHolder.getItemViewType() : GridImageAdapter.TYPE_CAMERA;
                if (itemViewType != GridImageAdapter.TYPE_CAMERA) {
                    if (ItemTouchHelper.ACTION_STATE_DRAG == actionState && mDragListener != null) {
                        mDragListener.dragState(true);
                    }
                    super.onSelectedChanged(viewHolder, actionState);
                }
            }

            @Override
            public long getAnimationDuration(@NonNull RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
                needScaleSmall = true;
                isUpward = true;
                return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy);
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int itemViewType = viewHolder.getItemViewType();
                if (itemViewType != GridImageAdapter.TYPE_CAMERA) {
                    viewHolder.itemView.setAlpha(1.0f);
                    super.clearView(recyclerView, viewHolder);
                    mAdapter.notifyDataSetChanged();
                    resetState();
                }
            }
        });

        // 绑定拖拽事件
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    /**
     * 重置
     */
    private void resetState() {
        if (mDragListener != null) {
            mDragListener.deleteState(false);
            mDragListener.dragState(false);
        }
        isUpward = false;
    }


    public Context getContext() {
        return this;
    }


    /**
     * 配制UCrop，可根据需求自我扩展
     *
     * @return
     */
    private PictureSelectorStyle selectorStyle = new PictureSelectorStyle();


}
