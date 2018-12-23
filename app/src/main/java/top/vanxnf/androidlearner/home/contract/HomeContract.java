package top.vanxnf.androidlearner.home.contract;

import java.util.ArrayList;
import java.util.List;

import top.vanxnf.androidlearner.home.model.entity.Article;


public interface HomeContract {
    interface Model {
        int getCurrentPage();
        ArrayList<Article> getArticleList();
        void getArticleData(okhttp3.Callback callback);
        void getArticleData(int page, okhttp3.Callback callback);
        void getMoreArticleData(okhttp3.Callback callback);
        void refreshArticleData(okhttp3.Callback callback);
    }

    interface View {

        void displayArticle(List<Article.DataBean.ArticleData> dtaBeans);
        void displayMoreArticle(List<Article.DataBean.ArticleData> dataBeans);
        void refreshArticleList(List<Article.DataBean.ArticleData> dataBeans);
    }

    interface Presenter {
        void loadArticleDataToView();
        void loadMoreArticleDataToView();
        void refreshArticleDataToView();
    }
}
