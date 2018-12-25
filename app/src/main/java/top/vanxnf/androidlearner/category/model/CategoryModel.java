package top.vanxnf.androidlearner.category.model;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;
import top.vanxnf.androidlearner.category.contract.CategoryContract;
import top.vanxnf.androidlearner.entity.Category;
import top.vanxnf.androidlearner.util.HttpUtil;

public class CategoryModel implements CategoryContract.Model {

    @SuppressWarnings("FieldCanBeLocal")
    private static final String API = "http://www.wanandroid.com/tree/json";
    private List<Category> categories = new ArrayList<>();

    @Override
    public void setCategoryList(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public List<Category> getCategoryList() {
        return categories;
    }

    @Override
    public void getCategoryData(Callback callback) {
        HttpUtil.sendOkHttpRequest(API, callback);
    }
}
