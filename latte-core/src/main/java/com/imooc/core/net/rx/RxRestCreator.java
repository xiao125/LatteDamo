package com.imooc.core.net.rx;

import com.imooc.core.app.ConfigKeys;
import com.imooc.core.app.Latte;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2017/9/24.
 */

public final class RxRestCreator {

    private static final class OKHttpHolder{

        private static final int TIME_OUT =60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        //拦截器
        private static final ArrayList<Interceptor> INTERCEPTORS = Latte.getConfiguration(ConfigKeys.INTERCEPTOR);

        private static OkHttpClient.Builder addInterceptor(){

            if (INTERCEPTORS !=null && !INTERCEPTORS.isEmpty()){

                for (Interceptor interceptors : INTERCEPTORS){

                    BUILDER.addInterceptor(interceptors);
                }
            }
            return BUILDER;
        }

        //OkHttpClient添加拦截器
        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();



    }

    /**
     * 构建全局Retrofit客户端
     */
    private static final class RetrofitHolder{

        private static final String BASE_URL = Latte.getConfiguration(ConfigKeys.API_HOST);
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    /**
     * Service接口
     */

    private static final class RxRestServiceHolder{

        private static final RxRestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RxRestService.class);
    }


    public static RxRestService getRxRestService(){

        return RxRestServiceHolder.REST_SERVICE;
    }


}
