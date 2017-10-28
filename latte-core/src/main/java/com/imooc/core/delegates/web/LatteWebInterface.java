package com.imooc.core.delegates.web;

import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.imooc.core.delegates.web.event.Event;
import com.imooc.core.delegates.web.event.EventManager;
import com.imooc.core.util.log.LatteLogger;

/**
 * Created
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
        final Event event = EventManager.getInstance().createEvent(action); //读取HashMap中的数据
        LatteLogger.d("WEB_EVENT",params);

        if (event !=null){ //存入相应的数据

            event.setmAction(action);
            event.setmDelegate(DELEGATE);
            event.setmContent(DELEGATE.getContext());
            event.setmUrl(DELEGATE.getUrl()); //获取的webview 显示的url
            return event.execute(params);
        }

        return null;
    }


}
