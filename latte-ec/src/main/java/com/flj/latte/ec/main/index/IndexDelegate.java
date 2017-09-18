package com.flj.latte.ec.main.index;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.flj.latte.ec.R;
import com.flj.latte.ec.R2;
import com.flj.latte.ec.main.EcBottomDelegate;
import com.imooc.core.delegates.bottom.BottomItemDelegate;
import com.joanzapata.iconify.widget.IconTextView;
import com.latte.ui.recycler.BaseDecoration;
import com.latte.ui.refresh.RefreshHandler;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页
 * Created by Administrator on 2017/9/16 0016.
 */

public class IndexDelegate extends BottomItemDelegate implements View.OnFocusChangeListener {

    @BindView(R2.id.rv_index)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.srl_index)
    SwipeRefreshLayout mRefreshLayout = null;
    @BindView(R2.id.tb_index)
    Toolbar mToolbar = null;
    @BindView(R2.id.icon_index_scan)
    IconTextView mIconScan = null;
    @BindView(R2.id.et_search_view)
    AppCompatEditText mSearchView = null;


    private RefreshHandler mRefreshHandler = null;//下拉刷新回调监听

    @OnClick(R2.id.icon_index_scan)
    void onClickScanQrCode(){



    }

    //初始化下拉刷新
    private void initRefreshLayout(){
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mRefreshLayout.setProgressViewOffset(true,120,300);

    }


    private void initRecyclerView(){

        final GridLayoutManager manager = new GridLayoutManager(getContext(),4);
        mRecyclerView.setLayoutManager(manager);
        //添加分割线
        mRecyclerView.addItemDecoration(BaseDecoration.create(ContextCompat.getColor(getContext(),R.color.app_background),5));
        final EcBottomDelegate ecBottomDelegate = getParentDelegate();//app的Fragment模块管理类
        mRecyclerView.addOnItemTouchListener(IndexItemClickListener.create(ecBottomDelegate));//RecyclerView点击监听


    }

    //Fragment 懒加载
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();

        mRefreshHandler.firstPage("index.php");



    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        super.onBindView(savedInstanceState, rootView);
       // mRefreshHandler = RefreshHandler.create(mRefreshLayout,mRecyclerView,new )

    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {

        if (hasFocus){ //搜索
           // getParentDelegate().getSupportDelegate().start(new SearchDel);
        }
    }
}
