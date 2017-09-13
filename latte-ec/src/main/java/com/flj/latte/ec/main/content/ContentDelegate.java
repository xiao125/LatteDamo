package com.flj.latte.ec.main.content;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.imooc.core.delegates.LatteDelegate;

import java.util.List;

/**
 * Created by Administrator on 2017/9/13 0013.
 */

public class ContentDelegate  extends LatteDelegate{

    private static final String ARG_CONTENT_ID = "CONTENT_ID";
    private int mContentId=-1;
   // private List<SectionBean>



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        if (args !=null){
            mContentId = args.getInt(ARG_CONTENT_ID);
        }

    }

    @Override
    public Object setLayout() {
        return null;
    }
}
