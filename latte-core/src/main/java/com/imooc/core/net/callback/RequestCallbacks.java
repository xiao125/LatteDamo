package com.imooc.core.net.callback;

import android.os.Handler;

import com.imooc.core.app.ConfigKeys;
import com.imooc.core.app.Latte;
import com.imooc.core.net.RestCreator;
import com.imooc.core.ui.loader.LatteLoader;
import com.imooc.core.ui.loader.LoaderStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/9/10.
 */

public final class RequestCallbacks implements Callback<String> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final LoaderStyle LOADER_STYLE;
    private static final Handler HANDLER = Latte.getHandler();

    public RequestCallbacks(IRequest request, ISuccess success, IFailure failure, IError error, LoaderStyle style) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.LOADER_STYLE = style;
    }


    @Override
    public void onResponse(Call<String> call, Response<String> response) {

        if (response.isSuccessful()){//请求成功
            if (call.isExecuted()){
                if (SUCCESS !=null){
                    SUCCESS.onSuccess(response.body());//返回成功的数据(response.body)
                }
            }

        }else {
            if (ERROR !=null){
                ERROR.onError(response.code(),response.message()); //返回错误信息
            }
        }

        //loading加载
        onRequestFinish();

    }






    @Override
    public void onFailure(Call<String> call, Throwable t) {

        if (FAILURE !=null){
            FAILURE.onFailure();
        }
        if (REQUEST !=null){  //请求结束
            REQUEST.onRequestEnd();
        }
        //loading加载
        onRequestFinish();
    }



    private void onRequestFinish(){
        final long delayed = Latte.getConfiguration(ConfigKeys.LOADER_DELAYED);
        if (LOADER_STYLE !=null){
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    RestCreator.getParams().clear();
                    LatteLoader.stopLoading();
                }
            },delayed);
        }
    }



}
