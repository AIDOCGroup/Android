package me.aidoc.client.mvp.web;


import me.aidoc.client.api.helper.HttpUtils;
import me.aidoc.client.entity.resp.NewsDetailResp;
import me.aidoc.client.base.frame.MvpPresenter;

public class CommonWebPresenter extends MvpPresenter<CommonWebModel, CommonWebContract.View> implements CommonWebContract.Presenter {

    @Override
    public void getDetail(int categories,int id) {
        mModel.getDetail(categories,id, new HttpUtils.OnResultListener<NewsDetailResp>() {
            @Override
            public void onSuccess(NewsDetailResp newsDetailResp) {
                getIView().showWeb(newsDetailResp);
            }

            @Override
            public void onError(Throwable error, String msg) {

            }
        });
    }
}
