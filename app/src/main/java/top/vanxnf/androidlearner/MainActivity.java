package top.vanxnf.androidlearner;


import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import me.yokeyword.fragmentation.ISupportFragment;
import top.vanxnf.androidlearner.view.base.BaseActivity;
import top.vanxnf.androidlearner.view.base.BaseFragment;
import top.vanxnf.androidlearner.view.base.BaseMainFragment;
import top.vanxnf.androidlearner.view.HomeFragment;

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
//        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = findViewById(R.id.main_nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setCheckedItem(R.id.nav_home);
        Resources resource= getResources();
        ColorStateList csl = resource.getColorStateList(R.color.nav_menu_selector, null);
        mNavigationView.setItemTextColor(csl);
        mNavigationView.setItemIconTintList(csl);

//        LinearLayout llNavHeader = (LinearLayout) mNavigationView.getHeaderView(0);
//        mTvName = (TextView) llNavHeader.findViewById(R.id.tv_name);
//        mImgNav = (ImageView) llNavHeader.findViewById(R.id.img_nav);
//        llNavHeader.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDrawer.closeDrawer(GravityCompat.START);
//                mDrawer.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        goLogin();
//                    }
//                }, 250);
//            }
//        });
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

    /**
     * 打开抽屉
     */
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
                Bundle newBundle = new Bundle();
                newBundle.putString("from", "From:" + topFragment.getClass().getSimpleName());
                fragment.putNewBundle(newBundle);

                myHome.start(fragment, BaseFragment.SINGLETASK);
            } else if (id == R.id.nav_official_account) {
                // TODO: 18-12-21 official account page
                Toast.makeText(this, "敬请期待", Toast.LENGTH_SHORT).show();
            }
//            } else if (id == R.id.nav_discover) {
//                DiscoverFragment fragment = findFragment(DiscoverFragment.class);
//                if (fragment == null) {
//                    myHome.startWithPopTo(DiscoverFragment.newInstance(), HomeFragment.class, false);
//                } else {
//                    // 如果已经在栈内,则以SingleTask模式start
//                    myHome.start(fragment, SupportFragment.SINGLETASK);
//                }
//            } else if (id == R.id.nav_shop) {
//                ShopFragment fragment = findFragment(ShopFragment.class);
//                if (fragment == null) {
//                    myHome.startWithPopTo(ShopFragment.newInstance(), HomeFragment.class, false);
//                } else {
//                    // 如果已经在栈内,则以SingleTask模式start,也可以用popTo
////                        start(fragment, SupportFragment.SINGLETASK);
//                    myHome.popTo(ShopFragment.class, false);
//                }
//            } else if (id == R.id.nav_login) {
//                goLogin();
//            } else if (id == R.id.nav_swipe_back) {
//                startActivity(new Intent(MainActivity.this, SwipeBackSampleActivity.class));
//            }

        }, 300);
        return true;
    }
}
