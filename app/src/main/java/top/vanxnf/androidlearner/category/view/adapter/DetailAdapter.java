package top.vanxnf.androidlearner.category.view.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import top.vanxnf.androidlearner.R;
import top.vanxnf.androidlearner.entity.Article;

public class DetailAdapter extends BaseQuickAdapter <Article.DataBean.ArticleData, BaseViewHolder> {

    public DetailAdapter(@Nullable List<Article.DataBean.ArticleData> data) {
        super(R.layout.item_list_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Article.DataBean.ArticleData item) {
        helper.setText(R.id.author_name_text, item.getAuthor())
                .setText(R.id.release_time_text, item.getNiceDate())
                .setText(R.id.release_title_text, item.getTitle());
    }
}
