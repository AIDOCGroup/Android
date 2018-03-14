package me.aidoc.client.mvp.mine.reset_pwd;

import android.widget.Button;

import me.aidoc.client.base.frame.BasePresenter;
import me.aidoc.client.base.frame.BaseView;

public class ResetContract {
    interface View extends BaseView {
        String getAccount();

        String getPwd();

        String getRePwd();

        String getVarifyCode();

        Button getVarifyButton();
        String getCountryCode();

        void rePwdSuccess();

        void rePwdErro(String msg);
    }

    interface Presenter extends BasePresenter {
        void rePwd();

        void varify();
    }
}
