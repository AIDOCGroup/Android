package me.aidoc.client.base;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.umeng.analytics.MobclickAgent;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import me.aidoc.client.R;
import me.aidoc.client.base.frame.BaseView;
import me.aidoc.client.util.L;
import me.aidoc.client.util.LocaleUtils;
import me.aidoc.client.util.MyConstants;
import me.aidoc.client.util.Store;
import me.aidoc.client.util.ToastUtil;
import me.aidoc.client.base.frame.MvpActivity;
import me.aidoc.client.base.frame.MvpPresenter;


public abstract class BaseTitleActivity<P extends MvpPresenter> extends MvpActivity<P> implements OnClickListener, BaseView {

    private TextView mTitleTextView;
    private ImageView mBackwardbButton;
    private TextView mForwardTv;
    private FrameLayout mContentLayout;
    protected boolean isInited = false;
    ImmersionBar mImmersionBar;
    ProgressBar pb;
    Dialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setupViews();
        mImmersionBar = ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimary)
                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)  //单独指定软键盘模式
                .statusBarDarkFont(true, 0.2f);
        mImmersionBar.init();   //所有子类都将继承这些相同的属性

        EventBus.getDefault().register(this);
        doChageLanguage();
    }

    protected void showBackwardView(int backwardResid, boolean show) {
        if (mBackwardbButton != null) {
            if (show) {
                // mBackwardbButton.setText(backwardResid);
                mBackwardbButton.setVisibility(View.VISIBLE);
            } else {
                mBackwardbButton.setVisibility(View.INVISIBLE);
            }
        } // else ignored
    }

    protected void showForwardView(int forwardResId, boolean show) {
        if (mForwardTv != null) {
            if (show) {
                mForwardTv.setVisibility(View.VISIBLE);
                mForwardTv.setText(forwardResId);
            } else {
                mForwardTv.setVisibility(View.INVISIBLE);
            }
        } // else ignored
    }

    /**
     * 回退事件，super()要写在后面，用来关闭页面
     *
     * @param backwardView
     */
    protected void onBackward(View backwardView) {
        finish();
    }

    /**
     * 右按钮的点击事件，重写即可
     *
     * @param forwardView
     */
    protected void onForward(View forwardView) {

    }

    @Override
    public void setTitle(int titleId) {
        mTitleTextView.setText(titleId);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitleTextView.setText(title);
    }

    @Override
    public void setTitleColor(int textColor) {
        mTitleTextView.setTextColor(textColor);
    }

    @Override
    public void setContentView(int layoutResID) {
        mContentLayout.removeAllViews();
        View.inflate(this, layoutResID, mContentLayout);
        onContentChanged();
    }

    @Override
    public void setContentView(View view) {
        mContentLayout.removeAllViews();
        mContentLayout.addView(view);
        onContentChanged();
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        mContentLayout.removeAllViews();
        mContentLayout.addView(view, params);
        onContentChanged();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_backward:
                onBackward(v);
                break;

            case R.id.button_forward:
                onForward(v);
                break;

            default:
                break;
        }
    }

    private void setupViews() {
        super.setContentView(R.layout.activity_base_title);

        mTitleTextView = findViewById(R.id.text_title);
        mContentLayout = findViewById(R.id.layout_content);

        mBackwardbButton = findViewById(R.id.button_backward);
        mForwardTv = findViewById(R.id.button_forward);
    }

    public TextView getForward() {
        return mForwardTv;
    }

    // 1、设置右上按钮文本 20160122增加
    public void setForwardBtnTitle(String title) {
        if (mForwardTv != null) {
            mForwardTv.setText(title);
        }
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


    @Override
    public void showDeveloping() {
        AlertDialog.Builder customizeDialog =
                new AlertDialog.Builder(this);
        final View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_developing, null);
        customizeDialog.setView(dialogView);
        alertDialog = customizeDialog.create();

        dialogView.findViewById(R.id.tvDismiss).setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void dismissLoading() {
        if (pb != null)
            pb.setVisibility(View.GONE);
    }

    protected void showLog(String log) {
        L.e(log);
    }

    public void toast(String msg) {
        ToastUtil.toast(this, msg);
    }

    @Subscriber(tag = MyConstants.TAG_FINISH_ALL_ACTIVITY)
    public void finishSelf(String flag) {
        //  关闭自己
        finish();
    }

    @Subscriber(tag = MyConstants.TAG_GOLOGIN)
    public void goLogin(String flag) {
        //  关闭自己
//        finish();
//        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();
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

    @Subscriber(tag = MyConstants.TAG_REFRESH_LANGUAGE)
    public void changeAppLanguage(String language) {
        doChageLanguage();
        recreate();//刷新界面
    }

    private void doChageLanguage() {
        String sta = Store.getLanguageLocal(this);
        if (sta != null && !"".equals(sta)) {
            // 本地语言设置
//            Locale myLocale = new Locale(sta);
//            Resources res = getResources();
//            DisplayMetrics dm = res.getDisplayMetrics();
//            Configuration conf = res.getConfiguration();
//            conf.locale = myLocale;
//            res.updateConfiguration(conf, dm);
            if (sta.contains("en")) {
                LocaleUtils.updateLocale(this, LocaleUtils.LOCALE_ENGLISH);

            } else
                LocaleUtils.updateLocale(this, LocaleUtils.LOCALE_CHINESE);

        }
    }
}
