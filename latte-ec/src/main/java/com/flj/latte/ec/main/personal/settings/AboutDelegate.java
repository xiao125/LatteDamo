package com.flj.latte.ec.main.personal.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.flj.latte.ec.R;
import com.flj.latte.ec.R2;
import com.imooc.core.delegates.LatteDelegate;
import com.imooc.core.net.RestClient;
import com.imooc.core.net.callback.ISuccess;

import butterknife.BindView;

/**
 * 关于界面
 * Created by Administrator on 2017/10/20 0020.
 */

public class AboutDelegate extends LatteDelegate {

    @BindView(R2.id.tv_info)
    AppCompatTextView mTextView = null;


    @Override
    public Object setLayout() {
        return  R.layout.delegate_about;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        RestClient.builder()
                .url("about.php")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final String info = JSON.parseObject(response).getString("data");
                        mTextView.setText(info);
                    }
                })
                .build()
                .get();

    }
}
