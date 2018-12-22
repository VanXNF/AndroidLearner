package top.vanxnf.androidlearner.base;

import android.content.Context;

import androidx.appcompat.widget.Toolbar;
import top.vanxnf.androidlearner.R;

public class BaseMainFragment extends BaseFragment {

    protected OnFragmentOpenDrawerListener mOpenDrawerListener;

    protected void initToolbarNav(Toolbar toolbar) {
        initToolbarNav(toolbar, false);
    }

    protected void initToolbarNav(Toolbar toolbar, boolean isHome) {
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener((v) -> {
            if (mOpenDrawerListener != null) {
                mOpenDrawerListener.onOpenDrawer();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentOpenDrawerListener) {
            mOpenDrawerListener = (OnFragmentOpenDrawerListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentOpenDrawerListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOpenDrawerListener = null;
    }

    public interface OnFragmentOpenDrawerListener {
        void onOpenDrawer();
    }
}
