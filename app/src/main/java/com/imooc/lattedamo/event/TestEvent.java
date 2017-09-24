package com.imooc.lattedamo.event;

import android.webkit.WebView;

import com.imooc.core.delegates.web.event.Event;

/**
 * Created by Administrator on 2017/9/24.
 */

public class TestEvent extends Event {

    @Override
    public String execute(String params) {

        if (getmAction().equals("test")){

            final WebView webView = getmWebView();
            webView.post(new Runnable() {
                @Override
                public void run() {

                    //相应js事件
                    webView.evaluateJavascript("nativeCall();",null);
                }
            });
        }

        return null;
    }
}
