package com.flj.latte.ec.main.sort.content;

import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flj.latte.ec.R;

import java.util.List;

/**
 * Created by Administrator on 2017/9/19 0019.
 */

public class SectionAdapter extends BaseSectionQuickAdapter<SectionBean,BaseViewHolder> {

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate();

    public SectionAdapter(int layoutResId, int sectionHeadResId, List<SectionBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SectionBean item) {

        helper.setText(R.id.header,item.header);
        helper.setVisible(R.id.more,item.ismIsMore());
        helper.addOnClickListener(R.id.more);

    }

    @Override
    protected void convert(BaseViewHolder helper, SectionBean item) {
        //item.t返回SectionBean类型
        final String thumb = item.t.getmGoodsThumb();
        final String name = item.t.getmGoodsName();
        final int goodsId = item.t.getmGoodsId();
        final SectionContentItemEntity entity = item.t;

        helper.setText(R.id.tv,name);
        final AppCompatImageView goodsImageView = helper.getView(R.id.iv);
        Glide.with(mContext)
                .load(thumb)
                .into(goodsImageView);

    }
}
