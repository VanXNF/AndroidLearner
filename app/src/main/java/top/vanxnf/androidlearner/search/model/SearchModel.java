package top.vanxnf.androidlearner.search.model;

import okhttp3.Callback;

import top.vanxnf.androidlearner.search.contract.SearchContract;
import top.vanxnf.androidlearner.util.HttpUtil;

public class SearchModel implements SearchContract.Model {

    final private static String HOT_KEY_API = "http://www.wanandroid.com//hotkey/json";

    @Override
    public void getHotKeyData(Callback callback) {
        HttpUtil.sendOkHttpRequest(HOT_KEY_API, callback);
    }
}
