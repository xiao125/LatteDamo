package com.imooc.core.delegates.web;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.view.menu.MenuWrapperFactory;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.imooc.core.app.ConfigKeys;
import com.imooc.core.app.Latte;
import com.imooc.core.delegates.LatteDelegate;
import com.imooc.core.delegates.web.route.RouteKeys;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * Fragment 显示webView
 * Created by Administrator on 2017/9/20 0020.
 */

public abstract class WebDelegate extends LatteDelegate implements IWebViewInitializer {

    private WebView mWebView=null;

    /**
     * WeakReference的父类Reference中有一个变量queue，是ReferenceQueue类型的。
     * WeakReference中的构造函数中有一个两个参数的构造函数，
     * 其中第二个参数就是这个ReferenceQueue，表示在WeakReference指向的对象被回收之后，
     * 可以利用ReferenceQueue来保存被回收的对象
     */
    private final ReferenceQueue<WebView> WEB_VIEW_QUEUE = new ReferenceQueue<>();
    private String mUrl=null;
    private boolean mIsWebViewAvailable =false;
    private LatteDelegate mTopDelegate =null;

    public WebDelegate() {
    }

    public abstract IWebViewInitializer setInitializer();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        mUrl = args.getString(RouteKeys.URL.name());

        initWebView();
    }


    @SuppressLint("JavascriptInterface")
    private void initWebView(){
        if (mWebView !=null){
            mWebView.removeAllViews();
            mWebView.destroy();
        }else {

            final IWebViewInitializer initializer = setInitializer();
            if (initializer !=null){

                //WeakReference 弱引用
                final WeakReference<WebView> webViewWeakReference =
                        new WeakReference<>(new WebView(getContext()),WEB_VIEW_QUEUE);

                mWebView = webViewWeakReference.get();
                mWebView= initializer.initWebView(mWebView); //WebView
                mWebView.setWebViewClient(initializer.initWebViewClient()); //WebViewClient
                mWebView.setWebChromeClient(initializer.initWebChromeClient());//WebChromeClient
                final String name = Latte.getConfiguration(ConfigKeys.JAVASCRIPT_INTERFACE);//与js交互的标识，一般都约定好的，比如”action“
                /**
                 * addJavascriptInterface将一个对象加入到webview中作为js方法供js调用，从而实现js与java的通信。
                 * addJavascriptInterface包含两项参数:
                 * 第一个参数：绑定到JavaScript的类实例
                 * 第二个参数：用来显示JavaScript中的实例的名称
                 */
                mWebView.addJavascriptInterface(LatteWebInterface.create(this),name); //设置js交互
                mIsWebViewAvailable = true;
            }else {

                throw new NullPointerException("Initializer is null!");
            }

        }

    }

    public void setTopDelegate(LatteDelegate delegate){
        mTopDelegate = delegate;
    }

    public LatteDelegate getTopDelegate(){

        if (mTopDelegate == null){
            mTopDelegate=this;
        }
        return mTopDelegate;
    }

    public WebView getmWebView(){

        if (mWebView == null){
            throw new NullPointerException("WebView IS NULL!");
        }
        return mIsWebViewAvailable ? mWebView : null;
    }

    public String getUrl(){

        if (mUrl == null){
            throw new NullPointerException("WebView IS NULL!");
        }
        return mUrl;
    }


    @Override
    public void onPause() {
        super.onPause();

        if (mWebView !=null){
            mWebView.onPause();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.onResume();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsWebViewAvailable = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
    }







}
