package top.vanxnf.androidlearner.home.model;
import java.util.ArrayList;
import java.util.List;

import top.vanxnf.androidlearner.home.contract.HomeContract;
import top.vanxnf.androidlearner.entity.Article;

import top.vanxnf.androidlearner.util.HttpUtil;

@SuppressWarnings("FieldCanBeLocal")
public class HomeModel implements HomeContract.Model {

    private final String API_PREFIX = "http://www.wanandroid.com/article/list/";
    private int allPages = 0;
    private int currentPage = 0;
    private final String API_SUFFIX = "/json";
    private List<Article> articles = new ArrayList<>();

    @Override
    public int getCurrentPage() {
        return currentPage;
    }

    @Override
    public int getAllPages() {
        try {
            return articles.get(currentPage).getData().getTotal();
        } catch (IndexOutOfBoundsException e) {
            return allPages;
        }
    }

    @Override
    public void setAllPages(int allPages) {
        this.allPages = allPages;
    }

    @Override
    public void setArticleList(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public List<Article> getArticleList() {
        return articles;
    }

    @Override
    public void getArticleData(okhttp3.Callback callback) {
        getArticleData(0, callback);
    }

    @Override
    public void getArticleData(int page, okhttp3.Callback callback) {
        currentPage = page;
        HttpUtil.sendOkHttpRequest(API_PREFIX + String.valueOf(currentPage) + API_SUFFIX, callback);
    }

    @Override
    public void getMoreArticleData(okhttp3.Callback callback) {
        getArticleData(currentPage + 1, callback);
    }
}
