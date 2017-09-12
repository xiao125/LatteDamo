package com.imooc.core.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.imooc.core.app.Latte;

/**
 * Created by Administrator on 2017/9/11.
 */

public final class DimenUtil {

    //获取宽度
    public static int getScreenWidth(){

        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm= resources.getDisplayMetrics();
        return dm.widthPixels;

    }

    //获取高度
    public static int getScreenHeight(){

        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm= resources.getDisplayMetrics();
        return dm.heightPixels;

    }


}
