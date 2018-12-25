package top.vanxnf.androidlearner.search.presenter;

import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.vanxnf.androidlearner.R;
import top.vanxnf.androidlearner.entity.Article;
import top.vanxnf.androidlearner.search.contract.ResultContract;
import top.vanxnf.androidlearner.search.model.ResultModel;

public class ResultPresenter implements ResultContract.Presenter {

    private static final String TAG = "ResultPresenter";

    private ResultContract.View mView;
    private ResultContract.Model mModel = new ResultModel();

    public ResultPresenter(ResultContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void initRootView(View view, String keyword) {
        mModel.setKeyword(keyword);
        mView.initView(view);
    }

    @Override
    public void loadArticleToView() {
        mView.showLoading();
        mModel.getArticleData(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mView.hideLoading();
                if (mModel.getArticleList().size() == 0) {
                    mView.showFailPage();
                }
                mView.showToast(R.string.network_error_please_try_again);
                Log.d(TAG, "onFailure: loadArticleToView");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mView.hideLoading();
                Article article = new Gson().fromJson(response.body().string(), Article.class);
                if (article != null && article.getData().getTotal() != 0 && article.getErrorCode() == 0) {
                    List<Article> articles = new ArrayList<>();
                    articles.add(article);
                    mModel.setArticleList(articles);
                    mModel.setAllPages(article.getData().getTotal());
                    mView.hideFailPage();
                    mView.showArticle(article.getData().getDatas());
                } else {
                    if (mModel.getArticleList().size() == 0) {
                        mView.showFailPage();
                    }
                    mView.showToast(R.string.data_error_please_try_again);
                }
                Log.d(TAG, "onResponse: loadArticleToView");
            }
        });
    }

    @Override
    public void loadMoreArticleToView() {
        if (mModel.getCurrentPage() < mModel.getAllPages()) {
            mView.showLoadingMore();
            mModel.getMoreArticleData(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mView.showToast(R.string.network_error_please_try_again);
                    mView.hideLoadingMore(false, false);
                    Log.d(TAG, "onFailure: loadMoreArticleToView");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Article article = new Gson().fromJson(response.body().string(), Article.class);
                    if (article != null && article.getErrorCode() == 0) {
                        List<Article> articles = mModel.getArticleList();
                        articles.add(article);
                        mModel.setArticleList(articles);
                        mView.showMoreArticle(article.getData().getDatas());
                        mView.hideLoadingMore(true, false);
                    } else {
                        mView.showToast(R.string.data_error_please_try_again);
                        mView.hideLoadingMore(false, false);
                    }
                    Log.d(TAG, "onResponse: loadMoreArticleToView");
                }
            });
        } else {
            mView.hideLoadingMore(false, true);
        }
    }

    @Override
    public void reloadArticleToView() {
        mView.hideFailPage();
        loadArticleToView();
    }

    @Override
    public void goToArticlePage(String url, String title) {
        title = title.replace("<em class='highlight'>", "")
                .replace("</em>", "");
        mView.goToArticlePage(url, title);
    }
}
