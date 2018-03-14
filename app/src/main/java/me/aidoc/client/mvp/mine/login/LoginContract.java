package me.aidoc.client.mvp.mine.login;

import me.aidoc.client.base.frame.BasePresenter;
import me.aidoc.client.base.frame.BaseView;

public class LoginContract {
    public enum Type{QQ,SINA,WECHAT,TAOBAO,ALIPAY};
     interface View extends BaseView{
        String getAccount();
        String getPassword();
        String getVarifyCode();
        void loginSuccess();
        void loginError(String msg);
        void loginAuthSuccess(Type type);
        void loginAuthError(String msg);
    }

    interface Presenter extends BasePresenter{
         void login();
         void varifyCodeLogin();
         void authLogin(Type type);
//         void qqLogin();
//         void sinaLogin();
//         void wechatLogin();
//         void taobaoLogin();
//         void alipayLogin();
         void forget();
         void register();
    }
}
