package top.vanxnf.androidlearner.search.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.chip.Chip;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import top.vanxnf.androidlearner.R;
import top.vanxnf.androidlearner.base.BaseBackFragment;
import top.vanxnf.androidlearner.entity.HotKey;
import top.vanxnf.androidlearner.search.contract.SearchContract;
import top.vanxnf.androidlearner.search.presenter.SearchPresenter;
import top.vanxnf.androidlearner.search.view.adapter.SearchTagAdapter;

@SuppressWarnings("FieldCanBeLocal")
public class SearchFragment extends BaseBackFragment implements SearchContract.View {

    private Toolbar mToolbar;
    private SearchPresenter mPresent;
    private TagFlowLayout mTagFlowLayout;
    private SearchTagAdapter mAdapter;
    private View mLoadingView;
    private View mEmptyView;
    private EditText mSearchEditView;
    private ImageView mClearButton;
    private List<HotKey.Key> keys = new ArrayList<>();

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        mPresent = new SearchPresenter(this);
        mPresent.initRootView(view);
        mPresent.loadHotKeyDataToView();
        return view;
    }

    @Override
    protected int setTitleBar() {
        return R.id.search_toolbar;
    }

    @Override
    public void initView(View view) {
        mToolbar = view.findViewById(R.id.search_toolbar);
        mTagFlowLayout = view.findViewById(R.id.search_tag_flow_layout);
        mLoadingView = view.findViewById(R.id.search_loading_view);
        mEmptyView = view.findViewById(R.id.search_empty_view);
        mSearchEditView = view.findViewById(R.id.search_edit_view);
        mClearButton = view.findViewById(R.id.search_clear_image);
        mToolbar.inflateMenu(R.menu.toolbar_menu);
        mToolbar.setOnMenuItemClickListener((MenuItem menuItem) -> {
            if (menuItem.getItemId() == R.id.toolbar_search) {
                String searchKey = mSearchEditView.getText().toString().trim();
                mPresent.goToResultPage(searchKey);
            }
            return true;
        });
        initToolbarNav(mToolbar);
        mAdapter = new SearchTagAdapter(keys, mTagFlowLayout);
        mTagFlowLayout.setOnTagClickListener((View v, int position, FlowLayout parent) -> {
            String keyword = keys.get(position).getName();
            mSearchEditView.setText(keyword);
            mPresent.goToResultPage(keyword);
            return true;
        });
        mEmptyView.setOnClickListener((v) -> {
            mPresent.reloadHotKeyDataToView();
        });
        mSearchEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String text = mSearchEditView.getText().toString().trim();
                if (!TextUtils.isEmpty(text)) {
                    mClearButton.setVisibility(View.VISIBLE);
                } else {
                    mClearButton.setVisibility(View.GONE);
                }
            }
        });
        mClearButton.setOnClickListener((v) -> mSearchEditView.setText(""));
    }

    @Override
    public void showHotKey(List<HotKey.Key> keyList) {
        keys = keyList;
        mTagFlowLayout.post(() -> {
            mAdapter = new SearchTagAdapter(keyList, mTagFlowLayout);
            mTagFlowLayout.setAdapter(mAdapter);
        });
    }

    @Override
    public void showLoading() {
        post(() -> {
            mLoadingView.setVisibility(View.VISIBLE);
            mTagFlowLayout.setVisibility(View.GONE);
        });
    }

    @Override
    public void hideLoading() {
        post(() -> {
            mLoadingView.setVisibility(View.GONE);
            mTagFlowLayout.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void showFailPage() {
        post(() -> {
            mEmptyView.setVisibility(View.VISIBLE);
            mTagFlowLayout.setVisibility(View.GONE);
        });
    }

    @Override
    public void hideFailPage() {
        post(() -> {
            mEmptyView.setVisibility(View.GONE);
            mTagFlowLayout.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void showToast(Integer resId) {
        showToast(getString(resId));
    }

    @Override
    public void showToast(CharSequence text) {
        post(() -> Toast.makeText(mActivity, text, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void showResultPage(String keyword) {
        start(ResultFragment.newInstance(keyword));
    }
}
