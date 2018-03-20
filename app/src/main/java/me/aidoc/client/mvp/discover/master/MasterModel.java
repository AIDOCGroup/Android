package me.aidoc.client.mvp.discover.master;

import me.aidoc.client.api.helper.HttpUtils;
import me.aidoc.client.api.helper.RetrofitHelper;
import me.aidoc.client.entity.resp.MasterResp;
import me.aidoc.client.base.frame.MvpModel;
import rx.Observable;


public class MasterModel extends MvpModel {
    public void getMastersList(int type, int page, int pageSize, HttpUtils.OnResultListener listener){
        Observable<MasterResp> loginObservable = RetrofitHelper.getService().ranking();
        HttpUtils.requestNetByPost(loginObservable, new HttpUtils.OnResultListener<MasterResp>() {

            @Override
            public void onSuccess(MasterResp userResp) {
                listener.onSuccess(userResp);
            }

            @Override
            public void onError(Throwable error, String msg) {
                listener.onError(error, msg);
            }
        });
    }
}
