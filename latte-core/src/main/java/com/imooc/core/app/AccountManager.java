package com.imooc.core.app;

import com.imooc.core.util.storage.LattePreference;

/**
 * Created by Administrator on 2017/9/13 0013.
 */

public class AccountManager {

    private enum SignTag{
        SIGN_TAG
    }

    //保存用户登录状态，登录后调用
    public static void setSignState(boolean state){

        //保存用户登录状态，存入Preference中
        LattePreference.setAppFlag(SignTag.SIGN_TAG.name(),state);
    }

    //获取保存的用户登录状态
    private static  boolean isSignIn(){
        return LattePreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    //判断是否登录
    public static void checkAccount(IUserChecker checker){

        if (isSignIn()){
            checker.onSignIn();
        }
        else {
            checker.onNotSignIn();
        }
    }


}
