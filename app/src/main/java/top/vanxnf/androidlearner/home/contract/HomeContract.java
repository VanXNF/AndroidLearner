package top.vanxnf.androidlearner.home.contract;

import java.util.List;

import top.vanxnf.androidlearner.entity.Article;


public interface HomeContract {
    interface Model {
        int getCurrentPage();
        int getAllPages();
        void setAllPages(int allPages);
        void setArticleList(List<Article> articles);
        List<Article> getArticleList();
        void getArticleData(okhttp3.Callback callback);
        void getArticleData(int page, okhttp3.Callback callback);
        void getMoreArticleData(okhttp3.Callback callback);
    }

    interface View {
        void initView(android.view.View view);
        void showLoading();
        void showLoadingMore();
        void showArticle(List<Article.DataBean.ArticleData> articles);
        void showMoreArticle(List<Article.DataBean.ArticleData> articles);
        void hideLoading();
        void hideLoadingMore(boolean isCompeted, boolean isEnd);
        void showToast(Integer resId);
        void showToast(CharSequence text);
        void showFailPage();
        void hideFailPage();
        void goToSearchPage();
        void goToArticlePage(String url, String title);
    }

    interface Presenter {
        void initRootView(android.view.View view);
        void loadArticleToView();
        void loadMoreArticleToView();
        void reloadArticleToView();
        void goToSearchPage();
        void goToArticlePage(String url, String title);
    }
}
