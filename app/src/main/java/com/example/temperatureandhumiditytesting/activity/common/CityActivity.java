package com.example.temperatureandhumiditytesting.activity.common;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.temperatureandhumiditytesting.R;
import com.example.temperatureandhumiditytesting.base.App;
import com.example.temperatureandhumiditytesting.base.BaseActivity;
import com.example.temperatureandhumiditytesting.bean.CityListBean;
import com.example.temperatureandhumiditytesting.bean.Tb_CityCode;
import com.example.temperatureandhumiditytesting.db.CityDao;
import com.example.temperatureandhumiditytesting.utils.CityCodeManager;
import com.example.temperatureandhumiditytesting.utils.DialogNewUtils;
import com.example.temperatureandhumiditytesting.utils.MyEvent;
import com.example.temperatureandhumiditytesting.utils.PrefUtils;
import com.example.temperatureandhumiditytesting.view.BottomSelectorDialog;
import com.gyf.immersionbar.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CityActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btAdd)
    Button btAdd;
    private BottomSelectorDialog dialog;
    private CityDao cityDao;
    private CityListAdapter cityListAdapter;
    @Override
    protected int initContentView() {
        return R.layout.activity_city;
    }
    public static void forward(Context context) {
        Intent intent = new Intent(App.context, CityActivity.class);
        context.startActivity(intent);
    }
    @Override
    public void onResume() {
        super.onResume();
        int chooseColor = PrefUtils.getInt(App.context, "choose_color", 0);
        if (chooseColor == 0) {
            rlTitle.setBackgroundColor(getResources().getColor(R.color.text4));
            btAdd.setBackgroundColor(getResources().getColor(R.color.text4));
        } else {
            rlTitle.setBackgroundColor(chooseColor);
            btAdd.setBackgroundColor(chooseColor);
        }
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();
        tvTitle.setText("城市选择");
        cityDao = new CityDao(CityActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(CityActivity.this));
        cityListAdapter = new CityListAdapter(R.layout.item_detail);
        recyclerView.setAdapter(cityListAdapter);
        List<CityListBean> all = cityDao.findAll();
        cityListAdapter.setNewData(all);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseCity();
            }
        });
    }

    @Override
    protected void initListener() {
        cityListAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                CityListBean cityListBean = (CityListBean) adapter.getItem(position);
                if (cityListBean!=null) {
                    chuli(cityListBean.getId(),position);
                }
                return false;
            }
        });
        cityListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                CityListBean cityListBean = (CityListBean) adapter.getItem(position);
                if (cityListBean!=null) {
                    PrefUtils.putParameter("location",cityListBean.getName());
                    PrefUtils.putParameter("location_id",""+cityListBean.getId());
                    EventBus.getDefault().post(new MyEvent("刷新城市天气"));
                    Toast.makeText(CityActivity.this, "选择"+cityListBean.getName()+"成功！", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void chuli(String id, int position) {
        new DialogNewUtils.Builder(CityActivity.this, false, false, "是否删除该城市？",
                "删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cityDao.delete(id);
                cityListAdapter.remove(position);

                dialogInterface.dismiss();
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

    }
    private void chooseCity() {
        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<CityListBean> cityListBeans = new ArrayList<>();

        List<Tb_CityCode> tb_cityCodes = CityCodeManager.CityCodeManager();
        for (int i = 0; i < tb_cityCodes.size(); i++) {
            nameList.add(tb_cityCodes.get(i).getCityName());

        }
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(CityActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                Tb_CityCode tb_cityCode = tb_cityCodes.get(options1);
                if (cityDao.findAll().size() >8) {
                    Toast.makeText(CityActivity.this, "最多添加9个城市！！", Toast.LENGTH_SHORT).show();
                } else {
                    if (cityDao.isAdd(""+tb_cityCode.getCityCode(), tb_cityCode.getCityName())) {
                        Toast.makeText(CityActivity.this, "该城市已添加！", Toast.LENGTH_SHORT).show();
                    } else {
                        cityDao.add(""+tb_cityCode.getCityCode(), tb_cityCode.getCityName());
                        cityListAdapter.setNewData(cityDao.findAll());
                    }
                }

            }
        })
                .setTitleText("选择城市")
                .setDividerColor(getResources().getColor(R.color.gray3))
                .setCancelColor(getResources().getColor(R.color._888888))
                .setSubmitColor(getResources().getColor(R.color.red))
                .setTextColorCenter(getResources().getColor(R.color.red)) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(nameList);
        pvOptions.setSelectOptions(0);
        pvOptions.show();
    }
    @OnClick(R.id.ll_common_back)
    public void onClick() {
        finish();
    }
    /**
     * 城市列表适配器
     */
    public class CityListAdapter extends BaseQuickAdapter<CityListBean, BaseViewHolder> {
        public CityListAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, CityListBean item) {
            helper.setText(R.id.tvCity,item.getName());
        }
    }
}
