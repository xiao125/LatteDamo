package com.imooc.core.delegates.web.event;

import com.imooc.core.util.log.LatteLogger;

/**
 * Created by Administrator on 2017/9/20 0020.
 */

public class UndefineEvent extends Event {
    @Override
    public String execute(String params) {

        LatteLogger.e("UndefineEvent",params);
        return null;
    }
}
