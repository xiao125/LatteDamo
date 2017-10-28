package com.imooc.core.delegates.web;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Administrator on 2017/9/20 0020.
 */

public interface IWebViewInitializer {

    WebView initWebView(WebView webView);
    //在影响View的事件到来时，会通过WebViewClient中的方法回调通知用户;
    // 比如：加载网页显示进度条，加载结束以后隐藏进度条
    WebViewClient initWebViewClient();

    //当影响浏览器的事件到来时，就会通过WebChromeClient中的方法回调通知用法
    WebChromeClient initWebChromeClient();

}
