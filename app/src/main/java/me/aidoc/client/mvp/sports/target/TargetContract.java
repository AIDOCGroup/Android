package me.aidoc.client.mvp.sports.target;

import me.aidoc.client.base.frame.BasePresenter;
import me.aidoc.client.base.frame.BaseView;

public class TargetContract {
     interface View extends BaseView{
         void putTargetSuccess();
         void putTargetError(String msg);
         void getTargetSuccess(int targetStep);
         void getTargetError(String msg);
    }

    interface Presenter extends BasePresenter {
         void putTarget(String targetStep);
        void getTarget();
    }
}
