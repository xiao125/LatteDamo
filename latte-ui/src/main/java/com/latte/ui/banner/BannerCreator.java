package com.latte.ui.banner;

import android.widget.AdapterView;

import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.latte.ui.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/18 0018.
 */

public class BannerCreator {

    public static void setDefault(ConvenientBanner<String> convenientBanner,
                                  ArrayList<String> banners,
                                 OnItemClickListener clickListener){

        convenientBanner
                .setPages(new HolderCreator(),banners)
                .setPageIndicator(new int[]{R.drawable.dot_normal,R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(clickListener)
                .setPageTransformer(new DefaultTransformer())
                .startTurning(3000)
                .setCanLoop(true);

    }
}
