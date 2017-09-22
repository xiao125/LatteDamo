package com.flj.latte.ec.pay;

/**
 * Created by Administrator on 2017/9/22 0022.
 */

public interface IAlPayResultListener {

    void onPaySuccess();

    void onPaying();

    void onPayFail();

    void onPayCancel();

    void onPayConnectError();


}
