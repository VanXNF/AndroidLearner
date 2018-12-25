package top.vanxnf.androidlearner.home.view.adapter;

import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


import top.vanxnf.androidlearner.R;
import top.vanxnf.androidlearner.glide.GlideApp;
import top.vanxnf.androidlearner.entity.Article;


public class HomeArticleAdapter extends BaseQuickAdapter<Article.DataBean.ArticleData, BaseViewHolder> {

    public HomeArticleAdapter(@Nullable List<Article.DataBean.ArticleData> data) {
        super(R.layout.item_list_article_card, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Article.DataBean.ArticleData item) {
        helper.setText(R.id.article_author_text, item.getAuthor())
                .setText(R.id.article_time_text, item.getNiceDate())
                .setText(R.id.article_category_text, item.getSuperChapterName() + "/" + item.getChapterName())
                .setText(R.id.article_title_text, Html.fromHtml(item.getTitle()));


        TextView descText = helper.getView(R.id.article_desc_text);
        ImageView projectImage = helper.getView(R.id.article_project_image);
        if (!TextUtils.isEmpty(item.getDesc())) {
            descText.setText(item.getDesc());
            GlideApp.with(mContext)
                    .load(item.getEnvelopePic())
                    .into((ImageView) helper.getView(R.id.article_project_image));

            descText.setVisibility(View.VISIBLE);
            projectImage.setVisibility(View.VISIBLE);
        } else {
            descText.setVisibility(View.GONE);
            projectImage.setVisibility(View.GONE);
        }
    }
}
