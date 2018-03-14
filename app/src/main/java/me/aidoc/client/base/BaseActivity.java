package me.aidoc.client.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.umeng.analytics.MobclickAgent;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import me.aidoc.client.base.frame.BaseView;
import me.aidoc.client.util.L;
import me.aidoc.client.util.LocaleUtils;
import me.aidoc.client.util.MyConstants;
import me.aidoc.client.util.Store;
import me.aidoc.client.util.ToastUtil;
import me.aidoc.client.base.frame.MvpActivity;
import me.aidoc.client.base.frame.MvpPresenter;

public abstract class BaseActivity<P extends MvpPresenter> extends MvpActivity<P> implements BaseView {
    ProgressBar pb;
    Dialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        doChageLanguage();
    }

    protected void showLog(String log) {
        L.e(log);
    }

    public void toast(String msg) {
        ToastUtil.toast(this, msg);
    }

    @Override
    public void showLoading() {
        if (pb == null) {
            FrameLayout rootFrameLayout = findViewById(android.R.id.content);
            FrameLayout.LayoutParams layoutParams =
                    new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER;
            pb = new ProgressBar(this);
            pb.setLayoutParams(layoutParams);
            pb.setVisibility(View.VISIBLE);
            rootFrameLayout.addView(pb);
        } else {
            pb.setVisibility(View.VISIBLE);
        }
    }


    public void dismissLoading() {
        if (pb != null)
            pb.setVisibility(View.GONE);
    }

    @Override
    public void showDeveloping() {
        AlertDialog.Builder customizeDialog =
                new AlertDialog.Builder(this);
        final View dialogView = LayoutInflater.from(this)
                .inflate(me.aidoc.client.R.layout.dialog_developing, null);
        customizeDialog.setView(dialogView);
        alertDialog = customizeDialog.create();

        dialogView.findViewById(me.aidoc.client.R.id.tvDismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = alertDialog.getWindow().getAttributes();  //获取对话框当前的参数值
        //        p.height = (int) (d.getHeight() * 0.3);   //高度设置为屏幕的0.3
        p.width = (int) (d.getWidth() * 0.85);    //宽度设置为屏幕的0.5
        alertDialog.getWindow().setAttributes(p);     //设置生效
    }

    @Override
    public void dismissDeveloping() {
        if (alertDialog != null)
            alertDialog.dismiss();
    }

    @Subscriber(tag = MyConstants.TAG_FINISH_ALL_ACTIVITY)
    public void finishSelf(String flag) {
        //  关闭自己
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    /**
     * 刷新语言
     *
     * @param language 不用填写
     */
    @Subscriber(tag = MyConstants.TAG_REFRESH_LANGUAGE)
    public void changeAppLanguage(String language) {
        doChageLanguage();
        recreate();//刷新界面
    }


    private void doChageLanguage() {
        String sta = Store.getLanguageLocal(this);
        if (sta != null && !"".equals(sta)) {
            if (sta.contains("en")) {
                LocaleUtils.updateLocale(this, LocaleUtils.LOCALE_ENGLISH);

            } else
                LocaleUtils.updateLocale(this, LocaleUtils.LOCALE_CHINESE);

        }
    }
}
