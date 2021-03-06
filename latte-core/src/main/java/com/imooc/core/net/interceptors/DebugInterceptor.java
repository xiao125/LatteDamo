package com.imooc.core.net.interceptors;


import android.support.annotation.NonNull;
import android.support.annotation.RawRes;

import com.imooc.core.util.file.FileUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/9/11.
 */

public class DebugInterceptor extends BaseInterceptor {

    private final String DEBUG_URL;
    private final int DEBUG_RAW_ID;

    public DebugInterceptor(String debugUrl, int rawId) {
        this.DEBUG_URL = debugUrl;
        this.DEBUG_RAW_ID = rawId;
    }

    //返回需要的response
    private Response getResponse(Chain chain,String json){

        return new Response.Builder()
                .code(200)
                .addHeader("Content-Type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"),json))
                .message("OK")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .build();
    }

    private Response debugResponse(Chain chain, @RawRes int rawId){

        final String json = FileUtil.getRawFile(rawId); //读取raw目录中的文件,并返回为字符串
        return getResponse(chain,json);

    }



    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        final String url = chain.request().url().toString();
        if (url.contains(DEBUG_URL)){ //如果拦截的URL包含关键字

            return debugResponse(chain,DEBUG_RAW_ID); //返回需要的数据
        }

        return chain.proceed(chain.request());
    }
}
