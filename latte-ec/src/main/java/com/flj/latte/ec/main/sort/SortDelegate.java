package com.flj.latte.ec.main.sort;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.flj.latte.ec.R;
import com.flj.latte.ec.main.sort.content.ContentDelegate;
import com.flj.latte.ec.main.sort.list.VerticaIListDelegate;
import com.imooc.core.delegates.bottom.BottomItemDelegate;

/**
 * 分类
 * Created by Administrator on 2017/9/16 0016.
 */

public class SortDelegate extends BottomItemDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        super.onBindView(savedInstanceState, rootView);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        //左边商品分类用 RecyclerView 显示
        final VerticaIListDelegate listDelegate = new VerticaIListDelegate();
        getSupportDelegate().loadRootFragment(R.id.vertical_list_container,listDelegate);

        //右边商品分类详细信息用 RecyclerView 显示
        ///设置右侧第一个分类显示，默认显示分类一
        getSupportDelegate().loadRootFragment(R.id.sort_content_container, ContentDelegate.newInstance(1));


    }
}
