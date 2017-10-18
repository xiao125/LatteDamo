package com.flj.latte.ec.main.personal.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.flj.latte.ec.R;
import com.imooc.core.delegates.LatteDelegate;

/**
 * Created by Administrator on 2017/10/15.
 */

public class NameDelegate extends LatteDelegate {

    @Override
    public Object setLayout() {

        return R.layout.delegate_name;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
    }


}
