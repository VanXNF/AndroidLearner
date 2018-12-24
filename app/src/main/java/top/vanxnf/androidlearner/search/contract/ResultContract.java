package top.vanxnf.androidlearner.search.contract;

import java.util.List;

import top.vanxnf.androidlearner.entity.Article;

public interface ResultContract {
    interface Model {
        void setKeyword(String keyword);
        void setAllPages(int allPages);
        void setArticleList(List<Article> articles);
        int getCurrentPage();
        int getAllPages();
        List<Article> getArticleList();
        void getArticleData(okhttp3.Callback callback);
        void getArticleData(int page, okhttp3.Callback callback);
        void getMoreArticleData(okhttp3.Callback callback);
    }

    interface View {
        void initView(android.view.View view);
        void showArticle(List<Article.DataBean.ArticleData> articles);
        void showMoreArticle(List<Article.DataBean.ArticleData> articles);
        void showLoading();
        void hideLoading();
        void showLoadingMore();
        void hideLoadingMore(boolean isCompeted, boolean isEnd);
        void showFailPage();
        void hideFailPage();
        void showToast(Integer resId);
        void showToast(CharSequence text);
        void goToArticlePage(String url, String title);
    }

    interface Presenter {
        void initRootView(android.view.View view, String keyword);
        void loadArticleToView();
        void loadMoreArticleToView();
        void reloadArticleToView();
        void goToArticlePage(String url, String title);
    }
}
