package top.vanxnf.androidlearner.home.presenter;

import top.vanxnf.androidlearner.home.contract.HomeContract;
import top.vanxnf.androidlearner.home.model.HomeModel;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;
    private HomeContract.Model mModel = new HomeModel();

    public HomePresenter(HomeContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void loadArticleDataToView() {
        mView.displayArticle(mModel.getArticleData());
    }

    @Override
    public boolean loadMoreArticleDataToView() {
        mView.displayMoreArticle(mModel.loadMoreArticleData());
        return true;
    }

    @Override
    public boolean refreshArticleDataToView() {
        mView.refreshArticleList(mModel.refreshArticleData());
        return true;
    }
}
