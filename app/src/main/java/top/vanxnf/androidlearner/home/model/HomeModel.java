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
    public int getCurrentPage() {
        return currentPage;
    }

    @Override
    public ArrayList<Article> getArticleList() {
        return articles;
    }

    @Override
    public void getArticleData(okhttp3.Callback callback) {
        articles.clear();
        getArticleData(0, callback);
    }

    @Override
    public void getArticleData(int page, okhttp3.Callback callback) {
        currentPage = page;
        HttpUtil.sendOkHttpRequest(API_PREFIX + String.valueOf(currentPage) + API_SUFFIX, callback);

//        while (flag[0]) {
//            try {
//                Thread.sleep(5);
//                Log.d(TAG, "getArticleData: wait for data");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        if (toggle[0] == 1) {
//            Log.d(TAG, "getArticleData: return success");
//            return articles.get(currentPage).getData().getDatas();
//        } else {
//            Log.d(TAG, "getArticleData: return failure");
//            return null;
//        }

    }

    @Override
    public void getMoreArticleData(okhttp3.Callback callback) {
        int currentCount = articles.get(currentPage).getData().getCurPage();
        int pageCount = articles.get(currentPage).getData().getPageCount();
        if (currentCount < pageCount) {
            getArticleData(currentCount, callback);
        }
    }

    @Override
    public void refreshArticleData(okhttp3.Callback callback) {
        getArticleData(callback);
    }
}
