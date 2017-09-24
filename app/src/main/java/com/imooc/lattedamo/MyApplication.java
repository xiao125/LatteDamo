package com.imooc.lattedamo;

import android.app.Application;

import com.flj.latte.ec.database.DatabaseManager;
import com.flj.latte.ec.icon.FontEcModule;
import com.imooc.core.app.Latte;
import com.imooc.core.net.interceptors.DebugInterceptor;
import com.imooc.core.net.rx.AddCookieInterceptor;
import com.imooc.lattedamo.event.TestEvent;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by Administrator on 2017/9/4.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Latte.init(this)
                .withIcon(new FontAwesomeModule()) //设置矢量图
                .withIcon(new FontEcModule()) //设置矢量图
                .withLoaderDelayed(1000)
                .withApiHost("http://116.196.95.67/RestServer/api")
                .withInterceptor(new DebugInterceptor("test",R.raw.test))
                .withWeChatAppId("你的微信AppKey")
                .withWechatAppSecret("你的微信AppSecret")
                .withWebEvent("test",new TestEvent())
                .withWebHost("https://www.baidu.com/")//添加Cookie同步拦截器
                .withInterceptor(new AddCookieInterceptor()) //添加cookit拦截器
                .configure();


        //初始化数据库
        DatabaseManager.getInstance().init(this);

    }
}
