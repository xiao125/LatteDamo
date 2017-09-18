package com.flj.latte.ec.main.index;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.imooc.core.delegates.LatteDelegate;
import com.latte.ui.recycler.MultipleFields;
import com.latte.ui.recycler.MultipleItemEntity;

/**
 * RecyclerView 点击监听回调
 * Created by Administrator on 2017/9/18 0018.
 */

public class IndexItemClickListener extends SimpleClickListener {

    private final LatteDelegate DELEGATE; // Fragment基类

    public IndexItemClickListener(LatteDelegate DELEGATE) {
        this.DELEGATE = DELEGATE;
    }

    public static SimpleClickListener create(LatteDelegate delegate){
        return new IndexItemClickListener(delegate);

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        final MultipleItemEntity entity = (MultipleItemEntity) baseQuickAdapter.getData().get(position);
        final int goodsId = entity.getField(MultipleFields.ID);
        //详情页面
      //  final GoodsDetailDelegate delegate = GoodsDetailDelegate.create(goodsId); //详情页面
      //  DELEGATE.getSupportDelegate().start(delegate); //跳转详情页面

    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }



    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
