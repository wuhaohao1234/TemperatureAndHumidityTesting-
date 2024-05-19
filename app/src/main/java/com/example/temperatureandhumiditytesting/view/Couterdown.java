package com.example.temperatureandhumiditytesting.view;

import android.os.CountDownTimer;

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
 * #            Created by 張某人 on 2023/2/20/09:22 .
 * #            com.example.book.view
 * #            APP
 */
public class Couterdown extends CountDownTimer {
    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public Couterdown(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {

    }

    @Override
    public void onFinish() {

    }
    public String toClock(long millisUntilFinished)

    {
        long hour = millisUntilFinished/(60*60*1000);
        long minute = (millisUntilFinished - hour*60*60*1000)/(60*1000);
        long second = (millisUntilFinished - hour*60*60*1000   - minute*60*1000)/1000;
        if(second >= 60 )
        {
            second = second % 60;
            minute+=second/60;
        }
        if(minute >= 60)
        {
            minute = minute %60;
            hour += minute/60;
        }
        String sh = "";
        String sm ="";
        String ss = "";
        if(hour <10)
        {
            sh = "0" + String.valueOf(hour);
        }else
        {
            sh = String.valueOf(hour);
        }
        if(minute <10)
        {
            sm = "0" + String.valueOf(minute);
        }else
        {
            sm = String.valueOf(minute);
        }
        if(second <10)
        {
            ss = "0" + String.valueOf(second);
        }else
        {
            ss = String.valueOf(second);
        }
        return  sm+":" + ss;
    }
}
