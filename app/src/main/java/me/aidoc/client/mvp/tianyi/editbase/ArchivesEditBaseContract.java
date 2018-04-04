package me.aidoc.client.mvp.tianyi.editbase;

import com.zhy.view.flowlayout.TagFlowLayout;

import me.aidoc.client.entity.req.ArchivesReq;
import me.aidoc.client.base.frame.BasePresenter;
import me.aidoc.client.base.frame.BaseView;

public class ArchivesEditBaseContract {
    interface View extends BaseView {
        void saveSuccess();
        void saveErro(String msg);

        //获取几个流布局
        TagFlowLayout[] getFlowLayout();
        void medicaArr(String[] merryArr);
    }

    interface Presenter extends BasePresenter {
        void init();
        void saveArchves(ArchivesReq req);
    }
}
