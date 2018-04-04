package me.aidoc.client.mvp.tianyi.kangfuzi;

import java.util.List;

import me.aidoc.client.entity.resp.KfzResp;
import me.aidoc.client.base.frame.BasePresenter;
import me.aidoc.client.base.frame.BaseView;

public class KangfuziContract {
     interface View extends BaseView{
         void setBtnNames(List<KfzResp> resp);
         void initError(String msg);
    }

    interface Presenter extends BasePresenter{
         void getKfzData(String userId);
    }
}
