package top.vanxnf.androidlearner.home.presenter;

import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.vanxnf.androidlearner.R;
import top.vanxnf.androidlearner.home.contract.HomeContract;
import top.vanxnf.androidlearner.home.model.HomeModel;
import top.vanxnf.androidlearner.entity.Article;

public class HomePresenter implements HomeContract.Presenter {

    private static final String TAG = "HomePresenter";

    private HomeContract.View mView;
    private HomeContract.Model mModel = new HomeModel();

    public HomePresenter(HomeContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void initRootView(View view) {
        mView.initView(view);
    }

    @Override
    public void loadArticleToView() {
        mView.showLoading();
        mModel.getArticleData(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mView.hideLoading();
                mView.showToast(R.string.network_error_please_try_again);
                Log.d(TAG, "onFailure: loadArticleToView");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Article article = new Gson().fromJson(response.body().string(), Article.class);
                if (article != null) {
                    List<Article> articles = mModel.getArticleList();
                    articles.add(article);
                    mModel.setArticleList(articles);
                    mModel.setAllPages(article.getData().getPageCount());
                    mView.showArticle(article.getData().getDatas());
                } else {
                    mView.showToast(R.string.data_error_please_try_again);
                }
                mView.hideLoading();
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
                    if (article != null) {
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
        loadArticleToView();
    }
}
