package me.aidoc.client.mvp.sports.glory;

import me.aidoc.client.base.frame.BasePresenter;
import me.aidoc.client.base.frame.BaseView;

public class GloryContract {
     interface View extends BaseView{
        String getAccount();
        String getPwd();
        void registerSuccess();
        void registerErro(String msg);
    }

    interface Presenter extends BasePresenter{
         void register();
    }
}
