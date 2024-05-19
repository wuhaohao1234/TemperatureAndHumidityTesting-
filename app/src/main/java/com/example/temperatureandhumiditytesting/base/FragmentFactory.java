package com.example.temperatureandhumiditytesting.base;

import androidx.fragment.app.Fragment;

import com.example.temperatureandhumiditytesting.fragment.HomeFragment;
import com.example.temperatureandhumiditytesting.fragment.MineFragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentFactory {
    public static final int SI =2 ;
    public static Map<Integer, Fragment> map = new HashMap<>();

    public static Fragment getShouYe(int position) {
        Fragment fragment = map.get(position);
        if (fragment == null) {
            switch (position) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new MineFragment();
                    break;
            }
        }
        return fragment;
    }


}
