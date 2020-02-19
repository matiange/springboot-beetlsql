package com.matiange.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 * <br>Created by Admin on 2020/2/19
 * <br>星期三 at 16:29.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLogger {
    String value() default "";
}
