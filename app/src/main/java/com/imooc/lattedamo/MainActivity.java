package com.imooc.lattedamo;

import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.flj.latte.ec.main.EcBottomDelegate;
import com.flj.latte.ec.sign.ISignListener;
import com.flj.latte.ec.sign.SignInDelegate;
import com.imooc.core.activities.ProxyActivity;
import com.imooc.core.app.Latte;
import com.imooc.core.delegates.LatteDelegate;
import com.latte.ui.launcher.ILauncherListener;
import com.latte.ui.launcher.OnLauncherFinishTag;

import qiu.niorgai.StatusBarCompat;

/**
 * 主Activity+多Fragment
 */
public class MainActivity extends ProxyActivity implements ISignListener,ILauncherListener {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.hide();
        }

        Latte.getConfigurator().withActivity(this);//全局的Activity
        StatusBarCompat.translucentStatusBar(this,true); //修改状态栏颜色

    }



    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public LatteDelegate setRootDelegate() {
        return new EcBottomDelegate(); //设置Fragment模块管理
    }


    @Override
    public void onSignInSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {

        switch (tag){
            case SIGNED:
                Toast.makeText(this, "启动结束，用户登录了", Toast.LENGTH_LONG).show();
               getSupportDelegate().startWithPop(new EcBottomDelegate()); //闪屏结束，进入主界面

                break;

            case NOT_SIGNED:
                Toast.makeText(this, "启动结束，用户没登录", Toast.LENGTH_LONG).show();
                getSupportDelegate().startWithPop(new SignInDelegate()); //跳转登录页面

                break;
        }


    }

}
