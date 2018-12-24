package top.vanxnf.androidlearner.search.view.adapter;

import android.support.annotation.Nullable;
import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import top.vanxnf.androidlearner.R;
import top.vanxnf.androidlearner.entity.Article;

public class ResultAdapter extends BaseQuickAdapter<Article.DataBean.ArticleData, BaseViewHolder> {

    public ResultAdapter(@Nullable List<Article.DataBean.ArticleData> data) {
        super(R.layout.item_list_article, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Article.DataBean.ArticleData item) {
        CharSequence title = Html.fromHtml(item.getTitle()
                .replace("<em class='highlight'>", "<font color=\"red\">")
                .replace("</em>", "</font>"));
        helper.setText(R.id.author_name_text, item.getAuthor())
                .setText(R.id.release_time_text, item.getNiceDate())
                .setText(R.id.release_title_text, title);
    }
}
