package top.vanxnf.androidlearner.category.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

import androidx.annotation.Nullable;
import top.vanxnf.androidlearner.R;
import top.vanxnf.androidlearner.category.model.entity.Category;

public class CategoryAdapter extends BaseQuickAdapter<Category.DataBean, BaseViewHolder> {

    public CategoryAdapter(@Nullable List<Category.DataBean> data) {
        super(R.layout.item_list_category, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Category.DataBean item) {
        helper.setText(R.id.category_name_text, item.getName());
        ChipGroup chipGroup = helper.getView(R.id.category_chip_group);
        chipGroup.removeAllViews();
        for (Category.DataBean.Subcategory subcategory : item.getSubcategories()) {
            Chip chip = new Chip(mContext);
            chip.setId(subcategory.getId());
            chip.setTextAppearance(R.style.Chip_TextAppearance);
            chip.setText(subcategory.getName());
            chip.setChipStartPaddingResource(R.dimen.dp_10);
            chip.setChipEndPaddingResource(R.dimen.dp_10);
            chipGroup.addView(chip);
            helper.addOnClickListener(chip.getId());
        }
    }
}
