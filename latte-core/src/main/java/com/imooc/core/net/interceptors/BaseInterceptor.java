package com.imooc.core.net.interceptors;

import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * 拦截器
 * Created by Administrator on 2017/9/11.
 */

public abstract class BaseInterceptor implements Interceptor{


    /**
     * 有序的Url 参数段
     * @param chain
     * @return
     */
    //LinkedHashMap 有序的
    //HashMap 无序的
    protected LinkedHashMap<String,String> getUrlParameters(Chain chain){
        final HttpUrl url = chain.request().url();
        int size = url.querySize();
        final LinkedHashMap<String,String> params = new LinkedHashMap<>();
        for (int i=0;i<size;i++){
            params.put(url.queryParameterName(i),url.queryParameterValue(i));
        }
        return params;

    }


    /**
     * 通过Key值获取value
     * @param chain
     * @param key
     * @return
     */
    protected String getUrlParameters(Chain chain,String key){
        final Request request = chain.request();
        return request.url().queryParameter(key);
    }


    protected LinkedHashMap<String,String> getBodyParameters(Chain chain){
        final FormBody formBody = (FormBody) chain.request().body();
        final LinkedHashMap<String,String> params = new LinkedHashMap<>();
        int size =0;
        if (formBody !=null){
            size = formBody.size();
        }
        for (int i=0;i<size;i++){
            params.put(formBody.name(i),formBody.value(i));
        }
        return params;

    }

    protected String getBodyParameters(Chain chain,String key){
        return getBodyParameters(chain).get(key);
    }


}
