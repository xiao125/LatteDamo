package com.flj.latte.ec.launcher;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.flj.latte.ec.R;
import com.imooc.core.app.AccountManager;
import com.imooc.core.app.IUserChecker;
import com.imooc.core.delegates.LatteDelegate;
import com.imooc.core.util.storage.LattePreference;
import com.latte.ui.launcher.ILauncherListener;
import com.latte.ui.launcher.LauncherHolderCreator;
import com.latte.ui.launcher.OnLauncherFinishTag;
import com.latte.ui.launcher.ScrollLauncherTag;

import java.util.ArrayList;

/**
 * 引导页 ,Fragment 页面
 * Created by Administrator on 2017/9/13 0013.
 */

public class LauncherScrollDelegate extends LatteDelegate implements OnItemClickListener {

    private ConvenientBanner<Integer> mConvenientBanner =null;
    private static final ArrayList<Integer> INIEGERS = new ArrayList<>();
    private ILauncherListener mILauncherListener = null;

    private void initBanner(){

        INIEGERS.add(R.mipmap.launcher_01);
        INIEGERS.add(R.mipmap.launcher_02);
        INIEGERS.add(R.mipmap.launcher_03);
        INIEGERS.add(R.mipmap.launcher_04);
        INIEGERS.add(R.mipmap.launcher_05);

        //banner 调用方法
        mConvenientBanner
                .setPages(new LauncherHolderCreator(),INIEGERS)//设置pages数量
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)//设置指示器的方向
                .setOnItemClickListener(this) //点击监听
                .setCanLoop(false); //是否可以循环


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener){
            mILauncherListener = (ILauncherListener) activity;
        }

    }


    @Override
    public Object setLayout() {
        mConvenientBanner = new ConvenientBanner<Integer>(getContext());
        return mConvenientBanner;
    }


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initBanner();
}

    @Override
    public void onItemClick(int position) {

        if (position == INIEGERS.size()-1){ //如果点击的是最后一个
            //存入SharePrference中
            LattePreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(),true);

            //检查用户是否已经登录
            AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void onSignIn() {

                    if (mILauncherListener !=null){
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                    }

                }

                @Override
                public void onNotSignIn() {
                    if (mILauncherListener != null) {
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                    }
                }
            });
        }
    }
}
