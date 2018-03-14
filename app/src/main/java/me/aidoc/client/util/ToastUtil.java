package me.aidoc.client.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Toast类
 */

public class ToastUtil {
    private ToastUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void toast(Context context, String info) {
        if (context == null || TextUtils.isEmpty(info)) return;
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
    }
}
