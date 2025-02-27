package top.vanxnf.androidlearner.category.contract;

import java.util.List;

import top.vanxnf.androidlearner.entity.Category;

/**
 * 类别页契约类
 * @author 许楠钒
 * */
public interface CategoryContract {
    interface Model {
        void setCategoryList(List<Category> categories);
        List<Category> getCategoryList();
        void getCategoryData(okhttp3.Callback callback);
    }

    interface View {
        void initView(android.view.View view);
        void showLoading();
        void showCategoryList(List<Category.DataBean> categories);
        void hideLoading();
        void showToast(Integer resId);
        void showToast(CharSequence text);
        void showFailPage();
        void hideFailPage();
        void goToSearchPage();
        void goToDetailPage(int cid, String name);
    }

    interface Presenter {
        void initRootView(android.view.View view);
        void loadCategoryDataToView();
        void reloadCategoryDataToView();
        void goToSearchPage();
        void goToDetailPage(int cid, String name);
    }
}
