package top.vanxnf.androidlearner.home.model;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.vanxnf.androidlearner.home.contract.HomeContract;
import top.vanxnf.androidlearner.home.model.entity.Article;

import top.vanxnf.androidlearner.util.HttpUtil;

public class HomeModel implements HomeContract.Model {

    private static final String TAG = "HomeModel";

    private final String API_PREFIX = "http://www.wanandroid.com/article/list/";
    private int currentPage = 0;
    private final String API_SUFFIX = "/json";
    private ArrayList<Article> articles = new ArrayList<>();

    @Override
    public List<Article.DataBean.ArticleData> getArticleData() {
        articles.clear();
        return getArticleData(0);
    }

    @Override
    public List<Article.DataBean.ArticleData> getArticleData(int page) {
        final boolean[] flag = {true};
        final int[] toggle = {0};
        currentPage = page;
        HttpUtil.sendOkHttpRequest(API_PREFIX + String.valueOf(currentPage) + API_SUFFIX, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: get article page " + String.valueOf(currentPage) + " failure");
                flag[0] = false;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.d(TAG, "onResponse: get article page "+ String.valueOf(currentPage) + " success");
                articles.add(new Gson().fromJson(json, Article.class));
                flag[0] = false;
                toggle[0] = 1;
            }
        });
        // TODO: 18-12-22 等待数据加载完成
        while (flag[0]) {
            try {
                Thread.sleep(5);
                Log.d(TAG, "getArticleData: wait for data");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (toggle[0] == 1) {
            Log.d(TAG, "getArticleData: return success");
            return articles.get(currentPage).getData().getDatas();
        } else {
            Log.d(TAG, "getArticleData: return failure");
            return null;
        }

    }

    @Override
    public List<Article.DataBean.ArticleData> loadMoreArticleData() {
        int currentCount = articles.get(currentPage).getData().getCurPage();
        int pageCount = articles.get(currentPage).getData().getPageCount();
        if (currentCount < pageCount) {
            return getArticleData(currentCount);
        }
        return null;
    }

    @Override
    public List<Article.DataBean.ArticleData> refreshArticleData() {
        return getArticleData();
    }
}
