package com.example.temperatureandhumiditytesting.activity.common;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.temperatureandhumiditytesting.R;
import com.example.temperatureandhumiditytesting.base.App;
import com.example.temperatureandhumiditytesting.base.AppUrl;
import com.example.temperatureandhumiditytesting.base.BaseActivity;
import com.example.temperatureandhumiditytesting.bean.TokenBean;
import com.example.temperatureandhumiditytesting.bean.User;
import com.example.temperatureandhumiditytesting.myhttp.MyHttpUtils;
import com.example.temperatureandhumiditytesting.myhttp.RequestCallBack;
import com.example.temperatureandhumiditytesting.utils.MyEvent;
import com.example.temperatureandhumiditytesting.utils.PhotoUtils;
import com.example.temperatureandhumiditytesting.utils.PrefUtils;
import com.example.temperatureandhumiditytesting.view.GlideEngine;
import com.example.temperatureandhumiditytesting.view.ImageCropEngine;
import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.utils.ToastUtils;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


public class ChangeBackImgActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.ivBeiJing)
    ImageView ivBeiJing;    @BindView(R.id.btChange)
    Button btChange;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private Uri cropImageUri;
    private Bitmap bitmap;
    private UploadManager uploadManager;

    @Override
    protected int initContentView() {
        return R.layout.activity_change_beijing;
    }

    public static void forward(Context context) {
        Intent intent = new Intent(App.context, ChangeBackImgActivity.class);
        context.startActivity(intent);
    }
    @Override
    public void onResume() {
        super.onResume();
        int chooseColor = PrefUtils.getInt(App.context, "choose_color", 0);
        if (chooseColor == 0) {
            rlTitle.setBackgroundColor(getResources().getColor(R.color.text4));
            btChange.setBackgroundColor(getResources().getColor(R.color.text4));
        } else {
            rlTitle.setBackgroundColor(chooseColor);
            btChange.setBackgroundColor(chooseColor);
        }
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();
        tvTitle.setText("背景图");
        uploadManager = new UploadManager();
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        User user = BmobUser.getCurrentUser(User.class);
        String country = user.getBeijingtu();
        if (!TextUtils.isEmpty(country)) {
            Picasso.with(ChangeBackImgActivity.this).load(country)
                    .placeholder(R.mipmap.bg_zhanweitu).error(R.mipmap.bg_zhanweitu).into(ivBeiJing);
        }
    }


    @OnClick({R.id.ll_common_back, R.id.btChange})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.btChange:
                autoObtainStoragePermission(ivBeiJing);
                break;
        }
    }

    /**
     * 自动获取sdk权限
     */
    private void autoObtainStoragePermission(ImageView customShapeImageView) {
        if (ContextCompat.checkSelfPermission(ChangeBackImgActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ChangeBackImgActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            //添加凭证图片
            PictureSelector.create(ChangeBackImgActivity.this)
                    .openGallery(SelectMimeType.ofImage())
                    .setMaxSelectNum(1)// 最大图片选择数量 int
                    .setImageEngine(GlideEngine.createGlideEngine())
                    .setMinSelectNum(1)
                    .isDisplayCamera(true)// 是否裁剪 true or false
                    .setCropEngine(new ImageCropEngine()) // 是否裁剪 true or false
                    .forResult(new OnResultCallbackListener<LocalMedia>() {
                        @Override
                        public void onResult(ArrayList<LocalMedia> result) {
                            fileCropUri = new File(result.get(0).getCutPath());
                            cropImageUri = Uri.fromFile(fileCropUri);
                            bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, ChangeBackImgActivity.this);
                            String key_logo = "pic_" + Calendar.getInstance().getTimeInMillis() + ".jpg";
                            if (bitmap != null) {
                                customShapeImageView.setImageBitmap(bitmap);
                                getTokenFromService(key_logo);

                            }
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
        }

    }

    private void getTokenFromService(final String key) {
        Map<String, String> map = new HashMap<>();
        map.put("key", key);
        MyHttpUtils.postHttpMessage(AppUrl.TOUXIANG, map, TokenBean.class, new RequestCallBack<TokenBean>() {
            @Override
            public void requestSuccess(TokenBean json) {
                if (200 == json.getCode()) {
                    uploadManager.put(fileCropUri, key, "" + json.getContent().getToken(), new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject response) {
                            if (info.isOK()) {
                                final User user = BmobUser.getCurrentUser(User.class);
                                user.setBeijingtu(AppUrl.QiNiu+key);
                                user.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            finish();
                                            EventBus.getDefault().post(new MyEvent("更换背景成功"));
                                            ToastUtils.showToast(ChangeBackImgActivity.this, "修改成功！");
                                        }
                                    }
                                });

                            }
                        }

                    }, null);
                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {
            }
        });
    }

}
