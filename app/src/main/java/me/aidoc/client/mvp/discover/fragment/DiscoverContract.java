package me.aidoc.client.mvp.discover.fragment;

import me.aidoc.client.entity.resp.NewsListResp;
import me.aidoc.client.base.frame.BasePresenter;
import me.aidoc.client.base.frame.BaseView;

public class DiscoverContract {
     public interface View extends BaseView{
         void getNewListSuccess(NewsListResp resp);
    }

    interface Presenter extends BasePresenter{
    }
}
