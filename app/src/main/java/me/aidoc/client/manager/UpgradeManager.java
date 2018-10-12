package me.aidoc.client.manager;

import android.app.Activity;
import android.content.Context;

public class UpgradeManager {
    public static final int INVALIDATE_VERSION_CODE = -1;

    public static final int VERSION_1_1_0 = 110;
    public static final int VERSION_1_1_1 = 111;
    public static final int VERSION_1_1_2 = 112;
    public static final int VERSION_1_1_3 = 113;
    public static final int VERSION_1_1_4 = 114;
    public static final int VERSION_1_1_5 = 115;
    public static final int VERSION_1_1_6 = 116;
    public static final int VERSION_1_1_7 = 117;
    private static final int sCurrentVersion = VERSION_1_1_7;
    private static UpgradeManager sUpgradeManager = null;
    private UpgradeTipTask mUpgradeTipTask;

    private Context mContext;

    private UpgradeManager(Context context) {
        mContext = context.getApplicationContext();
    }

    public static final UpgradeManager getInstance(Context context) {
        if (sUpgradeManager == null) {
            sUpgradeManager = new UpgradeManager(context);
        }
        return sUpgradeManager;
    }

    public void check() {
        int oldVersion = PreferenceManager.getInstance(mContext).getVersionCode();
        int currentVersion = sCurrentVersion;
        if (currentVersion > oldVersion) {
            if (oldVersion == INVALIDATE_VERSION_CODE) {
                onNewInstall(currentVersion);
            } else {
                onUpgrade(oldVersion, currentVersion);
            }
            PreferenceManager.getInstance(mContext).setAppVersionCode(currentVersion);
        }
    }

    private void onUpgrade(int oldVersion, int currentVersion) {
        mUpgradeTipTask = new UpgradeTipTask(oldVersion, currentVersion);
    }

    private void onNewInstall(int currentVersion) {
        mUpgradeTipTask = new UpgradeTipTask(INVALIDATE_VERSION_CODE, currentVersion);
    }

    public void runUpgradeTipTaskIfExist(Activity activity) {
        if (mUpgradeTipTask != null) {
            mUpgradeTipTask.upgrade(activity);
            mUpgradeTipTask = null;
        }
    }
}
