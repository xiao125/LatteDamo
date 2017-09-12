package com.imooc.core.app;

import android.content.Context;
import android.os.Handler;

/**
 * Created by Administrator on 2017/9/3.
 */

public final class Latte {

    public  static  Configurator init(Context context){

        Configurator.getInstance()
                .getLatteConfigs()
                .put(ConfigKeys.APPLICATION_CONTEXT,context.getApplicationContext());//app全局上下文

          return Configurator.getInstance();
    }


    public static Configurator getConfigurator(){

        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object key){
        return getConfigurator().getConfiguration(key);
    }


    //app全局上下文
    public static Context getApplicationContext(){

        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }

    public static Handler getHandler(){

        return getConfiguration(ConfigKeys.HANDLER);
    }

    public static void test(){

    }


}
