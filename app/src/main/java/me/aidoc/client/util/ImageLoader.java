package me.aidoc.client.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import json.chao.com.wanandroid.app.GeeksApp;


/**
 * @author quchao
 * @date 2018/07/03
 */
public class ImageLoader {

    /**
     * 使用Glide加载圆形ImageView(如头像)时，不要使用占位图
     *
     * @param context context
     * @param url image url
     * @param iv imageView
     */
    public static void load(Context context, String url, ImageView iv) {
        if (!GeeksApp.getAppComponent().preferencesHelper().getNoImageState()) {
            Glide.with(context).load(url).into(iv);
        }
    }
}
