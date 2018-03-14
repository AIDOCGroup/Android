package me.aidoc.client.mvp.mall;

import me.aidoc.client.base.frame.BasePresenter;
import me.aidoc.client.base.frame.BaseView;

public class MallContract {
     interface View extends BaseView{
        void getMallUrlSuccess(String url);
        void getMallUrlErro(String msg);
    }

    interface Presenter extends BasePresenter {
         void getMallUrl();
    }
}
