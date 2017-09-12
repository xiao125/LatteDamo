package com.flj.latte.ec.icon;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;

/**
 * Created by Administrator on 2017/9/4.
 */

public class FontEcModule implements IconFontDescriptor {
    @Override
    public String ttfFileName() { //字体库名字
        return "iconfont.ttf";
    }

    @Override
    public Icon[] characters() {
        return EcIcons.values();
    }
}
