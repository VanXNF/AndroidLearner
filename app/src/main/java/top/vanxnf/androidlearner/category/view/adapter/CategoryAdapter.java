package top.vanxnf.androidlearner.category.view.adapter;

import android.support.annotation.Nullable;
import android.support.design.chip.Chip;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import top.vanxnf.androidlearner.R;
import top.vanxnf.androidlearner.category.contract.OnTagItemClickListener;
import top.vanxnf.androidlearner.entity.Category;

public class CategoryAdapter extends BaseQuickAdapter<Category.DataBean, BaseViewHolder> {

    private static final String TAG = "CategoryAdapter";
    private OnTagItemClickListener listener;


    public CategoryAdapter(@Nullable List<Category.DataBean> data) {
        super(R.layout.item_list_category, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Category.DataBean item) {
        helper.setText(R.id.category_name_text, item.getName());
        TagFlowLayout tagFlowLayout = helper.getView(R.id.category_tag_flow_layout);
        tagFlowLayout.setAdapter(new TagAdapter<Category.DataBean.Subcategory>(item.getSubcategories()) {

            @Override
            public View getView(FlowLayout parent, int position, Category.DataBean.Subcategory subcategory) {
                if (subcategory != null) {
                    Chip tag = (Chip) LayoutInflater.from(mContext).inflate(R.layout.item_tag, tagFlowLayout, false);
                    tag.setText(subcategory.getName());
                    tagFlowLayout.setOnTagClickListener((View view, int index, FlowLayout container) -> {
                        listener.onClick(index, item.getSubcategories());
                        return true;
                    });
                    return tag;
                } else {
                    return null;
                }
            }
        });
    }

    public void addOnTagItemClickListener(OnTagItemClickListener listener) {
        this.listener = listener;
    }
}
