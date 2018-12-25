package top.vanxnf.androidlearner.search.model;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;

import top.vanxnf.androidlearner.entity.HotKey;
import top.vanxnf.androidlearner.search.contract.SearchContract;
import top.vanxnf.androidlearner.util.HttpUtil;

public class SearchModel implements SearchContract.Model {

    final private static String HOT_KEY_API = "http://www.wanandroid.com//hotkey/json";
    private List<HotKey.Key> keys = new ArrayList<>();

    @Override
    public void setKeyList(List<HotKey.Key> keyList) {
        keys = keyList;
    }

    @Override
    public List<HotKey.Key> getKeyList() {
        return keys;
    }

    @Override
    public void getHotKeyData(Callback callback) {
        HttpUtil.sendOkHttpRequest(HOT_KEY_API, callback);
    }
}
