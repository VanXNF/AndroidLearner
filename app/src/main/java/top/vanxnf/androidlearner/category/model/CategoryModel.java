package top.vanxnf.androidlearner.category.model;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;
import top.vanxnf.androidlearner.category.contract.CategoryContract;
import top.vanxnf.androidlearner.entity.Category;
import top.vanxnf.androidlearner.util.HttpUtil;

public class CategoryModel implements CategoryContract.Model {

    @SuppressWarnings("FieldCanBeLocal")
    private final String API = "http://www.wanandroid.com/tree/json";
    private List<Category> categories = new ArrayList<>();

    @Override
    public List<Category> getCategoryList() {
        return categories;
    }

    @Override
    public void getCategoryData(Callback callback) {
        categories.clear();
        HttpUtil.sendOkHttpRequest(API, callback);
    }

    @Override
    public void refreshCategoryData(Callback callback) {
        getCategoryData(callback);
    }
}
