package com.imooc.core.net;

import com.imooc.core.app.ConfigKeys;
import com.imooc.core.app.Latte;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2017/9/9.
 */

public final class RestCreator {

    /**
     * 参数容器
     */

    private static final class ParamsHolder{

        private static final WeakHashMap<String,Object> PARAMS = new WeakHashMap<>();

    }

    public static WeakHashMap<String,Object> getParams(){
        return ParamsHolder.PARAMS;

    }


    /**
     * 构建OkHttp
     */
    private static final class OkHttpHolder{

        private static final int TIME_OUT =60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();

        //拦截器
        private static final ArrayList<Interceptor> INTERCEPTORS = Latte.getConfiguration(ConfigKeys.INTERCEPTOR);


        //初始化拦截器
        private static OkHttpClient.Builder addInterceptor(){
            if (INTERCEPTORS !=null && !INTERCEPTORS.isEmpty()){
                for (Interceptor interceptor : INTERCEPTORS){
                    BUILDER.addInterceptor(interceptor);
                }
            }
            return BUILDER;
        }


        //请求等待时间
        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();


    }

    /**
     * 构建全局Retrofit客户端
     */
    private static final class RetrofitHolder{

        private static final String BASE_URL = Latte.getConfiguration(ConfigKeys.API_HOST); //获取Url
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

    }


    /**
     * Service接口
     */
    private static final class RestServiceHolder{

        private static final RestService REST_SERVICE = RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);

    }

    public static RestService getRestService(){
        return RestServiceHolder.REST_SERVICE;
    }



}
