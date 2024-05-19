package com.example.temperatureandhumiditytesting.view;

import android.content.Context;
import android.text.TextUtils;

import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.engine.SandboxFileEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnCallbackIndexListener;
import com.luck.picture.lib.utils.SandboxTransformUtils;

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
 * #            2/20/22:14 .
 * #            com.example.book.view
 * #            APP
 */
public class MeSandboxFileEngine implements SandboxFileEngine {

    @Override
    public void onStartSandboxFileTransform(Context context, boolean isOriginalImage,
                                            int index, LocalMedia media,
                                            OnCallbackIndexListener<LocalMedia> listener) {
        if (PictureMimeType.isContent(media.getAvailablePath())) {
            String sandboxPath = SandboxTransformUtils.copyPathToSandbox(context, media.getPath(),
                    media.getMimeType());
            media.setSandboxPath(sandboxPath);
        }
        if (isOriginalImage) {
            String originalPath = SandboxTransformUtils.copyPathToSandbox(context, media.getPath(),
                    media.getMimeType());
            media.setOriginalPath(originalPath);
            media.setOriginal(!TextUtils.isEmpty(originalPath));
        }
        listener.onCall(media, index);
    }
}
