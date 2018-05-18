package com.imooc.lattedamo.generators;

import com.compiler.annotations.PayEntryGenerator;
import com.imooc.core.wechat.templates.WXPayEntryTemplate;


/**
 * 生成 微信支付类（符合微信规定包名：com.imooc.lattedamo.wxapi.WXPayEntryTemplate）
 *   @EntryGenerator :  表示latte-annotation  java librarys 下的 微信支付注解类
 *    第一个参数：app包名
 *    第二个参数：  app微信支付逻辑类（微信支付类）
 */


@SuppressWarnings("unused")
@PayEntryGenerator(
        packageName = "com.imooc.lattedamo",
        payEntryTemplate = WXPayEntryTemplate.class
)
public interface WeChatPayEntry {
}
