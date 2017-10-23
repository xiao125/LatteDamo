package com.imooc.lattedamo;

import android.app.Application;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDexApplication;

import com.flj.latte.ec.database.DatabaseManager;
import com.flj.latte.ec.icon.FontEcModule;
import com.imooc.core.app.Latte;
import com.imooc.core.net.interceptors.DebugInterceptor;
import com.imooc.core.net.rx.AddCookieInterceptor;
import com.imooc.core.util.callback.CallbackManager;
import com.imooc.core.util.callback.CallbackType;
import com.imooc.core.util.callback.IGlobalCallback;
import com.imooc.lattedamo.event.ShareEvent;
import com.imooc.lattedamo.event.TestEvent;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/9/4.
 */

public class MyApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Latte.init(this)
                .withIcon(new FontAwesomeModule()) //设置矢量图
                .withIcon(new FontEcModule()) //设置矢量图
                .withLoaderDelayed(1000)
                .withApiHost("http://952cloud.top/RestServer/api/")
                .withInterceptor(new DebugInterceptor("test",R.raw.test))
                .withWeChatAppId("你的微信AppKey")
                .withWechatAppSecret("你的微信AppSecret")
                .withWebEvent("test",new TestEvent())
                .withWebEvent("share",new ShareEvent())
                .withWebHost("https://www.baidu.com/")//添加Cookie同步拦截器
                .withInterceptor(new AddCookieInterceptor()) //添加cookit拦截器
                .configure();


        //初始化数据库
        DatabaseManager.getInstance().init(this);

        //开启极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);


        CallbackManager.getInstance()
                .addCallback(CallbackType.TAG_OPEN_PUSH, new IGlobalCallback() {
                    @Override
                    public void executeCallback(@Nullable Object args) {
                        if (JPushInterface.isPushStopped(Latte.getApplicationContext())){
                            //开启极光推送
                            JPushInterface.setDebugMode(true);
                            JPushInterface.init(Latte.getApplicationContext());

                        }
                    }
                })
                .addCallback(CallbackType.TAG_STOP_PUSH, new IGlobalCallback() {
                    @Override
                    public void executeCallback(@Nullable Object args) {
                        if (!JPushInterface.isPushStopped(Latte.getApplicationContext())) {

                            //关闭极光推送
                            JPushInterface.stopPush(Latte.getApplicationContext());
                        }
                    }
                });



    }
}
