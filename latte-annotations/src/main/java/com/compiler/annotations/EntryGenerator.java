package com.compiler.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解类， 传入包名
 * @Target(ElementType.TYPE) 表示次注解用在类上面
 *  @Retention(RetentionPolicy.SOURCE) 表示编译期在源码上运行
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface EntryGenerator {

    String packageName();
    Class<?> entryTemplate();
}
