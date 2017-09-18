package com.latte.ui.recycler;


import android.support.annotation.ColorInt;

import com.choices.divider.DividerItemDecoration;

/**
 * recyclerView分割线
 * Created by Administrator on 2017/9/18 0018.
 */

public class BaseDecoration extends DividerItemDecoration {

    private BaseDecoration(@ColorInt int color,int size){
        setDividerLookup(new DividerLookupImpl(color,size));

    }

    public static BaseDecoration create(@ColorInt int color,int size){

        return new BaseDecoration(color,size);
    }




}
