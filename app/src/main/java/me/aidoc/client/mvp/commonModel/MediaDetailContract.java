package me.aidoc.client.mvp.commonModel;


public class MediaDetailContract {
    public interface MediaDetailPresenter extends IPresenter {
        void onGetDetail(String newsId);
    }

    public interface MediaDetailIView extends IView {
        void onRefreshOver();
        void onBindData(CoinMediaDetailBean data);
    }
}
