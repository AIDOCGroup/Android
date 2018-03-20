package me.aidoc.client.mvp.mall;

import me.aidoc.client.api.helper.HttpUtils;
import me.aidoc.client.api.helper.RetrofitHelper;
import me.aidoc.client.base.frame.MvpModel;
import me.aidoc.client.entity.resp.MallResp;
import rx.Observable;


public class MallHttpModel extends MvpModel {
    public void getMallUrl(HttpUtils.OnResultListener<MallResp> listener) {
        Observable<MallResp> mallResp = RetrofitHelper.getService().getMall();
        HttpUtils.requestNetByPost(mallResp, new HttpUtils.OnResultListener<MallResp>() {

            @Override
            public void onSuccess(MallResp userResp) {
                listener.onSuccess(userResp);
            }

            @Override
            public void onError(Throwable error, String msg) {
                listener.onError(error, msg);
            }
        });
    }
}
