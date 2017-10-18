package com.imooc.core.ui.camera;

import android.net.Uri;

import com.imooc.core.delegates.PermissionCheckerDelegate;
import com.imooc.core.util.file.FileUtil;

/**
 * Created
 * 照相机调用类
 */

public class LatteCamera {

    public static Uri createCropFile(){

        //创建图片保存路径
        return Uri.parse(FileUtil.createFile("crop_image",FileUtil.getFileNameByTime("IMG","jpg")).getPath());
    }

    public static void start(PermissionCheckerDelegate delegate){

        new CameraHandler(delegate).beginCameraDialog(); //实现头像选择dialog
    }

}
