package top.vanxnf.androidlearner.search.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Callback;
import top.vanxnf.androidlearner.entity.Article;
import top.vanxnf.androidlearner.search.contract.ResultContract;
import top.vanxnf.androidlearner.util.HttpUtil;

@SuppressWarnings("FieldCanBeLocal")
public class ResultModel implements ResultContract.Model {

    private static String SEARCH_API_PREFIX = "http://www.wanandroid.com/article/query/";
    private static String SEARCH_API_SUFFIX = "/json";
    private int currentPage = 0;
    private int allPages = 0;
    private String keyword = "";
    private List<Article> articles = new ArrayList<>();

    @Override
    public void setKeyword(String keyword) {
        this.keyword = keyword;
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
    public List<Article> getArticleList() {
        return articles;
    }

    @Override
    public void getArticleData(Callback callback) {
        getArticleData(0, callback);
    }

    @Override
    public void getArticleData(int page, Callback callback) {
        currentPage = page;
        Map<String, String> map = new HashMap<>();
        map.put("k", keyword);
        HttpUtil.sendOkHttpRequest(SEARCH_API_PREFIX + String.valueOf(currentPage) + SEARCH_API_SUFFIX, map, callback);
    }

    @Override
    public void getMoreArticleData(Callback callback) {
        getArticleData(currentPage + 1, callback);
    }
}
