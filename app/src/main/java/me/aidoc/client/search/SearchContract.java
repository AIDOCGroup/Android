package me.aidoc.client.search;

import com.example.lbf.imatationofwechat.BasePresenter;
import com.example.lbf.imatationofwechat.BaseView;

public interface SearchContract {
    interface Presenter extends BasePresenter {
    }

    interface View extends BaseView<Presenter> {
    }
}
