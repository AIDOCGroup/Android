package me.aidoc.client.mvp.tianyi.fragment;

import me.aidoc.client.base.frame.BasePresenter;
import me.aidoc.client.base.frame.BaseView;
import me.aidoc.client.entity.resp.ArchivesResp;
import me.aidoc.client.entity.resp.NewsDetailResp;

public class TianyiContract {
     interface View extends BaseView{
         void showWeb(NewsDetailResp resp);
         void getArchivesInfoSuccess(ArchivesResp resp);
    }

    interface Presenter extends BasePresenter{
         void init();
         void getArchives();
    }
}
