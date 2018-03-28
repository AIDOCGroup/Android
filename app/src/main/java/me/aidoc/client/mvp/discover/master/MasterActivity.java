package me.aidoc.client.mvp.discover.master;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.aidoc.client.R;
import me.aidoc.client.adapter.MasterAdapter;
import me.aidoc.client.base.BaseTitleActivity;
import me.aidoc.client.entity.resp.MasterResp;
import me.aidoc.client.base.frame.BaseView;


public class MasterActivity extends BaseTitleActivity<MasterPresenter> implements MasterContract.View {

    @BindView(R.id.rv_list)
    RecyclerView mRecyclerViewToday;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private static final int PAGE_SIZE = 8;
    @BindView(R.id.tvStepToday)
    TextView tvStepToday;
    @BindView(R.id.tvStepTotal)
    TextView tvStepTotal;
    @BindView(R.id.lineLeft)
    View lineLeft;
    @BindView(R.id.lineRight)
    View lineRight;
//    @BindView(R.id.rv_list2)
//    RecyclerView mRecyclerViewTotal;
//    @BindView(R.id.swipeLayout2)
//    SwipeRefreshLayout swipeLayout2;
    private int mNextRequestPage = 1;
    private int type=1;//1是今日，2是总步数
    MasterAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        ButterKnife.bind(this);
        initView();
        initAdapter();
        initRefreshLayout();
        refresh();
    }

    private void initView() {
        setTitle(getString(R.string.text_master));
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerViewToday.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> refresh());
    }

    @Override
    public void getMaterList(MasterResp resp) {
        //这里获得新数据
        if(type==1){
            setData(true, resp.getDay());
        }else{
            setData(true, resp.getTotal());
        }

        mAdapter.setEnableLoadMore(false);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void getMasterListError() {
        mAdapter.setEnableLoadMore(false);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void getMoreList(MasterResp resp) {
        /*if(type==1){
            setData(true, resp.getDay());
        }else{
            setData(true, resp.getTotal());
        }*/
    }

    @Override
    public void getMoreError() {
        mAdapter.loadMoreEnd();
    }


    @Override
    public BaseView getBaseView() {
        return this;
    }


    private void initAdapter() {
        mAdapter = new MasterAdapter();
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.isFirstOnly(true);
        mRecyclerViewToday.setAdapter(mAdapter);
    }


    private void refresh() {
        mNextRequestPage = 1;
        mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        mPresenter.getMasterist();
    }

    private void loadMore() {
        mPresenter.getMoreList(mNextRequestPage);
    }


    private void setData(boolean isRefresh, List data) {
        mNextRequestPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mAdapter.setNewData(data);
        } else {
            if (size > 0) {
                mAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh);
            // Toast.makeText(this, "没有更多数据了", Toast.LENGTH_SHORT).show();
        } else {
            mAdapter.loadMoreComplete();
        }
    }

    @OnClick({R.id.tvStepToday, R.id.tvStepTotal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvStepToday:
                showToaday();
                break;
            case R.id.tvStepTotal:
                showTotal();
                break;
        }
    }

    private void showToaday() {
        tvStepToday.setTextColor(getResources().getColor(me.aidoc.client.R.color.txt_blue_light));
        tvStepTotal.setTextColor(getResources().getColor(me.aidoc.client.R.color.black33));
        lineLeft.setVisibility(View.VISIBLE);
        lineRight.setVisibility(View.INVISIBLE);
        mPresenter.getMasterist();
        type=1;
//        mRecyclerViewToday.setVisibility(View.VISIBLE);
//        mSwipeRefreshLayout.setVisibility(View.GONE);
//        swipeLayout2.setVisibility(View.VISIBLE);
    }

    private void showTotal() {
        tvStepTotal.setTextColor(getResources().getColor(me.aidoc.client.R.color.txt_blue_light));
        tvStepToday.setTextColor(getResources().getColor(me.aidoc.client.R.color.black33));
        lineRight.setVisibility(View.VISIBLE);
        lineLeft.setVisibility(View.INVISIBLE);
        mPresenter.getMasterist();
        type=2;
//        mRecyclerViewToday.setVisibility(View.GONE);
//        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
//        swipeLayout2.setVisibility(View.GONE);
    }
}
