package top.vanxnf.androidlearner.home.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import top.vanxnf.androidlearner.R;
import top.vanxnf.androidlearner.home.contract.HomeContract;


import top.vanxnf.androidlearner.home.model.entity.Article;
import top.vanxnf.androidlearner.home.presenter.HomePresenter;
import top.vanxnf.androidlearner.home.view.adapter.HomeArticleAdapter;
import top.vanxnf.androidlearner.base.BaseMainFragment;
import top.vanxnf.androidlearner.web.WebFragment;

public class HomeFragment extends BaseMainFragment implements HomeContract.View {

    private static final String TAG = "HomeFragment";
    private HomePresenter mPresenter;
    private Toolbar mToolbar;
    private SwipeRefreshLayout mRefresh;
    private List<Article.DataBean.ArticleData> articleDataBeans;
    private RecyclerView mRecycler;
    private HomeArticleAdapter mAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mImmersionBar = ImmersionBar.with(this).titleBar(R.id.toolbar).statusBarDarkFont(true);
        mImmersionBar.init();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mPresenter = new HomePresenter(this);
        initView(view);
        mPresenter.loadArticleDataToView();
        return view;
    }

    @Override
    public void displayArticle(List<Article.DataBean.ArticleData> dataBeans) {
        if (dataBeans != null) {
            articleDataBeans = dataBeans;
            Log.d(TAG, "displayArticle: success ");
        } else {
            articleDataBeans = new ArrayList<>();
            doToast(getText(R.string.network_error_please_try_again));
        }
        mAdapter = new HomeArticleAdapter(articleDataBeans);
        mAdapter.setOnItemClickListener((BaseQuickAdapter adapter, View view, int position) -> {
            start(WebFragment.newInstance(articleDataBeans.get(position).getLink(), articleDataBeans.get(position).getTitle()));
        });
        mAdapter.setOnLoadMoreListener(() -> mRecycler.postDelayed(() -> mPresenter.loadMoreArticleDataToView(), 1000), mRecycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void displayMoreArticle(List<Article.DataBean.ArticleData> dataBeans) {
        if (dataBeans != null) {
            articleDataBeans.addAll(dataBeans);
            post(()->{
                mAdapter.setNewData(articleDataBeans);
                mAdapter.loadMoreComplete();
            });
        } else {
            doToast(getText(R.string.network_error_please_try_again));
        }
    }

    @Override
    public void refreshArticleList(List<Article.DataBean.ArticleData> dataBeans) {
        if (dataBeans != null) {
            if (articleDataBeans != null) {
                articleDataBeans.clear();
                articleDataBeans.addAll(dataBeans);
            } else {
                articleDataBeans = dataBeans;
            }
            mRecycler.postDelayed(()->{
                mAdapter.setNewData(articleDataBeans);
                mAdapter.notifyDataSetChanged();
                mRefresh.setRefreshing(false);
            }, 1000);
        } else {
            doToast(getText(R.string.network_error_please_try_again));
        }
    }

    private void initView(View view) {
        mRefresh = view.findViewById(R.id.home_refresh_layout);
        mRecycler = view.findViewById(R.id.home_recycler_view);
        mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.home);
        initToolbarNav(mToolbar, true);
        mRefresh.setOnRefreshListener(() -> {
            mPresenter.refreshArticleDataToView();
        });
    }

    private void doToast(CharSequence text) {
        Toast.makeText(mActivity, text, Toast.LENGTH_SHORT).show();
    }
}
