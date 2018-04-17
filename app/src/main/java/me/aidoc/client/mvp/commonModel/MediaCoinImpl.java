package me.aidoc.client.mvp.commonModel;


import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MediaCoinImpl extends BasePresenterImpl<MediaCoinContract.MediaCoinIView> implements MediaCoinContract.MediaCoinPresenter {
    @Override
    public void detachView() {

    }

    @Override
    public void onRefresh() {
        BiMediaModelImpl.getInstance().getMediaList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new SimpleObserver<CoinMediaBean>() {
                    @Override
                    public void onNext(CoinMediaBean value) {
                        if (mView != null) {
                            mView.onRefreshOver();
                            mView.onBindData(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView != null) {
                            mView.onRefreshOver();
                        }
                    }
                });
    }
}
