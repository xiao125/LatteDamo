package com.imooc.core.util.callback;

import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2017/10/15.
 */

public interface IGlobalCallback<T> {

    void executeCallback(@Nullable T args);

}
