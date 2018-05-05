package com.imooc.core.ui.loader;

import android.content.Context;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

/**
 * AVLoading 创建loader
 * Created by Administrator on 2017/9/10.
 */

final class LoaderCreator {

    private static final WeakHashMap<String,Indicator> LOADING_MAP = new WeakHashMap<>();

    static AVLoadingIndicatorView create(String type, Context context){

        final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);
        if (LOADING_MAP.get(type) == null){ //如果是为空
            final Indicator indicator = getIndicator(type);//Indicator就是Drawable ，loader样式
            LOADING_MAP.put(type,indicator);//添加

        }
        avLoadingIndicatorView.setIndicator(LOADING_MAP.get(type));
        return avLoadingIndicatorView;

    }

    //利用反射添加
    private static Indicator getIndicator(String name){

        if (name ==null || name.isEmpty()){
            return null;
        }

        final StringBuilder drawableClassName = new StringBuilder();
        if (!name.contains(".")){
            final String defaultPackageName = AVLoadingIndicatorView.class.getPackage().getName();
            drawableClassName.append(defaultPackageName)
                    .append(".indicators")//利用反射，AVLoadingIndicatorView总是“indicators”相应的包名下，所以拼接到相应的包下面
                    .append(".");
        }

        drawableClassName.append(name);

        try{
            final Class<?> drawableClass = Class.forName(drawableClassName.toString());
            return (Indicator) drawableClass.newInstance();

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


}
