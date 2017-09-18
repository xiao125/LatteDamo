package com.latte.ui.recycler;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 *  Holder
 * Created by Administrator on 2017/9/18 0018.
 */

public class MultipleViewHolder extends BaseViewHolder {

    public MultipleViewHolder(View view) {
        super(view);
    }
    public static MultipleViewHolder create(View view){

        return new MultipleViewHolder(view);
    }

}
