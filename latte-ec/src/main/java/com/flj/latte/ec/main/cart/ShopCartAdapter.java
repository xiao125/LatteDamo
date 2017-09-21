package com.flj.latte.ec.main.cart;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.flj.latte.ec.R;
import com.imooc.core.app.Latte;
import com.imooc.core.net.RestClient;
import com.imooc.core.net.callback.ISuccess;
import com.joanzapata.iconify.internal.HasOnViewAttachListener;
import com.joanzapata.iconify.widget.IconTextView;
import com.latte.ui.recycler.MultipleFields;
import com.latte.ui.recycler.MultipleItemEntity;
import com.latte.ui.recycler.MultipleRecyclerAdapter;
import com.latte.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/9/21 0021.
 */

public class ShopCartAdapter extends MultipleRecyclerAdapter {

    private boolean mIsSelectedAll =false;
    private ICartItemListener mCartItemListener =null;
    private double mTotalPrice =0.00;

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();


    public ShopCartAdapter(List<MultipleItemEntity> data) {
        super(data);

        //初始化总价
        for (MultipleItemEntity entity :data){
            final double price = entity.getField(ShopCartItemFields.PRICE);
            final int count = entity.getField(ShopCartItemFields.COUNT);
            final double total = price * count;
            mTotalPrice = mTotalPrice +total;

        }

        //添加购物测item布局
        addItemType(ShopCartItemType.SHOP_CART_ITEM, R.layout.item_shop_cart);

    }

    public void setIsSelectedAll(boolean isSelectedAll) {
        this.mIsSelectedAll = isSelectedAll;
    }

    public void setCartItemListener(ICartItemListener listener) {
        this.mCartItemListener = listener;
    }

    public double getmTotalPrice(){
        return mTotalPrice;
    }

    @Override
    protected void convert(MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);

       switch (holder.getItemViewType()){

           case ShopCartItemType.SHOP_CART_ITEM:

               //先取出所有值
               final int id = entity.getField(MultipleFields.ID);
               final String thumb = entity.getField(MultipleFields.IMAGE_URL);
               final String title = entity.getField(ShopCartItemFields.TITLE);
               final String desc = entity.getField(ShopCartItemFields.DESC);
               final int count = entity.getField(ShopCartItemFields.COUNT);
               final double price = entity.getField(ShopCartItemFields.PRICE);
               //取出所以控件
               final AppCompatImageView imgThumb = holder.getView(R.id.image_item_shop_cart);
               final AppCompatTextView tvTitle = holder.getView(R.id.tv_item_shop_cart_title);
               final AppCompatTextView tvDesc = holder.getView(R.id.tv_item_shop_cart_desc);
               final AppCompatTextView tvPrice = holder.getView(R.id.tv_item_shop_cart_price);
               final IconTextView iconMinus = holder.getView(R.id.icon_item_minus);
               final IconTextView iconPlus = holder.getView(R.id.icon_item_plus);
               final AppCompatTextView tvCount = holder.getView(R.id.tv_item_shop_cart_count); ////数量显示
               final IconTextView iconIsSelected = holder.getView(R.id.icon_item_shop_cart); //减数量按钮


               //赋值
               tvTitle.setText(title);
               tvDesc.setText(desc);
               tvPrice.setText(String.valueOf(price));
               tvCount.setText(String.valueOf(count));
               Glide.with(mContext)
                       .load(thumb)
                       .apply(OPTIONS)
                       .into(imgThumb);

               //在左侧勾勾渲染之前改变全选与否状态
               entity.setField(ShopCartItemFields.IS_SELECTED,mIsSelectedAll);
               final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
               //根据数据状态显示左侧勾勾
               if (isSelected){

                   iconIsSelected.setTextColor(ContextCompat.getColor(Latte.getApplicationContext(),R.color.app_main));
               }else {
                   iconIsSelected.setTextColor(Color.GRAY);
               }

               //添加左侧勾勾点击事件
               iconIsSelected.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                       final boolean currentSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
                       if (currentSelected){
                           iconIsSelected.setTextColor(Color.GRAY);
                           entity.setField(ShopCartItemFields.IS_SELECTED,false);
                       }else {

                           iconIsSelected.setTextColor(ContextCompat.getColor(Latte.getApplicationContext(),R.color.app_main));
                           entity.setField(ShopCartItemFields.IS_SELECTED,true);
                       }

                   }
               });


               //加减监听事件
               iconMinus.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                       final int currentCount = entity.getField(ShopCartItemFields.COUNT); //数量显示
                       if (Integer.parseInt(tvCount.getText().toString()) >1){

                           RestClient.builder()
                                   .url("shop_cart_count.php")
                                   .loader(mContext)
                                   .params("count",currentCount)
                                   .success(new ISuccess() {
                                       @Override
                                       public void onSuccess(String response) {
                                           int countNum = Integer.parseInt(tvCount.getText().toString());
                                           countNum--;

                                           tvCount.setText(String.valueOf(countNum));
                                           if (mCartItemListener !=null){
                                               mTotalPrice = mTotalPrice -price;
                                               final double  itemTotal = countNum * price;
                                               mCartItemListener.onItemClick(itemTotal);
                                           }

                                       }
                                   })
                                   .build()
                                   .post();
                       }
                   }
               });


               //减
               iconPlus.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                       final int currentCount = entity.getField(ShopCartItemFields.COUNT);
                       RestClient.builder()
                               .url("shop_cart_count.php")
                               .loader(mContext)
                               .params("count",currentCount)
                               .success(new ISuccess() {
                                   @Override
                                   public void onSuccess(String response) {

                                       int countNum = Integer.parseInt(tvCount.getText().toString());
                                       countNum++;
                                       tvCount.setText(String.valueOf(countNum));
                                       if (mCartItemListener !=null){
                                           mTotalPrice = mTotalPrice = price;
                                           final double itemTotal = countNum * price;
                                           mCartItemListener.onItemClick(itemTotal);
                                       }

                                   }
                               })
                               .build()
                               .post();
                   }
               });

               break;
           default:
               break;
       }


    }
}
