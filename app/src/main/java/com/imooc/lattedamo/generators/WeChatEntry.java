package com.imooc.lattedamo.generators;

import com.compiler.annotations.EntryGenerator;
import com.imooc.core.wechat.templates.WXEntryTemplate;

import org.greenrobot.greendao.annotation.Entity;

/**
 * 生成 微信登录类（符合微信规定包名：com.imooc.lattedamo.wxapi.WXEntryTemplate）
 *   @EntryGenerator :  表示latte-annotation  java librarys 下的 微信登录注解类
 *    第一个参数：app包名
 *    第二个参数： app微信登录逻辑类（ 微信登录类）
 */

@SuppressWarnings("unused")
@EntryGenerator(
        packageName = "com.imooc.lattedamo",
        entryTemplate = WXEntryTemplate.class
)
public interface WeChatEntry {
}
