package me.aidoc.client.mvp.discover.news;

import me.aidoc.client.api.helper.HttpUtils;
import me.aidoc.client.entity.resp.NewsListResp;
import me.aidoc.client.base.frame.MvpModel;
import me.aidoc.client.api.helper.RetrofitHelper;

import rx.Observable;


public class NewsModel extends MvpModel {
    public void getNewsList(int type,int page,int pageSize,HttpUtils.OnResultListener listener){
        Observable<NewsListResp> loginObservable = RetrofitHelper.getService().newsList(type,page,pageSize);
        HttpUtils.requestNetByPost(loginObservable, new HttpUtils.OnResultListener<NewsListResp>() {

            @Override
            public void onSuccess(NewsListResp userResp) {
                listener.onSuccess(userResp);
            }

            @Override
            public void onError(Throwable error, String msg) {
                listener.onError(error, msg);
            }
        });
    }
}
