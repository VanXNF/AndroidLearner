package top.vanxnf.androidlearner.category.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import top.vanxnf.androidlearner.R;
import top.vanxnf.androidlearner.base.BaseMainFragment;
import top.vanxnf.androidlearner.category.contract.CategoryContract;
import top.vanxnf.androidlearner.entity.Category;
import top.vanxnf.androidlearner.category.presenter.CategoryPresenter;
import top.vanxnf.androidlearner.category.view.adapter.CategoryAdapter;

@SuppressWarnings("FieldCanBeLocal")
public class CategoryFragment extends BaseMainFragment implements CategoryContract.View {

    private static final String TAG = "CategoryFragment";

    private Toolbar mToolbar;
    private SwipeRefreshLayout mRefresh;
    private RecyclerView mRecycler;
    private CategoryAdapter mAdapter;
    private CategoryContract.Presenter mPresenter;
    private List<Category.DataBean> categories = new ArrayList<>();

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
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
        categories = dataBeans;
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
        mToolbar = view.findViewById(R.id.category_toolbar);
        mToolbar.setTitle(R.string.category);
        initToolbarNav(mToolbar);

        mRefresh = view.findViewById(R.id.category_refresh_layout);
        mRefresh.setOnRefreshListener(() -> mPresenter.reloadCategoryDataToView());

        mRecycler = view.findViewById(R.id.category_recycler_view);
        mRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new CategoryAdapter(categories);
        mAdapter.openLoadAnimation();

        mAdapter.addOnTagItemClickListener((int index, List<Category.DataBean.Subcategory> dataBean) ->
            start(CategoryDetailFragment.newInstance(dataBean.get(index).getId(), dataBean.get(index).getName()))
        );
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    protected int setTitleBar() {
        return R.id.category_toolbar;
    }
}
