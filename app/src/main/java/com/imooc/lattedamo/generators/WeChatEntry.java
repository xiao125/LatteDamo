package com.imooc.lattedamo.generators;

import com.compiler.annotations.EntryGenerator;
import com.imooc.core.wechat.templates.WXEntryTemplate;

import org.greenrobot.greendao.annotation.Entity;

/**
 * 微信登录
 * Created by Administrator on 2017/9/16 0016.
 */

@SuppressWarnings("unused")
@EntryGenerator(
        packageName = "com.imooc.lattedamo",
        entryTemplate = WXEntryTemplate.class
)
public interface WeChatEntry {
}
