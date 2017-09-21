package com.flj.latte.ec.main.cart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewStubCompat;
import android.view.View;

import com.flj.latte.ec.R;
import com.flj.latte.ec.R2;
import com.imooc.core.delegates.bottom.BottomItemDelegate;
import com.imooc.core.net.RestClient;
import com.imooc.core.net.callback.ISuccess;
import com.joanzapata.iconify.widget.IconTextView;
import com.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 购物车
 * Created by Administrator on 2017/9/16 0016.
 */

public class ShopCartDelegate extends BottomItemDelegate  implements ICartItemListener{

    private ShopCartAdapter mAdapter =null;

    //购物车数量标记
    private int mCurrentCount = 0;
    private int mTotalCount =0;
    private double mTotalPrice =0.00;

    @BindView(R2.id.rv_shop_cart)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.icon_shop_cart_select_all)
    IconTextView mIconSelectAll = null;
    @BindView(R2.id.stub_no_item)
    ViewStubCompat mStubNoItem = null;
    @BindView(R2.id.tv_shop_cart_total_price)
    AppCompatTextView mTvTotalPrice = null;

    @OnClick(R2.id.icon_shop_cart_select_all)
    void onClickSelectAll(){ //全选/反选

        final int tag = (int) mIconSelectAll.getTag();
        if (tag == 0){
            mIconSelectAll.setTextColor(ContextCompat.getColor(getContext(),R.color.app_main));
            mIconSelectAll.setTag(1);
            mAdapter.setIsSelectedAll(true);
            //更新RecyclerView的显示状态
            mAdapter.notifyItemRangeChanged(0,mAdapter.getItemCount());

        }else {
            mIconSelectAll.setTextColor(Color.GRAY);
            mIconSelectAll.setTag(0);
            mAdapter.setIsSelectedAll(false);
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());

        }

    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        mIconSelectAll.setTag(0);

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url("shop_cart.php")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {

                        //解析josn
                        final ArrayList<MultipleItemEntity> data =
                                new ShopCartDataConverter()
                                .setJsonData(response)
                                .convert();





                    }
                })
                .build()
                .get();



    }


    @Override
    public void onItemClick(double itemTotalPrice) {



    }
}
