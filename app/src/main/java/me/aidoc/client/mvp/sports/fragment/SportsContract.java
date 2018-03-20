package me.aidoc.client.mvp.sports.fragment;

import me.aidoc.client.base.frame.BasePresenter;
import me.aidoc.client.base.frame.BaseView;

public class SportsContract {
    interface View extends BaseView {
        void getStepSuccess(int target);//用户步数

        void getStepErro();
        void getTargetSuccess(int target);//用户目标

        void getTargetErro();

        void getAIDSuccess();//AID币获取

        void getAIDErro();

        void getWeatherSuccess();//天气

        void getWeatherErro();

        void updateStep(int step);
        void updateInitStep(int step);

        void setEnergy(String energy);

    }

    interface Presenter extends BasePresenter {
        void init();
        void getCurrentStep();
    }
}
