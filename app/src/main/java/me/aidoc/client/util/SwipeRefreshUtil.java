package me.aidoc.client.util;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;

import me.aidoc.client.R;


/**
 * Created by zlc on 2017/1/13.
 * 刷新工具类
 */

public class SwipeRefreshUtil {

    public static void setSiwpeLayout(SwipeRefreshLayout siwpeLayout, Context context, SwipeRefreshLayout.OnRefreshListener listener) {

        //1 设置进度条的颜色主题，最多能设置四种 加载颜色是循环播放的，只要没有完成刷新就会一直循环
        siwpeLayout.setColorSchemeColors(
                context.getResources().getColor(R.color.black)
        );
        //2 设置圆圈大小
        siwpeLayout.setSize(DensityUtil.dp2px(context,50));
        //3 设置圆圈背景颜色
        siwpeLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        //4 设置下拉刷新的监听
        siwpeLayout.setOnRefreshListener(listener);
    }
}
