package me.aidoc.client.mvp.discover.master;

import me.aidoc.client.entity.resp.MasterResp;
import me.aidoc.client.base.frame.BasePresenter;
import me.aidoc.client.base.frame.BaseView;

public class MasterContract {
    interface View extends BaseView {
        void getMaterList(MasterResp resp);//获取新闻列表
        void getMoreList(MasterResp resp);//获取新闻列表
        void getMoreError();//获取新闻列表
        void getMasterListError();//获取新闻列表
    }

    interface Presenter extends BasePresenter {
        void getMasterist();
        void getMoreList(int page);
    }
}
