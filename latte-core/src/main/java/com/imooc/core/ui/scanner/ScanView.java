package com.imooc.core.ui.scanner;

import android.content.Context;
import android.util.AttributeSet;

import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * 打开二维码扫描框
 * Created b
 */

public class ScanView extends ZBarScannerView {

    public ScanView(Context context) {
        super(context,null);
    }

    public ScanView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected IViewFinder createViewFinderView(Context context) {
        return new LatteViewFinderView(context);//修改二维码的框框颜色
    }
}
