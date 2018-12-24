package top.vanxnf.androidlearner.category.view;

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
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import top.vanxnf.androidlearner.R;
import top.vanxnf.androidlearner.base.BaseBackFragment;
import top.vanxnf.androidlearner.category.contract.DetailContract;
import top.vanxnf.androidlearner.category.presenter.DetailPresenter;
import top.vanxnf.androidlearner.category.view.adapter.DetailAdapter;
import top.vanxnf.androidlearner.custom.LoadMoreFooterView;
import top.vanxnf.androidlearner.entity.Article;
import top.vanxnf.androidlearner.web.WebFragment;

@SuppressWarnings({"FieldCanBeLocal", "WeakerAccess"})
public class CategoryDetailFragment extends BaseBackFragment implements DetailContract.View {

    private static final String CID = "cid";
    private static final String TITLE = "title";
    private int cid;
    private String title;
    private DetailPresenter mPresenter;
    private Toolbar mToolbar;
    private RecyclerView mRecycler;
    private SwipeRefreshLayout mRefresh;
    private DetailAdapter mAdapter;
    private List<Article.DataBean.ArticleData> articles = new ArrayList<>();

    public static CategoryDetailFragment newInstance(int cid, String title) {
        Bundle bundle = new Bundle();
        bundle.putInt(CID, cid);
        bundle.putString(TITLE, title);
        CategoryDetailFragment fragment = new CategoryDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            cid = bundle.getInt(CID);
            title = bundle.getString(TITLE);
        } else {
            cid = 0;
            title = "";
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_detail, container, false);
        mPresenter = new DetailPresenter(this);
        mPresenter.initRootView(view, cid);
        mPresenter.loadArticleToView();
        return view;
    }

    @Override
    public void initView(View view) {
        mToolbar = view.findViewById(R.id.detail_toolbar);
        mToolbar.setTitle(title);
        initToolbarNav(mToolbar);
        mRefresh = view.findViewById(R.id.detail_refresh_layout);
        mRefresh.setOnRefreshListener(() -> mPresenter.reloadArticleToView());
        mRecycler = view.findViewById(R.id.detail_recycler_view);
        mRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new DetailAdapter(articles);
        mAdapter.openLoadAnimation();
        mAdapter.setLoadMoreView(new LoadMoreFooterView());
        mAdapter.setOnLoadMoreListener(() -> mPresenter.loadMoreArticleToView(), mRecycler);
        mAdapter.disableLoadMoreIfNotFullPage();
        mAdapter.setOnItemClickListener((BaseQuickAdapter adapter, View v, int position) ->
            start(WebFragment.newInstance(articles.get(position).getLink(), articles.get(position).getTitle()))
        );
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
        this.articles = articles;
        post(() -> {
            mAdapter.setNewData(articles);
            mAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void showMoreArticle(List<Article.DataBean.ArticleData> articleList) {
        this.articles.addAll(articleList);
        post(() -> mAdapter.addData(articleList));
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
        showToast(getText(resId));
    }

    @Override
    public void showToast(CharSequence text) {
        post(() -> Toast.makeText(mActivity, text, Toast.LENGTH_SHORT).show());
    }

    @Override
    protected int setTitleBar() {
        return R.id.detail_toolbar;
    }
}
