package top.vanxnf.androidlearner.home.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import top.vanxnf.androidlearner.R;
import top.vanxnf.androidlearner.custom.LoadMoreFooterView;
import top.vanxnf.androidlearner.home.contract.HomeContract;


import top.vanxnf.androidlearner.entity.Article;
import top.vanxnf.androidlearner.home.presenter.HomePresenter;
import top.vanxnf.androidlearner.home.view.adapter.HomeArticleAdapter;
import top.vanxnf.androidlearner.base.BaseMainFragment;
import top.vanxnf.androidlearner.web.WebFragment;

@SuppressWarnings("FieldCanBeLocal")
public class HomeFragment extends BaseMainFragment implements HomeContract.View {

    private static final String TAG = "HomeFragment";
    private static final long WAIT_TIME = 350L;
    private long TOUCH_TIME = 0;
    private HomePresenter mPresenter;
    private Toolbar mToolbar;
    private SwipeRefreshLayout mRefresh;
    private List<Article.DataBean.ArticleData> articleLists = new ArrayList<>();
    private RecyclerView mRecycler;
    private HomeArticleAdapter mAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mPresenter = new HomePresenter(this);
        mPresenter.initRootView(view);
        mPresenter.loadArticleToView();
        return view;
    }

    @Override
    public void initView(View view) {
        mRefresh = view.findViewById(R.id.home_refresh_layout);
        mRecycler = view.findViewById(R.id.home_recycler_view);
        mToolbar = view.findViewById(R.id.home_toolbar);
        mToolbar.setTitle(R.string.home);
        mToolbar.setOnClickListener((v)->{
            if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                mRecycler.scrollToPosition(0);
            } else {
                TOUCH_TIME = System.currentTimeMillis();
            }
        });
        initToolbarNav(mToolbar, true);
        mRefresh.setOnRefreshListener(() -> mPresenter.reloadArticleToView());
        mAdapter = new HomeArticleAdapter(articleLists);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.setOnItemClickListener((BaseQuickAdapter adapter, View v, int position) ->
                start(WebFragment.newInstance(articleLists.get(position).getLink(), articleLists.get(position).getTitle()))
        );
        mAdapter.disableLoadMoreIfNotFullPage();
        mAdapter.setOnLoadMoreListener(() -> mRecycler.post(() -> mPresenter.loadMoreArticleToView()), mRecycler);
        mAdapter.setLoadMoreView(new LoadMoreFooterView());
        mRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void showLoading() {
        post(() -> mRefresh.setRefreshing(true));
    }

    @Override
    public void showLoadingMore() {}

    @Override
    public void showArticle(List<Article.DataBean.ArticleData> articles) {
        articleLists = articles;
        post(() -> {
            mAdapter.setNewData(articles);
            mAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void showMoreArticle(List<Article.DataBean.ArticleData> articles) {
        articleLists.addAll(articles);
        post(() -> mAdapter.addData(articles));
    }

    @Override
    public void hideLoading() {
        post(() -> mRefresh.setRefreshing(false));
    }

    @Override
    public void hideLoadingMore(boolean isCompeted, boolean isEnd) {
        mRecycler.post(() -> {
            if (isEnd) {
                mAdapter.loadMoreEnd();
            } else {
                if (isCompeted) {
                    mAdapter.loadMoreComplete();
                } else {
                    mAdapter.loadMoreFail();
                }
            }
        });
    }

    @Override
    public void showToast(Integer resId) {
        showToast(getString(resId));
    }

    @Override
    public void showToast(CharSequence text) {
        post(() -> Toast.makeText(mActivity, text, Toast.LENGTH_SHORT).show());
    }

    @Override
    protected int setTitleBar() {
        return R.id.home_toolbar;
    }
}
