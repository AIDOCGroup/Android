package me.aidoc.client.mvp.web;

import me.aidoc.client.entity.resp.NewsDetailResp;
import me.aidoc.client.base.frame.BasePresenter;
import me.aidoc.client.base.frame.BaseView;

public class CommonWebContract {
    interface View extends BaseView {
        void showWeb(NewsDetailResp resp);
    }

    interface Presenter extends BasePresenter {
        void getDetail(int categories,int id);
    }
}
