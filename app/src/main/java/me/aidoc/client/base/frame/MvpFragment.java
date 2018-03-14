package me.aidoc.client.base.frame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class MvpFragment <P extends MvpPresenter> extends Fragment implements MvpView{
    public P mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initMvp();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void initMvp(){
        Mvp mvp = Mvp.getInstance();
        mvp.registerView(this.getClass(), this);
        mPresenter = (P) mvp.getPresenter(Mvp.getGenericType(this, 0));
        mPresenter.initPresenter(getBaseView());
    }

    public abstract BaseView getBaseView();

    @Override
    public void onDestroy() {
        super.onDestroy();

        Mvp.getInstance().unRegister(this.getClass());
        if(mPresenter!=null)
        mPresenter.destory();
    }
}
