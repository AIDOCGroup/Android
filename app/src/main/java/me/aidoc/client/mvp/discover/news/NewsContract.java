package me.aidoc.client.mvp.discover.news;

import me.aidoc.client.entity.resp.NewsListResp;
import me.aidoc.client.base.frame.BasePresenter;
import me.aidoc.client.base.frame.BaseView;

public class NewsContract {
    interface View extends BaseView {
        void getNewList(NewsListResp resp);//获取新闻列表
        void getMoreList(NewsListResp resp);//获取新闻列表
        void getMoreError();//获取新闻列表
        void getNewListError();//获取新闻列表
    }

    interface Presenter extends BasePresenter {
        void getNewsList();
        void getMoreList(int page);
    }
}
