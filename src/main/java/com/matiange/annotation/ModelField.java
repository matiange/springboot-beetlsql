package com.matiange.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author MaTianGe
 * @version 0.1
 * @Description: 自定义字段描述注解
 * @date 2020/8/9 14:16
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModelField {
    public String fieldDesc() default "字段中文描述";
    public String tableFieldName() default "数据表字段名";
}
