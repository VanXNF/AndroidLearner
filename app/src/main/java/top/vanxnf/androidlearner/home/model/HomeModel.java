package top.vanxnf.androidlearner.home.model;
import java.util.ArrayList;
import top.vanxnf.androidlearner.home.contract.HomeContract;
import top.vanxnf.androidlearner.home.model.entity.Article;

import top.vanxnf.androidlearner.util.HttpUtil;

public class HomeModel implements HomeContract.Model {

    private static final String TAG = "HomeModel";

    @SuppressWarnings("FieldCanBeLocal")
    private final String API_PREFIX = "http://www.wanandroid.com/article/list/";
    private int currentPage = 0;
    @SuppressWarnings("FieldCanBeLocal")
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
