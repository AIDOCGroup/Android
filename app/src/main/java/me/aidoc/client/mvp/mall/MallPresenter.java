package me.aidoc.client.mvp.mall;


import me.aidoc.client.api.helper.HttpUtils;
import me.aidoc.client.base.frame.MvpPresenter;
import me.aidoc.client.entity.resp.MallResp;

public class MallPresenter extends MvpPresenter<MallHttpModel, MallContract.View> implements MallContract.Presenter {
    @Override
    public void getMallUrl() {
        mModel.getMallUrl(new HttpUtils.OnResultListener<MallResp>() {
            @Override
            public void onSuccess(MallResp mallResp) {
                getIView().getMallUrlSuccess(mallResp.getUrl());
            }

            @Override
            public void onError(Throwable error, String msg) {
                getIView().getMallUrlErro(msg);
            }
        });
    }
}
