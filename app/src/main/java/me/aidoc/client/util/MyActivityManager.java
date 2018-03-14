package me.aidoc.client.util;

import android.app.Activity;

import java.lang.ref.WeakReference;

public class MyActivityManager {
    private static MyActivityManager sInstance = new MyActivityManager();
    // 采用弱引用持有 Activity ，避免造成 内存泄露
    private WeakReference<Activity> sCurrentActivityWeakRef;


    private MyActivityManager() {

    }

    public static MyActivityManager getInstance() {
        return sInstance;
    }

    public Activity getCurrentActivity() {
        Activity currentActivity = null;
        if (sCurrentActivityWeakRef != null) {
            currentActivity = sCurrentActivityWeakRef.get();
        }
        return currentActivity;
    }

    public void setCurrentActivity(Activity activity) {
        sCurrentActivityWeakRef = new WeakReference<Activity>(activity);
    }


}