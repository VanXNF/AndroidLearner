package top.vanxnf.androidlearner.category.contract;

import java.util.List;

import top.vanxnf.androidlearner.entity.Category;

public interface OnTagItemClickListener {

    void onClick(int index, List<Category.DataBean.Subcategory> subcategories);
}
