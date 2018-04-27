package me.aidoc.client.mvp.tianyi.pay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.Constants;
import json.chao.com.wanandroid.base.fragment.AbstractRootFragment;
import json.chao.com.wanandroid.component.RxBus;
import json.chao.com.wanandroid.contract.hierarchy.KnowledgeHierarchyListContract;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.core.bean.BaseResponse;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleData;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleListData;
import json.chao.com.wanandroid.core.event.CollectEvent;
import json.chao.com.wanandroid.core.event.DismissDetailErrorView;
import json.chao.com.wanandroid.core.event.ShowDetailErrorView;
import json.chao.com.wanandroid.core.event.SwitchNavigationEvent;
import json.chao.com.wanandroid.core.event.SwitchProjectEvent;
import json.chao.com.wanandroid.presenter.hierarchy.KnowledgeHierarchyListPresenter;
import json.chao.com.wanandroid.ui.main.activity.LoginActivity;
import json.chao.com.wanandroid.ui.mainpager.adapter.ArticleListAdapter;
import json.chao.com.wanandroid.utils.CommonUtils;
import json.chao.com.wanandroid.utils.JudgeUtils;


public class KnowledgeHierarchyListFragment extends AbstractRootFragment<KnowledgeHierarchyListPresenter>
        implements KnowledgeHierarchyListContract.View {

    @BindView(R.id.knowledge_hierarchy_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.normal_view)
    RecyclerView mRecyclerView;

    private int id;
    private int mCurrentPage;
    private List<FeedArticleData> mArticles;
    private ArticleListAdapter mAdapter;
    private boolean isRefresh = true;

    @Inject
    DataManager mDataManager;
    private int articlePosition;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge_hierarchy_list;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        isInnerFragment = true;
        setRefresh();
        Bundle bundle = getArguments();
        id = bundle.getInt(Constants.ARG_PARAM1, 0);
        if (id == 0) {
            return;
        }
        //重置当前页数，防止页面切换后当前页数为较大而加载后面的数据或没有数据
        mCurrentPage = 0;
        mPresenter.getKnowledgeHierarchyDetailData(mCurrentPage, id);
        mAdapter = new ArticleListAdapter(R.layout.item_search_pager, mArticles);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            articlePosition = position;
            JudgeUtils.startArticleDetailActivity(_mActivity,
                    mAdapter.getData().get(position).getId(),
                    mAdapter.getData().get(position).getTitle().trim(),
                    mAdapter.getData().get(position).getLink().trim(),
                    mAdapter.getData().get(position).isCollect(),
                    false,
                    false);
        });
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.item_search_pager_chapterName:
                    break;
                case R.id.item_search_pager_like_iv:
                    likeEvent(position);
                    break;
                case R.id.item_search_pager_tag_tv:
                    String superChapterName = mAdapter.getData().get(position).getSuperChapterName();
                    if (superChapterName.contains(getString(R.string.open_project))) {
                        RxBus.getDefault().post(new SwitchProjectEvent());
                    } else if (superChapterName.contains(getString(R.string.navigation))) {
                        RxBus.getDefault().post(new SwitchNavigationEvent());
                    }
                    break;
                default:
                    break;
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        if (CommonUtils.isNetworkConnected()) {
            showLoading();
        }
    }

    @Override
    public void showKnowledgeHierarchyDetailData(BaseResponse<FeedArticleListData> feedArticleListResponse) {
        if (feedArticleListResponse == null
                || feedArticleListResponse.getData() == null
                || feedArticleListResponse.getData().getDatas() == null) {
            showKnowledgeHierarchyDetailDataFail();
            return;
        }
        RxBus.getDefault().post(new DismissDetailErrorView());
        mRefreshLayout.setVisibility(View.VISIBLE);
        mArticles = feedArticleListResponse.getData().getDatas();
        if (isRefresh) {
            mAdapter.replaceData(mArticles);
        } else {
            mAdapter.addData(mArticles);
        }
        showNormal();
    }

    @Override
    public void showError() {
        RxBus.getDefault().post(new ShowDetailErrorView());
        mRefreshLayout.setVisibility(View.GONE);
    }

    @Override
    public void showCollectArticleData(int position, FeedArticleData feedArticleData, BaseResponse<FeedArticleListData> feedArticleListResponse) {
        mAdapter.setData(position, feedArticleData);
        RxBus.getDefault().post(new CollectEvent(false));
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.collect_success));
    }

    @Override
    public void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, BaseResponse<FeedArticleListData> feedArticleListResponse) {
        mAdapter.setData(position, feedArticleData);
        RxBus.getDefault().post(new CollectEvent(true));
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.cancel_collect_success));
    }

    @Override
    public void showKnowledgeHierarchyDetailDataFail() {
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.failed_to_obtain_knowledge_data));
    }

    @Override
    public void showJumpTheTop() {
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void showReloadDetailEvent() {
        if (mRefreshLayout != null) {
            mRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void showCollectSuccess() {
        if (mAdapter != null && mAdapter.getData().size() > articlePosition) {
            mAdapter.getData().get(articlePosition).setCollect(true);
            mAdapter.setData(articlePosition, mAdapter.getData().get(articlePosition));
        }
    }

    @Override
    public void showCancelCollectSuccess() {
        if (mAdapter != null && mAdapter.getData().size() > articlePosition) {
            mAdapter.getData().get(articlePosition).setCollect(false);
            mAdapter.setData(articlePosition, mAdapter.getData().get(articlePosition));
        }
    }

    public static KnowledgeHierarchyListFragment getInstance(int id, String param2) {
        KnowledgeHierarchyListFragment fragment = new KnowledgeHierarchyListFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_PARAM1, id);
        args.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void likeEvent(int position) {
        if (!mDataManager.getLoginStatus()) {
            startActivity(new Intent(_mActivity, LoginActivity.class));
            CommonUtils.showMessage(_mActivity, getString(R.string.login_tint));
            return;
        }
        if (mAdapter.getData().get(position).isCollect()) {
            mPresenter.cancelCollectArticle(position, mAdapter.getData().get(position));
        } else {
            mPresenter.addCollectArticle(position, mAdapter.getData().get(position));
        }
    }

    private void setRefresh() {
        mRefreshLayout.setPrimaryColorsId(Constants.BLUE_THEME, R.color.white);
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mCurrentPage = 0;
            if (id != 0) {
                isRefresh = true;
                mPresenter.getKnowledgeHierarchyDetailData(0, id);
            }
            refreshLayout.finishRefresh(1000);
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mCurrentPage++;
            if (id != 0) {
                isRefresh = false;
                mPresenter.getKnowledgeHierarchyDetailData(mCurrentPage, id);
            }
            refreshLayout.finishLoadMore(1000);
        });
    }
}
