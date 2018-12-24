package top.vanxnf.androidlearner.custom;

import com.chad.library.adapter.base.loadmore.LoadMoreView;

import top.vanxnf.androidlearner.R;

public class LoadMoreFooterView extends LoadMoreView {

    @Override
    public int getLayoutId() {
        return R.layout.loading_footer;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
