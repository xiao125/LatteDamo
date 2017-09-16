package com.imooc.core.wechat.templates;

import com.imooc.core.wechat.BaseWXEntryActivity;
import com.imooc.core.wechat.LatteWeChat;

/**
 * 微信登录Activity
 * Created by Administrator on 2017/9/16 0016.
 */

public class WXEntryTemplate extends BaseWXEntryActivity {

    @Override
    protected void onResume() {
        super.onResume();
        finish();
        overridePendingTransition(0,0);//登录成功透明消失
    }

    @Override
    protected void onSignInSuccess(String userInfo) {

        LatteWeChat.getInstance().getSignInCallback().onSignInSuccess(userInfo);
    }


}
