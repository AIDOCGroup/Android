package me.aidoc.client.mvp.mine.regiser;

import android.widget.Button;

import me.aidoc.client.base.frame.BasePresenter;
import me.aidoc.client.base.frame.BaseView;

public class RegisetrContract {
     interface View extends BaseView{
        String getAccount();
        String getPwd();
        String getRePwd();
        String getVarifyCode();
        String getCountryCode();
        Button getVarifyButton();

        void registerSuccess();
        void registerErro(String msg);
        void getVarifySuccess();
        void getVarifyErro(String msg);
    }

    interface Presenter extends BasePresenter{
         void register();
         void varify();
    }
}
