package me.aidoc.client.mvp.discover.fragment;


import me.aidoc.client.api.helper.HttpUtils;
import me.aidoc.client.entity.resp.NewsListResp;
import me.aidoc.client.base.frame.MvpPresenter;
import me.aidoc.client.api.helper.RetrofitHelper;

import rx.Observable;

public class DiscoverPresenter extends MvpPresenter<DiscoverHttpModel, DiscoverContract.View> implements DiscoverContract.Presenter {

    public void getNewsList(){
        Observable<NewsListResp> loginObservable = RetrofitHelper.getService().newsList(1,0,5);
        HttpUtils.requestNetByPost(loginObservable, new HttpUtils.OnResultListener<NewsListResp>() {

            @Override
            public void onSuccess(NewsListResp userResp) {
                getIView().getNewListSuccess(userResp);
            }

            @Override
            public void onError(Throwable error, String msg) {
            }
        });
    }
}
