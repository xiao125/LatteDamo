package com.imooc.core.delegates;

/**
 * setLayout(),onBindView() ，Fragment的view显示与数据绑定方法
 * Created by Administrator on 2017/9/5.
 */

public abstract class LatteDelegate extends PermissionCheckerDelegate {

    @SuppressWarnings("unchecked")
    public <T extends LatteDelegate> T getParentDelegate() {
        return (T) getParentFragment();
    }
}
