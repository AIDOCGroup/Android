package me.aidoc.client.activity;

import android.widget.Toast;

import com.zhuoting.health.MyApplication;

/**
 * Created by dingjikerbo on 2016/9/6.
 */
public class CommonUtils {

    public static void toast(String text) {
        Toast.makeText(MyApplication.getInstance(), text, Toast.LENGTH_SHORT).show();
    }
}
