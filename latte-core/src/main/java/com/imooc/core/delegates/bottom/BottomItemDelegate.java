package com.imooc.core.delegates.bottom;

import android.widget.Toast;

import com.imooc.core.R;
import com.imooc.core.app.Latte;
import com.imooc.core.delegates.LatteDelegate;

/**
 * Created by Administrator on 2017/9/16 0016.
 */

public  abstract class BottomItemDelegate extends LatteDelegate{

    // 再点一次退出程序时间设置
    private static final long WAIT_TIME =2000L;
    private long TOUCH_TIME =0;

    @Override
    public boolean onBackPressedSupport() {

        if (System.currentTimeMillis()-TOUCH_TIME<WAIT_TIME){
            mActivity.finish();
        }else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(mActivity,"双击退出"+ Latte.getApplicationContext().getString(R.string.app_name),Toast.LENGTH_LONG).show();
        }
        return true;
    }
}
