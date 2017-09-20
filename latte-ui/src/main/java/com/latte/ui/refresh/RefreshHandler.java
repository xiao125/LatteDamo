package com.latte.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.imooc.core.app.Latte;
import com.imooc.core.net.RestClient;
import com.imooc.core.net.callback.ISuccess;
import com.imooc.core.util.log.LatteLogger;
import com.latte.ui.recycler.DataConverter;
import com.latte.ui.recycler.MultipleRecyclerAdapter;

/**
 * 下拉刷新，上拉加载更多
 * Created by Administrator on 2017/9/18 0018.
 */

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {


    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;//记录当前滑动的位置
    private final RecyclerView RECYCLERVIEW;
    private MultipleRecyclerAdapter mAdapter =null;
    private final DataConverter CONVERTER; //json数据

    private RefreshHandler(SwipeRefreshLayout swipeRefreshLayout,
                           RecyclerView recyclerView,
                           DataConverter converter, PagingBean bean) {
        this.REFRESH_LAYOUT = swipeRefreshLayout;
        this.RECYCLERVIEW = recyclerView;
        this.CONVERTER = converter;
        this.BEAN = bean;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static RefreshHandler create(SwipeRefreshLayout swipeRefreshLayout,
                                        RecyclerView recyclerView,DataConverter converter){

        return new RefreshHandler(swipeRefreshLayout,recyclerView,converter,new PagingBean());
    }


    private void refresh(){
        REFRESH_LAYOUT.setRefreshing(true);//开启自动加载
        Latte.getHandler().postDelayed(new Runnable() {//启动线程请求网络
            @Override
            public void run() {

                //进行一些网络请求
                REFRESH_LAYOUT.setRefreshing(false);//关闭刷新
            }
        },100);

    }

    public void firstPage(String url){

        BEAN.setDelayed(1000); //加载延迟
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {

                        final JSONObject object = JSON.parseObject(response);
                        BEAN.setTotal(object.getInteger("total"))
                                .setPageSize(object.getInteger("page_size")); //总数据条数

                        //设置Adapter
                        mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(response)); //适配器参数传入返回的json数据
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this,RECYCLERVIEW);
                        RECYCLERVIEW.setAdapter(mAdapter);
                        BEAN.addIndex();  //当前是第几页

                    }
                })
                .build()
                .get();

    }


    private void paging(final String url){

        final int pageSize = BEAN.getPageSize();
        final int currentCount = BEAN.getCurrentCount();
        final int total = BEAN.getmTotal();
        final int index = BEAN.getmPageIndex();

        //当前适配器的数据条数小于一页显示几条数据，或者当前已经显示了几条数据大于总条数
        if (mAdapter.getData().size() < pageSize || currentCount >= total){ //没有更多了
            mAdapter.loadMoreEnd(true);//,数据全部加载完之后调用
        }else {

            Latte.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    RestClient.builder()
                            .url(url+index)
                            .success(new ISuccess() {
                                @Override
                                public void onSuccess(String response) {

                                    LatteLogger.json("paging",response);

                                    CONVERTER.clearData();
                                    mAdapter.addData(CONVERTER.setJsonData(response).convert());

                                    //累加数量
                                    BEAN.setCurrentCount(mAdapter.getData().size());
                                    mAdapter.loadMoreComplete();//每次网络请求成功之后调用
                                    BEAN.addIndex(); //加载下一页


                                }
                            })
                            .build()
                            .get();
                }
            },1000);

        }


    }


    @Override
    public void onRefresh() { //下拉刷新

        refresh();  //加载数据

    }


    @Override
    public void onLoadMoreRequested() {//上拉加载更多

        paging("refresh.php?index=");
    }
}
