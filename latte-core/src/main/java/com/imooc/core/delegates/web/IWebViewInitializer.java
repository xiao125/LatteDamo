package com.imooc.core.delegates.web;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Administrator on 2017/9/20 0020.
 */

public interface IWebViewInitializer {

    WebView initWebView(WebView webView);
    WebViewClient initWebViewClient();
    WebChromeClient initWebChromeClient();

}
