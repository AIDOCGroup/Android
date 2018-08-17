package me.aidoc.client.search;

import android.content.Context;

import com.example.lbf.imatationofwechat.util.CommonUtil;

public class SearchPresenter implements SearchContract.Presenter {
    private Context mContext;
    private SearchContract.View mView;

    public SearchPresenter(Context context, SearchContract.View view) {
        mContext = context;
        mView = CommonUtil.checkNotNull(view,"view cannot be null!");
        mView.setPresenter(this);
    }

    @Override
    public void start() {
    }
}
