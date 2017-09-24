package com.imooc.core.app;

import android.app.Activity;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.Utils;
import com.imooc.core.delegates.web.event.Event;
import com.imooc.core.delegates.web.event.EventManager;
import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * app配置类，单例模式
 * Created by Administrator on 2017/9/3.
 */

public  final class Configurator {

    private static final HashMap<Object,Object> LATTE_CONFIGS = new HashMap<>();
    private static  final Handler HANDLER = new Handler();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>(); //矢量图icon
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();



    private Configurator(){

        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY,false);//默认是设置未初始化
        LATTE_CONFIGS.put(ConfigKeys.HANDLER,HANDLER);//添加Handler

    }

    static  Configurator getInstance(){
        return Holder.INSTANCE;
    }

    final HashMap<Object,Object> getLatteConfigs(){
        return LATTE_CONFIGS;
    }


    //静态内部类单例模式
    private static  class  Holder{

        private static final Configurator INSTANCE = new Configurator();


    }


    public final void configure(){

        initIcons(); //设置icons
        Logger.addLogAdapter(new AndroidLogAdapter());
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY, true); //app配置初始化完成
       Utils.init(Latte.getApplicationContext());

    }




    //设置网络请求域名统一标识
    public final Configurator withApiHost(String host){
        LATTE_CONFIGS.put(ConfigKeys.API_HOST,host);
        return this;
    }


    //下载
    public final Configurator withLoaderDelayed(long delayed){

        LATTE_CONFIGS.put(ConfigKeys.LOADER_DELAYED,delayed);
        return this;
    }


    //设置字体
    private void initIcons(){

        if (ICONS.size()>0){
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i =1;i< ICONS.size();i++ ){
                initializer.with(ICONS.get(i));
            }
        }

    }


    public final  Configurator withIcon(IconFontDescriptor descriptor){
        ICONS.add(descriptor);
        return this;
    }


    //拦截器
    public final Configurator withInterceptor(Interceptor interceptor){

        INTERCEPTORS.add(interceptor);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR,INTERCEPTORS);
        return this;

    }

    //微信appid
    public final Configurator withWeChatAppId(String appId){
        LATTE_CONFIGS.put(ConfigKeys.WE_CHAT_APP_ID,appId);
        return this;
    }

    public final Configurator withWechatAppSecret(String appSecret){
        LATTE_CONFIGS.put(ConfigKeys.WE_CHAT_APP_SECRET,appSecret);
        return this;
    }


    public final Configurator withActivity(Activity activity){
        LATTE_CONFIGS.put(ConfigKeys.ACTIVITY,activity);
        return this;
    }

    public Configurator withJavascriptInterface(@NonNull String name){
        LATTE_CONFIGS.put(ConfigKeys.JAVASCRIPT_INTERFACE,name);
        return this;
    }

    //添加webView
   public Configurator withWebEvent(@NonNull String name, @NonNull Event event){
       final EventManager manager = EventManager.getInstance();
       manager.addEvent(name,event);
       return this;

   }

   //添加Html域名
   public Configurator withWebHost(String host){

       LATTE_CONFIGS.put(ConfigKeys.WEB_HOST,host);
       return this;
   }




    //查询配置初始化是否完成
    private void checkConfiguration(){

        final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if (!isReady){

            throw new RuntimeException("初始化未完成");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key){

        checkConfiguration();
        final Object value = LATTE_CONFIGS.get(key);
        if (value == null){
            throw new NullPointerException(key.toString() + "IS NULl");

        }
        return (T) LATTE_CONFIGS.get(key);
    }


}
