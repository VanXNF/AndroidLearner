package top.vanxnf.androidlearner.search.contract;

import java.util.List;
import top.vanxnf.androidlearner.entity.HotKey;
/**
 * 搜索页
 * @author 许楠钒
 * */
public interface SearchContract {
    interface Model {
        void setKeyList(List<HotKey.Key> keyList);
        List<HotKey.Key> getKeyList();
        void getHotKeyData(okhttp3.Callback callback);
    }

    interface View {

        void initView(android.view.View view);
        void showHotKey(List<HotKey.Key> keyList);
        void showLoading();
        void hideLoading();
        void showFailPage();
        void hideFailPage();
        void showToast(Integer resId);
        void showToast(CharSequence text);
        void showResultPage(String keyword);
    }

    interface Presenter {

        void initRootView(android.view.View view);
        void loadHotKeyDataToView();
        void reloadHotKeyDataToView();
        void goToResultPage(String keyword);
    }
}
