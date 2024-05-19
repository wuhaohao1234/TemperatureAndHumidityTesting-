package com.example.temperatureandhumiditytesting.activity.common;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.temperatureandhumiditytesting.R;
import com.example.temperatureandhumiditytesting.base.App;
import com.example.temperatureandhumiditytesting.base.BaseActivity;
import com.example.temperatureandhumiditytesting.utils.DialogNewUtils;
import com.example.temperatureandhumiditytesting.utils.PrefUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.utils.ToastUtils;
import com.shixia.colorpickerview.ColorPickerView;
import com.shixia.colorpickerview.OnColorChangeListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ChangeColorActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.view_color)
    View view_color;
    @BindView(R.id.cpv_color_picker)
    ColorPickerView colorPicker;
    @BindView(R.id.btChange)
    Button btChange;
    private int choose_color;

    @Override
    protected int initContentView() {
        return R.layout.activity_change_color;
    }

    public static void forward(Context context) {
        Intent intent = new Intent(App.context, ChangeColorActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();
        tvTitle.setText("设置主题颜色");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        int chooseColor = PrefUtils.getInt(App.context, "choose_color", 0);
        if (chooseColor==0) {
            btChange.setBackgroundColor(getResources().getColor(R.color.text4));
        }else {
            btChange.setBackgroundColor(chooseColor);
        }
        colorPicker.setOnColorChangeListener(new OnColorChangeListener() {
            @Override
            public void colorChanged(int color) {
                choose_color = color;
                view_color.setBackgroundColor(color);
                btChange.setBackgroundColor(color);
            }
        });
    }

    @OnClick({R.id.ll_common_back, R.id.btChange})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.btChange:
                tuichu("是否选择该主题颜色？", "选择");
                break;

        }
    }

    private void tuichu(String title, String button) {
        new DialogNewUtils.Builder(ChangeColorActivity.this, false, false, title,
                button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ToastUtils.showToast(ChangeColorActivity.this,"更改成功");
                PrefUtils.putInt(ChangeColorActivity.this, "choose_color", choose_color);
                dialogInterface.dismiss();
            }
        }, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create(0).show();
    }

}
