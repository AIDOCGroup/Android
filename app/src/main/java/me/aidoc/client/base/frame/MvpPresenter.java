package me.aidoc.client.base.frame;

import android.content.Context;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;


public class MvpPresenter <M extends MvpModel, V extends BaseView> {
    public Context mContext;
    public Reference<V> mViewRef;
    public M mModel;

    public void initPresenter(V view){
        mModel = (M) Mvp.getInstance().getModel(Mvp.getGenericType(this, 0));

        mViewRef = new WeakReference<V>(view);
        mContext = Mvp.getInstance().getApplictionContext();
    }

    public V getIView(){
        return mViewRef.get();
    }

    public void destory(){
        if(mViewRef != null){
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
