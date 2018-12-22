package top.vanxnf.androidlearner.base;

import androidx.appcompat.widget.Toolbar;
import top.vanxnf.androidlearner.R;

public class BaseBackFragment extends BaseFragment {

    protected void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener((v) -> pop());
    }

}
