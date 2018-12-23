package top.vanxnf.androidlearner.category.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import top.vanxnf.androidlearner.R;
import top.vanxnf.androidlearner.base.BaseMainFragment;
import top.vanxnf.androidlearner.category.contract.CategoryContract;
import top.vanxnf.androidlearner.category.model.entity.Category;
import top.vanxnf.androidlearner.category.presenter.CategoryPresenter;
import top.vanxnf.androidlearner.category.view.adapter.CategoryAdapter;

@SuppressWarnings("FieldCanBeLocal")
public class CategoryFragment extends BaseMainFragment implements CategoryContract.View {

    private Toolbar mToolbar;
    private SwipeRefreshLayout mRefresh;
    private RecyclerView mRecycler;
    private ChipGroup chipGroup;
    private CategoryAdapter mAdapter;
    private CategoryContract.Presenter mPresenter;
    private List<Category.DataBean> categories = new ArrayList<>();

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mImmersionBar = ImmersionBar.with(this).titleBar(R.id.toolbar);
        mImmersionBar.init();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        mPresenter = new CategoryPresenter(this);
        mPresenter.initRootView(view);
        mPresenter.loadCategoryDataToView();
        return view;
    }

    @Override
    public void showLoading() {
        post(() -> mRefresh.setRefreshing(true));
    }

    @Override
    public void showToast(CharSequence text) {
        post(() -> Toast.makeText(mActivity, text, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void showToast(Integer resId) {
        showToast(getString(resId));
    }

    @Override
    public void showCategoryList(List<Category.DataBean> dataBeans) {
        categories.clear();
        categories.addAll(dataBeans);
        post(() -> {
            mAdapter.setNewData(categories);
            mAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void hideLoading() {
        post(() -> mRefresh.setRefreshing(false));
    }

    @Override
    public void initView(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.category);
        initToolbarNav(mToolbar);

        mRefresh = view.findViewById(R.id.category_refresh_layout);
        mRefresh.setOnRefreshListener(() -> mPresenter.reloadCategoryDataToView());

        mRecycler = view.findViewById(R.id.category_recycler_view);
        mRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new CategoryAdapter(categories);
        mAdapter.openLoadAnimation();
        mAdapter.loadMoreEnd(false);
        mAdapter.setOnItemChildClickListener((BaseQuickAdapter adapter, View v, int position) -> {
            showToast(String.valueOf(v.getId()));
            // TODO: 18-12-23 启动新界面 分类详情界面 
//            start();
        });
        mRecycler.setAdapter(mAdapter);

        chipGroup = view.findViewById(R.id.category_chip_group);
//        chipGroup.setOnCheckedChangeListener((ChipGroup chipGroup, int i) -> {
//            showToast(String.valueOf(chipGroup.getCheckedChipId()) + " i = " + String.valueOf(i));
//        });
    }
}
