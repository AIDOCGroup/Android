package me.aidoc.client.mvp.mine.reset_phone;

import android.widget.Button;

import me.aidoc.client.base.frame.BasePresenter;
import me.aidoc.client.base.frame.BaseView;

public class ResetContract {
    interface View extends BaseView {
        String getAccount();

        String getNewAccount();

        String getVarifyCode();

        String getNewVarifyCode();
        String getCountryCode();

        Button getResetButton();

        void resetSuccess();

        void resetErro(String msg);

        void getVarifySuccess();

        void getVarifyErro(String msg);

        void getNewVarifySuccess();

        Button getVarifyButton();

        Button getNewVarifyButton();

        void getNewVarifyErro(String msg);
    }

    interface Presenter extends BasePresenter {
        void resetPhone();

        void varify();

        void newVarify();
    }
}
