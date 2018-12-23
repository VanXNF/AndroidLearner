package top.vanxnf.androidlearner.category.contract;

import java.util.List;

import top.vanxnf.androidlearner.category.model.entity.Category;

public interface CategoryContract {
    interface Model {
        List<Category> getCategoryList();
        void getCategoryData(okhttp3.Callback callback);
        void refreshCategoryData(okhttp3.Callback callback);
    }

    interface View {
        void initView(android.view.View view);
        void showLoading();
        void showCategoryList(List<Category.DataBean> categories);
        void hideLoading();
        void showToast(Integer resId);
        void showToast(CharSequence text);
    }

    interface Presenter {
        void initRootView(android.view.View view);
        void loadCategoryDataToView();
        void reloadCategoryDataToView();
    }
}
