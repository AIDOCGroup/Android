package me.aidoc.client.mvp.commonModel;


public class MediaCoinContract {

    public interface MediaCoinPresenter extends IPresenter{
        void onRefresh();
    }

    public interface MediaCoinIView extends IView{
        void onRefreshOver();
        void onBindData(CoinMediaBean data);
    }
}
