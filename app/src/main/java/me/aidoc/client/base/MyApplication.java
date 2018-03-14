package me.aidoc.client.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import com.pgyersdk.crash.PgyCrashManager;
import com.umeng.analytics.MobclickAgent;

import org.simple.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import me.aidoc.client.entity.resp.UserResp;
import me.aidoc.client.mvp.mine.login.LoginActivity;
import me.aidoc.client.util.L;
import me.aidoc.client.util.LocaleUtils;
import me.aidoc.client.util.MyActivityManager;
import me.aidoc.client.util.MyConstants;
import me.aidoc.client.util.UserUtil;
import me.aidoc.client.base.frame.Mvp;

public class MyApplication extends Application {

    private static MyApplication application;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Mvp.getInstance().init(this);
        registerActivityLife();
        initLanguage();
        // 初始化Umeng
        initUmeng();
        initPgy();
    }

    private void initPgy() {
        PgyCrashManager.register(this);
    }

    private void initLanguage() {
        Locale _UserLocale= LocaleUtils.getUserLocale(this);
        LocaleUtils.updateLocale(this, _UserLocale);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Locale _UserLocale=LocaleUtils.getUserLocale(this);
        //系统语言改变了应用保持之前设置的语言
        if (_UserLocale != null) {
            Locale.setDefault(_UserLocale);
            Configuration _Configuration = new Configuration(newConfig);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                _Configuration.setLocale(_UserLocale);
            } else {
                _Configuration.locale =_UserLocale;
            }
            getResources().updateConfiguration(_Configuration, getResources().getDisplayMetrics());
        }
    }
    private void initUmeng() {
        MobclickAgent.setScenarioType(this,  MobclickAgent.EScenarioType.E_UM_NORMAL);
        L.e("有盟初始化完成");
    }

    private void registerActivityLife() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                MyActivityManager.getInstance().setCurrentActivity(activity);
//                L.e("当前Activity->"+activity.getComponentName()+","+activity.getLocalClassName()+","+activity.getCallingPackage());
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    public static String getToken() {
        UserResp currentUser = UserUtil.getUser();
        if (currentUser == null)
            return "";
        return currentUser.getToken();
    }

    static Date curDate;
    public static void goLogin() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date curDate = new Date(System.currentTimeMillis());
//        //PROCESSING
//        Date endDate = new Date(System.currentTimeMillis());
//        long diff = endDate.getTime() - curDate.getTime();

        // 3秒内不再能调用
        if(curDate==null){
            curDate = new Date(System.currentTimeMillis());
        }else{
            Date endDate = new Date(System.currentTimeMillis());
            long diff = endDate.getTime() - curDate.getTime();
            curDate = new Date(System.currentTimeMillis());
            if(diff<3*1000){//两次调用此方法小于3秒
                L.e("两次调用glLogin方法小于3秒，为"+diff+"毫秒");
                return;
            }
        }

        // 判断Activity是否在运行中
        if (!MyActivityManager.getInstance().getCurrentActivity().getLocalClassName().equals("mvp.login.LoginActivity")) {
            L.e("Application 打开LoginActivity，当前是--》"+MyActivityManager.getInstance().getCurrentActivity().getLocalClassName());
            Intent mIntent = new Intent();
            mIntent.setClass(application, LoginActivity.class);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            EventBus.getDefault().post("", MyConstants.TAG_FINISH_ALL_ACTIVITY);
            application.startActivity(mIntent);
        }else{
            L.e("当前页面是LoginActivity，不再打开");
        }

    }
    public static void goMain() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date curDate = new Date(System.currentTimeMillis());
//        //PROCESSING
//        Date endDate = new Date(System.currentTimeMillis());
//        long diff = endDate.getTime() - curDate.getTime();

        // 3秒内不再能调用
        if(curDate==null){
            curDate = new Date(System.currentTimeMillis());
        }else{
            Date endDate = new Date(System.currentTimeMillis());
            long diff = endDate.getTime() - curDate.getTime();
            curDate = new Date(System.currentTimeMillis());
            if(diff<3*1000){//两次调用此方法小于3秒
                L.e("两次调用glLogin方法小于3秒，为"+diff+"毫秒");
                return;
            }
        }

        // 判断Activity是否在运行中
        if (!MyActivityManager.getInstance().getCurrentActivity().getLocalClassName().equals("mvp.main.MainActivity")) {
            L.e("Application 打开MainActivity，当前是--》"+MyActivityManager.getInstance().getCurrentActivity().getLocalClassName());
            Intent mIntent = new Intent();
            mIntent.setClass(application, LoginActivity.class);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            EventBus.getDefault().post("", MyConstants.TAG_FINISH_ALL_ACTIVITY);
            application.startActivity(mIntent);
        }else{
            L.e("当前页面是MainActivity，不再打开");
        }

    }

    public static Context getContext() {
        return application;
    }
}
