package top.vanxnf.androidlearner.web;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.just.agentweb.AgentWeb;

import top.vanxnf.androidlearner.MainActivity;
import top.vanxnf.androidlearner.R;
import top.vanxnf.androidlearner.base.BaseBackFragment;

@SuppressWarnings({"FieldCanBeLocal", "ConstantConditions"})
public class WebFragment extends BaseBackFragment {

    private static final String LOAD_URL = "URL";
    private static final String LOAD_TITLE = "TITLE";
    private Toolbar mToolbar;
    private String url;
    private String title;
    private AgentWeb mAgentWeb;

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
        mToolbar = view.findViewById(R.id.web_toolbar);
        mToolbar.setTitle(title);
        initToolbarNav(mToolbar);
        mToolbar.setNavigationOnClickListener((v) -> {
            if (!mAgentWeb.back()) {
                pop();
            }
        });
        mToolbar.inflateMenu(R.menu.toolbar_web_menu);
        mToolbar.setOnMenuItemClickListener((MenuItem item) -> {
            if (item.getItemId() == R.id.toolbar_refresh) {
                mAgentWeb.getUrlLoader().reload();
            }
            return true;
        });
        LinearLayout mLinear = view.findViewById(R.id.web_linear_layout);
        mAgentWeb = AgentWeb.with(mActivity)
                .setAgentWebParent(mLinear, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .createAgentWeb()
                .ready()
                .go(url);
    }

    @Override
    public boolean onBackPressedSupport() {
        return mAgentWeb.back();
    }

    @Override
    public void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onSupportVisible() {
        ((MainActivity) getActivity()).setDrawerState(true);
        super.onSupportVisible();
    }

    @Override
    public void onDestroyView() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroyView();
    }

    @Override
    protected int setTitleBar() {
        return R.id.web_toolbar;
    }
}
