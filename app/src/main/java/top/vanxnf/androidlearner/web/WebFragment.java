package top.vanxnf.androidlearner.web;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.gyf.barlibrary.ImmersionBar;
import com.just.agentweb.AgentWeb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import top.vanxnf.androidlearner.MainActivity;
import top.vanxnf.androidlearner.R;
import top.vanxnf.androidlearner.base.BaseBackFragment;

public class WebFragment extends BaseBackFragment {

    private static final String LOAD_URL = "URL";
    private static final String LOAD_TITLE = "TITLE";
    private Toolbar mToolbar;
    private String url;
    private String title;
    private AgentWeb mAgentWeb;
    private DrawerLayout mDrawler;

    public static WebFragment newInstance(String url, String title) {
        Bundle bundle = new Bundle();
        bundle.putString(LOAD_URL, url);
        bundle.putString(LOAD_TITLE, title);
        WebFragment fragment = new WebFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mImmersionBar = ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.red);
        mImmersionBar.init();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            url = bundle.getString(LOAD_URL);
            title = bundle.getString(LOAD_TITLE);
        } else {
            url = null;
            title = null;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.setTitle(title);
        initToolbarNav(mToolbar);
        LinearLayout mLinear = view.findViewById(R.id.web_linear_layout);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mLinear, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url);
        mDrawler = ((MainActivity) getActivity()).getmDrawer();
        mDrawler.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDrawler != null) {
            mDrawler.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }
}
