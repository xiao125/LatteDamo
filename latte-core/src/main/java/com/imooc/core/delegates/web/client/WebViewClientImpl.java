package com.imooc.core.delegates.web.client;

import android.graphics.Bitmap;
import android.os.Handler;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.imooc.core.app.ConfigKeys;
import com.imooc.core.app.Latte;
import com.imooc.core.delegates.web.IPageLoadListener;
import com.imooc.core.delegates.web.WebDelegate;
import com.imooc.core.delegates.web.route.Router;
import com.imooc.core.ui.loader.LatteLoader;
import com.imooc.core.util.log.LatteLogger;
import com.imooc.core.util.storage.LattePreference;

/**
 * 自定义 WebViewClient
 * Created by Administrator on 2017/9/20 0020.
 */

public class WebViewClientImpl extends WebViewClient {

    private final WebDelegate DELEGATE;
    private IPageLoadListener mIPageLoadListener = null;
    private static final Handler HANDLER = Latte.getHandler();

    public void setPageLoadListener(IPageLoadListener listener){
        this.mIPageLoadListener = listener;
    }

    public WebViewClientImpl(WebDelegate delegate){
        this.DELEGATE =delegate;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        LatteLogger.d("shouldOverrideUrlLoading",url);
        return Router.getInstance().handleWebUrl(DELEGATE, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (mIPageLoadListener !=null){
            mIPageLoadListener.onLoadStart();
        }
        LatteLoader.showLoading(view.getContext()); //显示Loading

    }

    //获取浏览器cookie
    private void syncCookie(){

        final CookieManager manager = CookieManager.getInstance();

        /**
          注意，这里的Cookie和API请求的Cookie是不一样的，这个在网页不可见
         */
        final String webHost = Latte.getConfiguration(ConfigKeys.WEB_HOST);
        if (webHost !=null){
            if (manager.hasCookies()){
                final String cookieStr = manager.getCookie(webHost);
                if (cookieStr !=null && !cookieStr.equals("")){
                    LattePreference.addCustomAppProfile("cookie",cookieStr); //添加cookie
                }
            }
        }
    }



    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        syncCookie();

        if (mIPageLoadListener !=null){
            mIPageLoadListener.onLoadEnd();
        }

        HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                LatteLoader.stopLoading(); //关闭Loading
            }
        },1000);

    }
}
