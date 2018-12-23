package top.vanxnf.androidlearner.home.presenter;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.vanxnf.androidlearner.home.contract.HomeContract;
import top.vanxnf.androidlearner.home.model.HomeModel;
import top.vanxnf.androidlearner.home.model.entity.Article;

public class HomePresenter implements HomeContract.Presenter {

    private static final String TAG = "HomePresenter";

    private HomeContract.View mView;
    private HomeContract.Model mModel = new HomeModel();

    public HomePresenter(HomeContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void loadArticleDataToView() {
        mModel.getArticleData(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: loadArticleDataToView");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mModel.getArticleList().add(new Gson().fromJson(response.body().string(), Article.class));
                mView.displayArticle(mModel.getArticleList().get(mModel.getCurrentPage()).getData().getDatas());
                Log.d(TAG, "onResponse: loadArticleDataToView");
            }
        });

    }

    @Override
    public void loadMoreArticleDataToView() {
        mModel.getMoreArticleData(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: loadMoreArticleDataToView");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mModel.getArticleList().add(new Gson().fromJson(response.body().string(), Article.class));
                mView.displayMoreArticle(mModel.getArticleList().get(mModel.getCurrentPage()).getData().getDatas());
                Log.d(TAG, "onResponse: loadMoreArticleDataToView");
            }
        });
    }

    @Override
    public void refreshArticleDataToView() {
        mModel.refreshArticleData(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: refreshArticleDataToView");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mModel.getArticleList().add(new Gson().fromJson(response.body().string(), Article.class));
                mView.refreshArticleList(mModel.getArticleList().get(mModel.getCurrentPage()).getData().getDatas());
                Log.d(TAG, "onResponse: refreshArticleDataToView");
            }
        });
    }
}
