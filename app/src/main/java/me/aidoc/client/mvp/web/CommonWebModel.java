package me.aidoc.client.mvp.web;

import me.aidoc.client.api.helper.HttpUtils;
import me.aidoc.client.base.frame.MvpModel;
import me.aidoc.client.api.helper.RetrofitHelper;
import me.aidoc.client.entity.resp.NewsDetailResp;

import rx.Observable;


public class CommonWebModel extends MvpModel {
    public static void getDetail(int categories,int id , HttpUtils.OnResultListener<NewsDetailResp> listener){
        Observable<NewsDetailResp> loginObservable = RetrofitHelper.getService().newsDetail(categories,id);
        HttpUtils.requestNetByPost(loginObservable, new HttpUtils.OnResultListener<NewsDetailResp>() {

            @Override
            public void onSuccess(NewsDetailResp userResp) {
                listener.onSuccess(userResp);
            }

            @Override
            public void onError(Throwable error, String msg) {
                listener.onError(error, msg);
            }
        });
    }
}
