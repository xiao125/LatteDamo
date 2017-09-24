package com.imooc.core.net.rx;

import com.imooc.core.util.storage.LattePreference;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 添加Cookie拦截器
 * Created by Administrator on 2017/9/23.
 */

public class AddCookieInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {

        final Request.Builder builder =  chain.request().newBuilder();
        Observable.just(LattePreference.getCustomAppProfile("cookie"))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String cookie) throws Exception {

                        //给原生API请求附带上WebView拦截下来的Cookie
                        builder.addHeader("Cookie",cookie);

                    }
                });


        return chain.proceed(builder.build());
    }



}
