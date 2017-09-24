package com.flj.latte.ec.main.cart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewStubCompat;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.flj.latte.ec.R;
import com.flj.latte.ec.R2;
import com.flj.latte.ec.pay.FastPay;
import com.flj.latte.ec.pay.IAlPayResultListener;
import com.imooc.core.delegates.bottom.BottomItemDelegate;
import com.imooc.core.net.RestClient;
import com.imooc.core.net.callback.ISuccess;
import com.imooc.core.util.log.LatteLogger;
import com.joanzapata.iconify.widget.IconTextView;
import com.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 购物车
 * Created by Administrator on 2017/9/16 0016.
 */

public class ShopCartDelegate extends BottomItemDelegate  implements ISuccess,ICartItemListener,IAlPayResultListener{

    private ShopCartAdapter mAdapter =null;


    private int mCurrentCount = 0;//当前选中的item的数量
    private int mTotalCount =0;//Recycler的item总数量
    private double mTotalPrice =0.00;


    @BindView(R2.id.rv_shop_cart)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.icon_shop_cart_select_all)
    IconTextView mIconSelectAll = null;
    @BindView(R2.id.stub_no_item)
    ViewStubCompat mStubNoItem = null;
    @BindView(R2.id.tv_shop_cart_total_price)
    AppCompatTextView mTvTotalPrice = null;
    @BindView(R2.id.tv_shop_cart_pay)
    AppCompatTextView mvshopcartpay = null;

    @OnClick(R2.id.icon_shop_cart_select_all)
    void onClickSelectAll(){ //全选/反选

        final int tag = (int) mIconSelectAll.getTag(); //根据tag判断是否选中
        if (tag == 0){
            mIconSelectAll.setTextColor(ContextCompat.getColor(getContext(), R.color.app_main));
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

    @OnClick(R2.id.tv_top_shop_cart_remove_selected)
    void onClickRemoveSelectedItem(){  //删除

        final List<MultipleItemEntity> data = mAdapter.getData();
        //要删除的数据
        final List<MultipleItemEntity> deleteEntities = new ArrayList<>();
        for (MultipleItemEntity entity :data){  //取出需要删除的item
            final boolean isSelected  = entity.getField(ShopCartItemFields.IS_SELECTED); //获取是否选中状态
            if (isSelected){ //选中的item就是需要移除的item
                deleteEntities.add(entity); //先保存起来
            }
        }
        for (MultipleItemEntity entity :deleteEntities){
            int removePosition; //需要删除的item数量
            final int entityPosition = entity.getField(ShopCartItemFields.POSITION); //获取Recycler的item的位置
            if (entityPosition>mCurrentCount-1){ //如果item的数量大于当前选中的item的数量

                removePosition = entityPosition -(mTotalCount - mCurrentCount); //需要删除的item就数量

            }else {

                removePosition = entityPosition; //Recycler的item的总数量

            }
            if (removePosition <=mAdapter.getItemCount()){  //防止如果item没有了溢出（如果需要移除的item数量小于item总数）
                mAdapter.remove(removePosition); //移除item
                mCurrentCount = mAdapter.getItemCount(); //移除item后，Recycler中的item总数量

                //更新数据
                mAdapter.notifyItemRangeChanged(removePosition,mAdapter.getItemCount());
            }
        }

        //显示购物车是否有商品
        checkItemCount();


    }

    @OnClick(R2.id.tv_top_shop_cart_clear)
    void onClickClear(){ //清除

        mAdapter.getData().clear();
        mAdapter.notifyDataSetChanged();

        checkItemCount();//ReclcyView没有item就是购物车没有商品，显示去购物


    }

    @OnClick(R2.id.tv_shop_cart_pay)
    void onClickPay() { //结算

        createOrder();
    }


    //创建订单，注意，和支付是没有关系的
    private void createOrder(){
        final String orderUrl="";
        final WeakHashMap<String,Object> orderParams = new WeakHashMap<>();
        orderParams.put("userid","");
        orderParams.put("amount",0.01);
        orderParams.put("comment","测试支付");
        orderParams.put("ordertype",0);

        RestClient.builder()
                .url(orderUrl)
                .loader(getContext())
                .params(orderParams)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {

                        LatteLogger.d("ORDER",response);
                        final int orderId = JSON.parseObject(response).getInteger("result");

                        FastPay.create(ShopCartDelegate.this)
                                .setPayResultListener(ShopCartDelegate.this)
                                .setOrderId(orderId)
                                .beginPayDialog();
                    }
                })
                .build()
                .post();


    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        mIconSelectAll.setTag(0); //初始化的时候设置默认的tag

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url("shop_cart.php")
                .loader(getContext())
                .success(this)
                .build()
                .get();
    }

    @Override
    public void onSuccess(String response) {

        //解析josn
        final ArrayList<MultipleItemEntity> data =
                new ShopCartDataConverter()
                        .setJsonData(response)
                        .convert();
        mAdapter = new ShopCartAdapter(data); //适配器传递参数
        mAdapter.setCartItemListener(this); //加减按钮监听
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mTotalPrice = mAdapter.getmTotalPrice();//获得解析后的总价格
        mTvTotalPrice.setText(String.valueOf(mTotalPrice));//显示购物车总价格

    }

    @Override
    public void onItemClick(double itemTotalPrice) { //显示商品总价接口，itemTotalPrice是每个item的总价格，需要时再使用

        final double price = mAdapter.getmTotalPrice(); //获取总价
        mTvTotalPrice.setText(String.valueOf(price));


    }



    @SuppressWarnings("RestrictedApi")
    private void checkItemCount(){

        final int count = mAdapter.getItemCount();
        if (count ==0){

            final View stubView = mStubNoItem.inflate(); //显示view
            final AppCompatTextView tvToBuy = (AppCompatTextView)stubView.findViewById(R.id.tv_stub_to_buy);

            tvToBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(getContext(), "你该购物啦！", Toast.LENGTH_SHORT).show();
                }
            });

            mRecyclerView.setVisibility(View.GONE);

        }else {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onPaySuccess() {

    }

    @Override
    public void onPaying() {

    }

    @Override
    public void onPayFail() {

    }

    @Override
    public void onPayCancel() {

    }

    @Override
    public void onPayConnectError() {

    }
}
