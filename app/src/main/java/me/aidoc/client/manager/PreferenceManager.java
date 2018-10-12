package me.aidoc.client.manager;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private static SharedPreferences sPreferences;
    private static PreferenceManager sPreferenceManager = null;

    private static final String APP_VERSION_CODE = "app_version_code";

    private PreferenceManager(Context context) {
        sPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    public static final PreferenceManager getInstance(Context context) {
        if (sPreferenceManager == null) {
            sPreferenceManager = new PreferenceManager(context);
        }
        return sPreferenceManager;
    }

    public void setAppVersionCode(int code) {
        final SharedPreferences.Editor editor = sPreferences.edit();
        editor.putInt(APP_VERSION_CODE, code);
        editor.apply();
    }

    public int getVersionCode() {
        return sPreferences.getInt(APP_VERSION_CODE, UpgradeManager.INVALIDATE_VERSION_CODE);
    }
}
