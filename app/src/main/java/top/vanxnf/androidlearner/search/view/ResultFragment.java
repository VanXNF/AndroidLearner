package top.vanxnf.androidlearner.search.view;

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
import top.vanxnf.androidlearner.base.BaseBackFragment;
import top.vanxnf.androidlearner.custom.LoadMoreFooterView;
import top.vanxnf.androidlearner.entity.Article;
import top.vanxnf.androidlearner.search.contract.ResultContract;
import top.vanxnf.androidlearner.search.presenter.ResultPresenter;
import top.vanxnf.androidlearner.search.view.adapter.ResultAdapter;
import top.vanxnf.androidlearner.web.WebFragment;

@SuppressWarnings("FieldCanBeLocal")
public class ResultFragment extends BaseBackFragment implements ResultContract.View {

    private static final String KEYWORD = "keyword";
    private String mKeyword;
    private ResultPresenter mPresenter;
    private Toolbar mToolbar;
    private SwipeRefreshLayout mRefresh;
    private RecyclerView mRecycler;
    private View mEmptyView;
    private ResultAdapter mAdapter;
    private List<Article.DataBean.ArticleData> articles = new ArrayList<>();


    public static ResultFragment newInstance(String keyword) {
        Bundle bundle = new Bundle();
        bundle.putString(KEYWORD, keyword);
        ResultFragment fragment = new ResultFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mKeyword = bundle.getString(KEYWORD);
        } else {
            mKeyword = "";
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        mPresenter = new ResultPresenter(this);
        mPresenter.initRootView(view, mKeyword);
        mPresenter.loadArticleToView();
        return view;
    }

    @Override
    protected int setTitleBar() {
        return R.id.result_toolbar;
    }

    @Override
    public void initView(View view) {
        mToolbar = view.findViewById(R.id.result_toolbar);
        mRefresh = view.findViewById(R.id.result_refresh_layout);
        mRecycler = view.findViewById(R.id.result_recycler_view);
        mEmptyView = view.findViewById(R.id.result_empty_view);
        mToolbar.setTitle(mKeyword);
        initToolbarNav(mToolbar);
        mAdapter = new ResultAdapter(articles);
        mAdapter.openLoadAnimation();
        mAdapter.setLoadMoreView(new LoadMoreFooterView());
        mAdapter.disableLoadMoreIfNotFullPage(mRecycler);
        mAdapter.setOnLoadMoreListener(() -> mPresenter.loadMoreArticleToView(), mRecycler);
        mAdapter.setOnItemClickListener((BaseQuickAdapter adapter, View v, int position) ->
            mPresenter.goToArticlePage(articles.get(position).getLink(), articles.get(position).getTitle())
        );
        mRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecycler.setAdapter(mAdapter);
        mRefresh.setOnRefreshListener(() -> mPresenter.reloadArticleToView());
    }

    @Override
    public void showArticle(List<Article.DataBean.ArticleData> articles) {
        this.articles = articles;
        mRecycler.post(() -> {
            mAdapter.setNewData(articles);
            mAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void showMoreArticle(List<Article.DataBean.ArticleData> articles) {
        this.articles.addAll(articles);
        mRecycler.post(() -> mAdapter.addData(articles));
    }

    @Override
    public void showLoading() {
        post(() -> mRefresh.setRefreshing(true));
    }

    @Override
    public void hideLoading() {
        post(() -> mRefresh.setRefreshing(false));
    }

    @Override
    public void showLoadingMore() {}

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
    public void showFailPage() {
        post(() -> {
            mEmptyView.setVisibility(View.VISIBLE);
            mRecycler.setVisibility(View.GONE);
        });
    }

    @Override
    public void hideFailPage() {
        post(() -> {
            mEmptyView.setVisibility(View.GONE);
            mRecycler.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void showToast(Integer resId) {
        showToast(getText(resId));
    }

    @Override
    public void showToast(CharSequence text) {
        post(() -> Toast.makeText(mActivity, text, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void goToArticlePage(String url, String title) {
        start(WebFragment.newInstance(url, title));
    }
}
