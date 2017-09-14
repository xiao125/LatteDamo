package com.imooc.lattedamo;

import android.widget.Toast;

import com.flj.latte.ec.sign.ISignListener;
import com.imooc.core.activities.ProxyActivity;
import com.imooc.core.delegates.LatteDelegate;
import com.latte.ui.launcher.ILauncherListener;
import com.latte.ui.launcher.OnLauncherFinishTag;

/**
 * 主Activity+多Fragment
 */
public class MainActivity extends ProxyActivity implements ISignListener,ILauncherListener {

    @Override
    public LatteDelegate setRootDelegate() {
        return new ExampleDelegate();
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
               // getSupportDelegate().start(new EC);

                break;

            case NOT_SIGNED:
                Toast.makeText(this, "启动结束，用户没登录", Toast.LENGTH_LONG).show();
                break;
        }


    }

}
