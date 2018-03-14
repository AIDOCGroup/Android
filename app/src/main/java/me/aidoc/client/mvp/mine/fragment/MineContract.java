package me.aidoc.client.mvp.mine.fragment;

import me.aidoc.client.entity.resp.VersionResp;
import me.aidoc.client.base.frame.BasePresenter;
import me.aidoc.client.base.frame.BaseView;
import me.aidoc.client.entity.resp.UserResp;

public class MineContract {
     public interface View extends BaseView{
        void setUserInfo(UserResp userInfo);
        void newVersion(VersionResp resp);
        void noNewVersion(VersionResp resp);
    }

    interface Presenter extends BasePresenter{
         void init();
         void chkVersion();
    }
}
