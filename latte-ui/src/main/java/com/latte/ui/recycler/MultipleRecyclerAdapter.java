package com.latte.ui.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.latte.ui.R;
import com.latte.ui.banner.BannerCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseMultiItemQuickAdapter<MultipleItemEntity,MultipleViewHolder>
 *   第一个泛型是数据实体类型
 *   第二个ViewHolder其目的是为了支持扩展ViewHolder。
 * Created by Administrator on 2017/9/18 0018.
 */

public class MultipleRecyclerAdapter extends BaseMultiItemQuickAdapter<MultipleItemEntity,MultipleViewHolder>
        implements BaseQuickAdapter.SpanSizeLookup,
        OnItemClickListener {

    //确保初始化一次Banner，防止重复Item加载
    private boolean mIsInitBanner = false;

    //使用glide 设置图片加载策略
    private static final RequestOptions RECYCLER_OPTIONS =
            new RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate();

    public MultipleRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        init();
    }

    public static MultipleRecyclerAdapter create(List<MultipleItemEntity> data){

        return new MultipleRecyclerAdapter(data);
    }

    public static  MultipleRecyclerAdapter create(DataConverter converter){ //DataConverter 数据实体类

        return new MultipleRecyclerAdapter(converter.convert());
    }

    public void refresh(List<MultipleItemEntity> data){

        getData().clear();
        setNewData(data);
        notifyDataSetChanged(); //更新视图
    }


    private void init(){

        //设置不同的item布局 ，addItemType绑定type和layout的关系
        addItemType(ItemType.TEXT, R.layout.item_multiple_text);
        addItemType(ItemType.IMAGE,R.layout.item_multiple_image);
        addItemType(ItemType.TEXT_IMAGE,R.layout.item_multiple_image_text);
        addItemType(ItemType.BANNER,R.layout.item_multiple_banner);

        //设置宽度监听
        setSpanSizeLookup(this);
        //添加列表加载动画(默认为渐显效果)
        openLoadAnimation();
        //如果想重复执行可设置,多次执行动画
        isFirstOnly(false);

    }

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }


    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {

        final String text;
        final String imageUrl;
        final ArrayList<String> bannerImages;
        switch (holder.getItemViewType()){

            case ItemType.TEXT: //显示标题
                text = entity.getField(MultipleFields.TEXT);
                holder.setText(R.id.text_single,text);
                break;

            case ItemType.IMAGE:

                imageUrl = entity.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(RECYCLER_OPTIONS)
                        .into((ImageView) holder.getView(R.id.img_single));

                break;


            case ItemType.TEXT_IMAGE:

                text = entity.getField(MultipleFields.TEXT);
                imageUrl = entity.getField(MultipleFields.IMAGE_URL);

                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(RECYCLER_OPTIONS)
                        .into((ImageView) holder.getView(R.id.img_multiple));

                holder.setText(R.id.tv_multiple,text);
                break;

            case ItemType.BANNER:

                if (!mIsInitBanner){
                    bannerImages = entity.getField(MultipleFields.BANNERS);
                    final ConvenientBanner<String> convenientBanner = holder.getView(R.id.banner_recycler_item);
                    //banner 图片设置
                    BannerCreator.setDefault(convenientBanner,bannerImages,this);
                    mIsInitBanner =true;
                }
                break;

            default:
                break;

        }

    }


    //banner 的点击回调

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {

        return getData().get(position).getField(MultipleFields.SPAN_SIZE);
    }


}
