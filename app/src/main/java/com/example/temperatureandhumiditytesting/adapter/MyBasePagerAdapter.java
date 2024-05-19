package com.example.temperatureandhumiditytesting.adapter;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.temperatureandhumiditytesting.base.FragmentFactory;
public class MyBasePagerAdapter extends FragmentPagerAdapter {

    public MyBasePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.getShouYe(position);
    }

    @Override
    public int getCount() {
        return FragmentFactory.SI;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}