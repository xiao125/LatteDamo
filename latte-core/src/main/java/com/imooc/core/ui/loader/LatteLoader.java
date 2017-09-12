package com.imooc.core.ui.loader;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.imooc.core.R;
import com.imooc.core.util.dimen.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 *  loading
 * Created by Administrator on 2017/9/10.
 */

public class LatteLoader {

    private static final int LOADER_SIZE_SCALE=8;
    private static final int LOADER_OFFSET_SCALE=10;//偏移量

    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();

    //默认的样式
    private static final String DEFAULT_LOADER = LoaderStyle.BallClipRotatePulseIndicator.name();

    public static void showLoading(Context context,Enum<LoaderStyle> type){

        showLoading(context,type.name());

    }


    /**
     * 显示loading
     * @param context 上下文
     * @param type loader样式
     */
    public static void showLoading(Context context,String type){

        final AppCompatDialog dialog = new AppCompatDialog(context,R.style.dialog);


        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type,context);//创建AVloadr
        //获取dialog
        dialog.setContentView(avLoadingIndicatorView);

        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();

        final Window dialogWindow = dialog.getWindow();

        if (dialogWindow !=null){
            final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth / LOADER_SIZE_SCALE;
            lp.height = deviceHeight / LOADER_SIZE_SCALE;
            lp.height = lp.height + deviceHeight / LOADER_OFFSET_SCALE;
            lp.gravity = Gravity.CENTER; //位置居中
        }

        LOADERS.add(dialog);//添加进视图
        dialog.show();

    }


    //显示
    public static void showLoading(Context context){

        showLoading(context,DEFAULT_LOADER);
    }

    public static void stopLoading(){
        for (AppCompatDialog dialog : LOADERS){
            if (dialog !=null){
                if (dialog.isShowing()){
                    dialog.cancel();
                }
            }
        }
    }



}
