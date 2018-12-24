package top.vanxnf.androidlearner.category.contract;


import java.util.List;

import top.vanxnf.androidlearner.entity.Article;

public interface DetailContract {
    interface Model {
        void setCid(int cid);
        void setAllPages(int allPages);
        int getCurrentPage();
        int getAllPages();
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

    }

    interface Presenter {
        void initRootView(android.view.View view, int cid);
        void loadArticleToView();
        void loadMoreArticleToView();
        void reloadArticleToView();
    }
}
