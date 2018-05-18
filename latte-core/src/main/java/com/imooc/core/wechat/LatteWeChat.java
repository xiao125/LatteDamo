package com.imooc.core.wechat;

import android.app.Activity;

import com.imooc.core.app.ConfigKeys;
import com.imooc.core.app.Latte;
import com.imooc.core.wechat.callbacks.IWeChatSignInCallback;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 微信登录基础配置
 * Created by Administrator on 2017/9/15 0015.
 */

public class LatteWeChat {

    public static final String APP_ID = Latte.getConfiguration(ConfigKeys.WE_CHAT_APP_ID);
    public static final String APP_SECRET = Latte.getConfiguration(ConfigKeys.WE_CHAT_APP_SECRET);

    private final IWXAPI WXAPI; //IWXAPI 是第三方app和微信通信的openapi接口

    private IWeChatSignInCallback mSignInCallback = null;

    private static final class Holder{

        private static final LatteWeChat INSTANCE = new LatteWeChat();
    }


    public static LatteWeChat getInstance(){
        return Holder.INSTANCE;

    }

    private LatteWeChat(){

        final Activity activity = Latte.getConfiguration(ConfigKeys.ACTIVITY); //上下文
        WXAPI = WXAPIFactory.createWXAPI(activity,APP_ID,true);//获取IWXAPI的实例
        WXAPI.registerApp(APP_ID);//将应用的appId注册到微信

    }


    //获取WXAPI
    public final IWXAPI getWXAPI(){

        return WXAPI;
    }

    public LatteWeChat onSignSuccess(IWeChatSignInCallback callback){

        this.mSignInCallback = callback;
        return  this;
    }

    public IWeChatSignInCallback getSignInCallback() {
        return mSignInCallback;
    }

    //发送微信登录请求（微信登录成功后回调个Activity： wxapi.WXEntryActivity）
    public final void signIn(){

        final SendAuth.Req  req = new SendAuth.Req();
        req.scope="snsapi_userinfo";
        req.state="random_state";
        WXAPI.sendReq(req);
    }


}
