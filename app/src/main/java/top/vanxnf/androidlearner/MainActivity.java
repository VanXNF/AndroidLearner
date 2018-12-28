package top.vanxnf.androidlearner;


import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;

import me.yokeyword.fragmentation.ISupportFragment;
import top.vanxnf.androidlearner.category.view.CategoryFragment;
import top.vanxnf.androidlearner.home.view.HomeFragment;
import top.vanxnf.androidlearner.base.BaseActivity;
import top.vanxnf.androidlearner.base.BaseFragment;
import top.vanxnf.androidlearner.base.BaseMainFragment;


public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, BaseMainFragment.OnFragmentOpenDrawerListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    private TextView mTvName;   // NavigationView上的名字
    private ImageView mImgNav;  // NavigationView上的头像

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImmersionBar.with(this).init();
        BaseFragment fragment = findFragment(HomeFragment.class);
        if (fragment == null) {
            loadRootFragment(R.id.main_frame_container, HomeFragment.newInstance());
        }
        initView();
    }

    private void initView() {
        mDrawer = findViewById(R.id.main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = findViewById(R.id.main_nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setCheckedItem(R.id.nav_home);
        Resources resource= getResources();
        ColorStateList csl = resource.getColorStateList(R.color.nav_menu_selector, null);
        mNavigationView.setItemTextColor(csl);
        mNavigationView.setItemIconTintList(csl);
    }

    @Override
    public void onBackPressedSupport() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            ISupportFragment topFragment = getTopFragment();

            // 主页的Fragment
            if (topFragment instanceof BaseMainFragment) {
                mNavigationView.setCheckedItem(R.id.nav_home);
            }

            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                pop();
            } else {
                if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                    finish();
                } else {
                    TOUCH_TIME = System.currentTimeMillis();
                    Toast.makeText(this, R.string.press_again_exit, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onOpenDrawer() {
        if (!mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        mDrawer.closeDrawer(GravityCompat.START);

        mDrawer.postDelayed(() -> {
            int id = item.getItemId();
            final ISupportFragment topFragment = getTopFragment();
            BaseFragment myHome = (BaseFragment) topFragment;

            if (id == R.id.nav_home) {
                HomeFragment fragment = findFragment(HomeFragment.class);
                myHome.start(fragment, BaseFragment.SINGLETASK);
            } else if (id == R.id.nav_category) {
                CategoryFragment fragment = findFragment(CategoryFragment.class);
                if (fragment == null) {
                    myHome.startWithPopTo(CategoryFragment.newInstance(), HomeFragment.class, false);
                } else {
                    myHome.start(fragment, BaseFragment.SINGLETASK);
                }
            }
        }, 300);
        return true;
    }

    public void setDrawerState(boolean isLocked) {
        if (isLocked) {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }
}
