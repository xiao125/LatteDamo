package com.imooc.core.delegates.bottom;

/**
 * charSequence是一个接口，表示char值的一个可读可写序列
 * Created by Administrator on 2017/9/16 0016.
 */

public final class BottomTabBean {

    private final CharSequence ICON;
    private final CharSequence TITLE;

    public BottomTabBean(CharSequence icon, CharSequence title) {
        this.ICON = icon;
        this.TITLE = title;
    }

    public CharSequence getIcon() {
        return ICON;
    }

    public CharSequence getTitle() {
        return TITLE;
    }

}
