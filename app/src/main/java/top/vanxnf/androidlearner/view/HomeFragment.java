package top.vanxnf.androidlearner.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import top.vanxnf.androidlearner.contract.HomeContract;


import top.vanxnf.androidlearner.model.entity.Article;
import top.vanxnf.androidlearner.presenter.HomePresenter;
import top.vanxnf.androidlearner.view.adapter.HomeArticleAdapter;
import top.vanxnf.androidlearner.view.base.BaseMainFragment;

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
        mImmersionBar = ImmersionBar.with(this).transparentStatusBar().statusBarDarkFont(true);
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
        articleDataBeans = dataBeans;
        mAdapter = new HomeArticleAdapter(articleDataBeans);
        mAdapter.setOnLoadMoreListener(() -> {
            mRecycler.postDelayed(()->{
                mPresenter.loadMoreArticleDataToView();
            }, 2000);
        }, mRecycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecycler.setAdapter(mAdapter);
        Log.d(TAG, "displayArticle: success ");
    }

    @Override
    public void displayMoreArticle(List<Article.DataBean.ArticleData> dataBeans) {
        articleDataBeans.addAll(dataBeans);
        post(()->{
            mAdapter.setNewData(articleDataBeans);
//            mAdapter.notifyDataSetChanged();
            mAdapter.loadMoreComplete();
        });
    }

    @Override
    public void refreshArticleList(List<Article.DataBean.ArticleData> dataBeans) {
        articleDataBeans.clear();
        articleDataBeans.addAll(dataBeans);
        post(()->{
            mAdapter.setNewData(articleDataBeans);
            mAdapter.notifyDataSetChanged();
            mRefresh.setRefreshing(false);
        });
    }

    private void initView(View view) {
        mRefresh = view.findViewById(R.id.home_refresh_layout);
        mRecycler = view.findViewById(R.id.home_recycler_view);
        mToolbar = view.findViewById(R.id.toolbar);
        initToolbarNav(mToolbar, true);
        mRefresh.setOnRefreshListener(() -> {
            mPresenter.refreshArticleDataToView();
        });
//        WebView webView = view.findViewById(R.id.home_web_view);
//        webView.loadUrl("https://www.baidu.com/");
//
//        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
//        webView.getSettings().setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
//        webView.getSettings().setSupportZoom(true);//是否可以缩放，默认true
//        webView.getSettings().setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
//        webView.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
//        webView.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
//        webView.getSettings().setAppCacheEnabled(true);//是否使用缓存
//        webView.getSettings().setDomStorageEnabled(true);//DOM Storage
//
//        // 设置WebViewClient来接收处理请求和通知
//        webView.setWebViewClient(new WebViewClient() {
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });
//        // 获取焦点
//        webView.requestFocus();


    }
}
