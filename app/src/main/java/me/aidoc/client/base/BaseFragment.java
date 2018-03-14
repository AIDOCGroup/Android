package me.aidoc.client.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.gyf.barlibrary.ImmersionBar;

import org.simple.eventbus.EventBus;

import me.aidoc.client.R;
import me.aidoc.client.base.frame.BaseView;
import me.aidoc.client.util.L;
import me.aidoc.client.util.LocaleUtils;
import me.aidoc.client.util.Store;
import me.aidoc.client.util.ToastUtil;
import me.aidoc.client.base.frame.MvpFragment;
import me.aidoc.client.base.frame.MvpPresenter;

public abstract class BaseFragment<P extends MvpPresenter> extends MvpFragment<P> implements BaseView {
    ProgressBar pb;
    protected ImmersionBar mImmersionBar;
    Dialog alertDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doChageLanguage();
        EventBus.getDefault().register(this);
//        changeAppLanguage("");

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isImmersionBarEnabled())
            initImmersionBar();
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarColor(R.color.colorPrimary).statusBarDarkFont(true);
        mImmersionBar.keyboardEnable(true).navigationBarWithKitkatEnable(false).init();
    }


    protected void showLog(String log) {
        L.e(log);
    }

    public void toast(String msg) {
        ToastUtil.toast(getActivity(), msg);
    }

    @Override
    public void showLoading() {
        if (pb == null){
            FrameLayout rootFrameLayout=getActivity().findViewById(android.R.id.content);
            FrameLayout.LayoutParams layoutParams=
                    new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity= Gravity.CENTER;
            pb=new ProgressBar(getActivity());
            pb.setLayoutParams(layoutParams);
            pb.setVisibility(View.VISIBLE);
            rootFrameLayout.addView(pb);
        }else{
            pb.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showDeveloping() {
        AlertDialog.Builder customizeDialog =
                new AlertDialog.Builder(getActivity());
        final View dialogView = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_developing,null);
        customizeDialog.setView(dialogView);
        alertDialog = customizeDialog.create();

        dialogView.findViewById(R.id.tvDismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
        WindowManager m = getActivity().getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = alertDialog.getWindow().getAttributes();  //获取对话框当前的参数值
//        p.height = (int) (d.getHeight() * 0.3);   //高度设置为屏幕的0.3
        p.width = (int) (d.getWidth() * 0.85);    //宽度设置为屏幕的0.5
        alertDialog.getWindow().setAttributes(p);     //设置生效
    }

    @Override
    public void dismissDeveloping() {
        if (alertDialog!=null)
            alertDialog.dismiss();
    }

    @Override
    public void dismissLoading() {
        if(pb!=null)
            pb.setVisibility(View.GONE);
    }

    /**
     * 是否在Fragment使用沉浸式
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && mImmersionBar != null)
            mImmersionBar.init();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();
        EventBus.getDefault().unregister(this);
    }

/*    @Subscriber(tag = MyConstants.TAG_REFRESH_LANGUAGE)
    public void changeAppLanguage(String language) {
        doChageLanguage();
        recreate();//刷新界面
    }

    private void doChageLanguage(){
        String sta = Store.getLanguageLocal(getActivity());
        if(sta != null && !"".equals(sta)){
            // 本地语言设置
            Locale myLocale = new Locale(sta);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
        }
    }*/

    private void doChageLanguage() {
        String sta = Store.getLanguageLocal(getActivity());
        if (sta != null && !"".equals(sta)) {
            // 本地语言设置
//            Locale myLocale = new Locale(sta);
//            Resources res = getResources();
//            DisplayMetrics dm = res.getDisplayMetrics();
//            Configuration conf = res.getConfiguration();
//            conf.locale = myLocale;
//            res.updateConfiguration(conf, dm);
            if (sta.contains("en")) {
                LocaleUtils.updateLocale(getActivity(), LocaleUtils.LOCALE_ENGLISH);

            } else
                LocaleUtils.updateLocale(getActivity(), LocaleUtils.LOCALE_CHINESE);

        }
    }
}
