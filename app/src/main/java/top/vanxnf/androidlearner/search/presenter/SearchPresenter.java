package top.vanxnf.androidlearner.search.presenter;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.vanxnf.androidlearner.R;
import top.vanxnf.androidlearner.entity.HotKey;
import top.vanxnf.androidlearner.search.contract.SearchContract;
import top.vanxnf.androidlearner.search.model.SearchModel;

public class SearchPresenter implements SearchContract.Presenter {

    private static final String TAG = "SearchPresenter";

    private SearchContract.View mView;
    private SearchContract.Model mModel = new SearchModel();

    public SearchPresenter(SearchContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void initRootView(View view) {
        mView.initView(view);
    }

    @Override
    public void loadHotKeyDataToView() {
        mView.showLoading();
        mModel.getHotKeyData(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mView.hideLoading();
                mView.showFailPage();
                mView.showToast(R.string.network_error_please_try_again);
                Log.d(TAG, "onFailure: loadHotKeyDataToView");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mView.hideLoading();
                HotKey hotKey = new Gson().fromJson(response.body().string(), HotKey.class);
                if (hotKey != null) {
                    mView.hideFailPage();
                    mView.showHotKey(hotKey.getKeys());
                } else {
                    mView.showFailPage();
                    mView.showToast(R.string.data_error_please_try_again);
                }
                Log.d(TAG, "onResponse: loadHotKeyDataToView");
            }
        });
    }

    @Override
    public void reloadHotKeyDataToView() {
        mView.hideFailPage();
        loadHotKeyDataToView();
    }

    @Override
    public void goToResultPage(String keyword) {
        if (!TextUtils.isEmpty(keyword)) {
            mView.showResultPage(keyword);
        } else {
            mView.showToast(R.string.please_input_keyword);
        }
    }
}
