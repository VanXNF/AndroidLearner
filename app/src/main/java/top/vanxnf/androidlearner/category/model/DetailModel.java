package top.vanxnf.androidlearner.category.model;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;
import top.vanxnf.androidlearner.category.contract.DetailContract;
import top.vanxnf.androidlearner.entity.Article;
import top.vanxnf.androidlearner.util.HttpUtil;

public class DetailModel implements DetailContract.Model {

    private int cid = 0;
    private int currentPage = 0;
    private int allPages = 0;
    private static final String API_PREFIX = "http://www.wanandroid.com/article/list/";
    private static final String API_SUFFIX = "/json?cid=";
    private List<Article> articles = new ArrayList<>();

    @Override
    public void setCid(int cid) {
        this.cid = cid;
    }

    @Override
    public void setAllPages(int allPages) {
        this.allPages = allPages;
    }

    @Override
    public int getAllPages() {
        try {
            return articles.get(currentPage).getData().getTotal();
        } catch (IndexOutOfBoundsException e) {
            return allPages;
        }
    }

    /** currentPage 用于组合api，从 0 开始计数， 页码则从 1 开始计数， 故需要 +1*/
    @Override
    public int getCurrentPage() {
        return currentPage + 1;
    }

    @Override
    public List<Article> getArticleList() {
        return articles;
    }

    @Override
    public void setArticleList(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public void getArticleData(Callback callback) {
        getArticleData(0, callback);
    }

    @Override
    public void getArticleData(int page, Callback callback) {
        currentPage = page;
        HttpUtil.sendOkHttpRequest(API_PREFIX + String.valueOf(currentPage) + API_SUFFIX + String.valueOf(cid), callback);
    }

    @Override
    public void getMoreArticleData(Callback callback) {
        getArticleData(currentPage + 1, callback);
    }

}
