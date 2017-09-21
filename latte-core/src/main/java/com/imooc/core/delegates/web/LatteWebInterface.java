package com.imooc.core.delegates.web;

import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.imooc.core.delegates.web.event.Event;
import com.imooc.core.delegates.web.event.EventManager;
import com.imooc.core.util.log.LatteLogger;

/**
 * Created by Administrator on 2017/9/20 0020.
 */

public class LatteWebInterface {

    private final WebDelegate DELEGATE;

    public LatteWebInterface(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    static LatteWebInterface create(WebDelegate delegate){

        return new LatteWebInterface(delegate);
    }

    //js与原生交互
    @SuppressWarnings("unused")
    @JavascriptInterface
    public String event(String params){

        final String action = JSON.parseObject(params).getString("action"); //js返回的标识
        final Event event = EventManager.getInstance().createEvent(action);
        LatteLogger.d("WEB_EVENT",params);

        if (event !=null){

            event.setmAction(action);
            event.setmDelegate(DELEGATE);
            event.setmContent(DELEGATE.getContext());
            event.setmUrl(DELEGATE.getUrl());
            return event.execute(params);
        }

        return null;
    }


}
