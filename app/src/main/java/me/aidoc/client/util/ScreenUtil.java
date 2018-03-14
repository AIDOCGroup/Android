package me.aidoc.client.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by zlc on 2017/2/10.
 */
public class ScreenUtil {

    public static int getScreenWidth(Context context){

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight(Context context){

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }
}
