package com.latte.ui.banner;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

/**
 * Created by Administrator on 2017/9/18 0018.
 */

public class HolderCreator implements CBViewHolderCreator<ImageHolder> {
    @Override
    public ImageHolder createHolder() {
        return new ImageHolder();
    }
}
