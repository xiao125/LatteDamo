package com.imooc.core.app;

/**
 * 登录成功失败回调
 * Created by Administrator on 2017/9/13 0013.
 */

public interface IUserChecker {

    void onSignIn();
    void onNotSignIn();
}
