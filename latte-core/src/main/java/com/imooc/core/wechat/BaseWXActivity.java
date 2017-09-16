package com.imooc.core.wechat;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * 微信基础类，当微信发送请求到你的应用，将通过IWXAPIEventHandler接口的onReq方法进行回调，类似的，应用请求微信的响应结果将通过onResp回调。
 * Created by Administrator on 2017/9/15 0015.
 */

public abstract class BaseWXActivity extends AppCompatActivity implements IWXAPIEventHandler {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //在WXEntryActivity中将接收到的intent及实现了IWXAPIEventHandler接口的对象传递给IWXAPI接口的handleIntent方法
        LatteWeChat.getInstance().getWXAPI().handleIntent(getIntent(),this);

    }

    //WXEntryActivity 登录界面模式是sigtop，所以需要onNewIntent接收信息
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        LatteWeChat.getInstance().getWXAPI().handleIntent(getIntent(),this);
    }
}
