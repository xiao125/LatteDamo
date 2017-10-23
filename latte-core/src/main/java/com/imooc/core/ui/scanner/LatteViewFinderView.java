package com.imooc.core.ui.scanner;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import me.dm7.barcodescanner.core.ViewFinderView;

/**
 * 设置扫描框颜色
 * Created by Administrator on 2017/10/23 0023.
 */

public class LatteViewFinderView extends ViewFinderView {

    public LatteViewFinderView(Context context) {
        super(context,null);
    }

    public LatteViewFinderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mSquareViewFinder = true;
        mBorderPaint.setColor(Color.YELLOW);
        mLaserPaint.setColor(Color.YELLOW);
    }
}
