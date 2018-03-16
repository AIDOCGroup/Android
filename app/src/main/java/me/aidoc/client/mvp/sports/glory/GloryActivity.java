package me.aidoc.client.mvp.sports.glory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import me.aidoc.client.base.BaseActivity;
import me.aidoc.client.base.frame.BaseView;
import me.aidoc.client.R;


public class GloryActivity extends BaseActivity<GloryPresenter> implements GloryContract.View {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_glory);
        intiView();
    }


    private void intiView() {
        setTitle(getString(R.string.reback_pwd));
    }

    @Override
    public String getAccount() {
        return null;
    }

    @Override
    public String getPwd() {
        return null;
    }

    @Override
    public void registerSuccess() {

    }

    @Override
    public void registerErro(String msg) {

    }

    @Override
    public BaseView getBaseView() {
        return this;
    }

}
