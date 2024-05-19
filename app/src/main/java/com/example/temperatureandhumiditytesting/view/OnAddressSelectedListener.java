package com.example.temperatureandhumiditytesting.view;

import com.example.temperatureandhumiditytesting.bean.City;
import com.example.temperatureandhumiditytesting.bean.County;
import com.example.temperatureandhumiditytesting.bean.Province;
import com.example.temperatureandhumiditytesting.bean.Street;

/**
 * * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #            APP
 */
public interface OnAddressSelectedListener {
    // 获取地址完成回调
    void onAddressSelected(Province province, City city, County county, Street street);
    // 选取省份完成回调
    void onProvinceSelected(Province province);
    // 选取城市完成回调
    void onCitySelected(City city);
    // 选取区/县完成回调
    void onCountySelected(County county);
}
