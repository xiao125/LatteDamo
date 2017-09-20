package com.imooc.core.delegates.web.event;

import android.content.Context;
import android.webkit.WebView;

import com.imooc.core.delegates.web.WebDelegate;

/**
 * Created by Administrator on 2017/9/20 0020.
 */

public abstract class Event implements IEvent {
    private Context mContent = null;
    private String mAction = null;
    private WebDelegate mDelegate = null;
    private String mUrl = null;
    private WebView mWebView =null;

    public Context getmContent() {
        return mContent;
    }

    public void setmContent(Context mContent) {
        this.mContent = mContent;
    }

    public String getmAction() {
        return mAction;
    }

    public void setmAction(String mAction) {
        this.mAction = mAction;
    }

    public WebDelegate getmDelegate() {
        return mDelegate;
    }

    public void setmDelegate(WebDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public WebView getmWebView() {
        return mWebView;
    }


}
