package com.cxz.networklib.annotation;

import com.cxz.networklib.type.NetType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chenxz
 * @date 2019/2/22
 * @desc
 */
@Target(ElementType.METHOD) // 作用、目标在方法之上
@Retention(RetentionPolicy.RUNTIME) // jvm 在运行时，通过反射获取注解的值
public @interface Network {
    NetType netType() default NetType.AUTO;
}
