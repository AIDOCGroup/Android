package me.aidoc.client.mvp.discover.master;


import me.aidoc.client.api.helper.HttpUtils;
import me.aidoc.client.entity.resp.MasterResp;
import me.aidoc.client.base.frame.MvpPresenter;

public class MasterPresenter extends MvpPresenter<MasterModel, MasterContract.View> implements MasterContract.Presenter {
    @Override
    public void getMasterist() {
        mModel.getMastersList(1, 0, 50, new HttpUtils.OnResultListener<MasterResp>() {
            @Override
            public void onSuccess(MasterResp resp) {
                if (resp.getDay().size() > 0 || resp.getTotal().size() > 0) {
                    getIView().getMaterList(resp);
                } else
                    getIView().getMasterListError();
            }

            @Override
            public void onError(Throwable error, String msg) {
                getIView().getMasterListError();
            }
        });
    }

    @Override
    public void getMoreList(int page) {
        mModel.getMastersList(1, page, 20, new HttpUtils.OnResultListener<MasterResp>() {
            @Override
            public void onSuccess(MasterResp resp) {
//                NewsListResp.ListBean listBean = resp.getList().get(0);
//                resp.getList().add(listBean);
//                resp.getList().add(listBean);
//                resp.getList().add(listBean);
//                resp.getList().add(listBean);
//                resp.getList().add(listBean);
//                resp.getList().add(listBean);
//                resp.getList().add(listBean);
//                resp.getList().add(listBean);
                if (resp.getDay().size() > 0|| resp.getTotal().size() > 0)
                    getIView().getMoreList(resp);
                else
                    getIView().getMoreError();
            }

            @Override
            public void onError(Throwable error, String msg) {
                getIView().getMoreError();
            }
        });
    }

}
