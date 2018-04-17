package me.aidoc.client.mvp.commonModel;


public interface MediaDetailModel {
    /**
     * 获得详情
     */
    Observable<CoinMediaDetailBean> getMediaDetail(String newsId);
}
