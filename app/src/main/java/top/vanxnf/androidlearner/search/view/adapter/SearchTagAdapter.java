package top.vanxnf.androidlearner.search.view.adapter;

import android.content.Context;
import android.support.design.chip.Chip;
import android.view.LayoutInflater;
import android.view.View;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import top.vanxnf.androidlearner.R;
import top.vanxnf.androidlearner.entity.HotKey;

public class SearchTagAdapter extends TagAdapter<HotKey.Key> {

    private TagFlowLayout view;

    public SearchTagAdapter(List<HotKey.Key> data, TagFlowLayout view) {
        super(data);
        this.view = view;
    }

    @Override
    public View getView(FlowLayout parent, int position, HotKey.Key key) {
        Chip chip = (Chip) LayoutInflater.from(view.getContext()).inflate(R.layout.item_tag, view, false);
        chip.setText(key.getName());
        return chip;
    }
}
