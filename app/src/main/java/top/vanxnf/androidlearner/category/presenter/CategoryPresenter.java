package top.vanxnf.androidlearner.category.presenter;

import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.vanxnf.androidlearner.R;
import top.vanxnf.androidlearner.category.contract.CategoryContract;
import top.vanxnf.androidlearner.category.model.CategoryModel;
import top.vanxnf.androidlearner.entity.Category;

public class CategoryPresenter implements CategoryContract.Presenter {

    private static final String TAG = "CategoryPresenter";

    private CategoryContract.Model mModel = new CategoryModel();
    private CategoryContract.View mView;

    public CategoryPresenter(CategoryContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void initRootView(View view) {
        mView.initView(view);
    }

    @Override
    public void loadCategoryDataToView() {
        mView.showLoading();
        mModel.getCategoryData(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mView.hideLoading();
                if (mModel.getCategoryList().size() == 0) {
                    mView.showFailPage();
                }
                mView.showToast(R.string.network_error_please_try_again);
                Log.d(TAG, "onFailure: loadCategoryDataToView");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Category category = new Gson().fromJson(response.body().string(), Category.class);
                if (category.getData() != null && category.getErrorCode() == 0) {
                    List<Category> categories = new ArrayList<>();
                    categories.add(category);
                    mModel.setCategoryList(categories);
                    mView.hideFailPage();
                    mView.showCategoryList(category.getData());
                } else {
                    if (mModel.getCategoryList().size() == 0) {
                        mView.showFailPage();
                    }
                    mView.showToast(R.string.data_error_please_try_again);
                }
                mView.hideLoading();
                Log.d(TAG, "onResponse: loadCategoryDataToView");
            }
        });
    }

    @Override
    public void reloadCategoryDataToView() {
        loadCategoryDataToView();
    }

    @Override
    public void goToSearchPage() {
        mView.goToSearchPage();
    }

    @Override
    public void goToDetailPage(int cid, String name) {
        mView.goToDetailPage(cid, name);
    }
}
