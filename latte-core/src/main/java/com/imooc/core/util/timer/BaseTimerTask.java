package com.imooc.core.util.timer;

import java.util.TimerTask;

/**
 * Created by Administrator on 2017/9/13 0013.
 */

public class BaseTimerTask extends TimerTask {

    private ITimerListener mITimerListener = null;

    public BaseTimerTask(ITimerListener mITimerListener) {
        this.mITimerListener = mITimerListener;
    }

    @Override
    public void run() {
        if (mITimerListener !=null){
            mITimerListener.onTimer();
        }
    }
}
