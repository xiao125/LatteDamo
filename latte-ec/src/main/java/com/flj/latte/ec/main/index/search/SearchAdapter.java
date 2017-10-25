package com.flj.latte.ec.main.index.search;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.flj.latte.ec.R;
import com.latte.ui.recycler.MultipleFields;
import com.latte.ui.recycler.MultipleItemEntity;
import com.latte.ui.recycler.MultipleRecyclerAdapter;
import com.latte.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/10/25 0025.
 */

public class SearchAdapter extends MultipleRecyclerAdapter {


    public SearchAdapter(List<MultipleItemEntity> data) {
        super(data);
        //添加布局
        addItemType(SearchItemType.ITEM_SEARCH, R.layout.item_search);

    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (entity.getItemType()){
            case SearchItemType.ITEM_SEARCH:
                final AppCompatTextView textView = holder.getView(R.id.tv_search_item);
                final String history = entity.getField(MultipleFields.TEXT);
                textView.setText(history); //显示数据

                break;
        }






    }
}
