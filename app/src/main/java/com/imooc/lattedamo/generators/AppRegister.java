package com.imooc.lattedamo.generators;

import com.compiler.annotations.AppRegisterGenerator;
import com.imooc.core.wechat.templates.AppRegisterTemplate;

/**
 * Created by Administrator on 2018/5/7 0007.
 */
@SuppressWarnings("unused")
@AppRegisterGenerator(
        packageName = "com.imooc.lattedamo",
        registerTemplate = AppRegisterTemplate.class
)
public interface AppRegister {
}
