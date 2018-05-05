package com.flj.latte.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flj.latte.ec.R;
import com.flj.latte.ec.R2;
import com.imooc.core.app.IUserChecker;
import com.imooc.core.delegates.LatteDelegate;
import com.imooc.core.util.storage.LattePreference;
import com.imooc.core.util.timer.BaseTimerTask;
import com.imooc.core.util.timer.ITimerListener;
import com.latte.ui.launcher.ILauncherListener;
import com.latte.ui.launcher.OnLauncherFinishTag;
import com.latte.ui.launcher.ScrollLauncherTag;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 闪屏页， 每次进入app时。此页面始终依附在MainActivity中（单Activity+多Fragment）架构,
 * Created by Administrator on 2017/9/13 0013.
 */

public class LauncherDelegate extends LatteDelegate implements ITimerListener {


    @BindView(R2.id.tv_launcher_time)
    AppCompatTextView mTvTimer =null;

    private Timer mTimer =null;
    private int mCount =5;
    private ILauncherListener mILauncherListener =null;
    Unbinder unbinder;


    @OnClick(R2.id.tv_launcher_time)
    void onClickTimerView(){
        if (mTimer !=null){
            mTimer.cancel();
            mTimer=null;
            checkIsShowScroll();
        }

    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onTimer() {

        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTvTimer !=null){
                    mTvTimer.setText(MessageFormat.format("跳过\n{0}s",mCount));
                    mCount--;
                    if (mCount <0){
                        mTimer.cancel();
                        mTimer=null;
                        checkIsShowScroll();
                    }
                }
            }
        });

    }

    private void initTimer(){
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        mTimer.schedule(task,0,1000);

    }


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        initTimer();

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener){
            mILauncherListener = (ILauncherListener) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }



    //判断是否显示滑动启动页
    private void checkIsShowScroll(){

        if (!LattePreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())){

            //开始引导页界面，其中第二个参数是启动模式
            getSupportDelegate().start(new LauncherScrollDelegate(),SINGLETASK);

        }else {

            //检查用户是否登录了APP
            com.imooc.core.app.AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void onSignIn() {
                    if (mILauncherListener !=null){
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED); //登录了
                    }
                }

                @Override
                public void onNotSignIn() {

                    if (mILauncherListener !=null){
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED); //没有登录
                    }
                }
            });

        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
