package top.vanxnf.androidlearner.home.contract;

import java.util.List;

import top.vanxnf.androidlearner.home.model.entity.Article;


public interface HomeContract {
    interface Model {
        List<Article.DataBean.ArticleData> getArticleData();
        List<Article.DataBean.ArticleData> getArticleData(int page);
        List<Article.DataBean.ArticleData> loadMoreArticleData();
        List<Article.DataBean.ArticleData> refreshArticleData();
    }

    interface View {

        void displayArticle(List<Article.DataBean.ArticleData> dtaBeans);
        void displayMoreArticle(List<Article.DataBean.ArticleData> dataBeans);
        void refreshArticleList(List<Article.DataBean.ArticleData> dataBeans);
    }

    interface Presenter {
        void loadArticleDataToView();
        boolean loadMoreArticleDataToView();
        boolean refreshArticleDataToView();
    }
}
